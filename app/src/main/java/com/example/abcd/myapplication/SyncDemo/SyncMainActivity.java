package com.example.abcd.myapplication.SyncDemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.abcd.myapplication.R;
import com.example.abcd.myapplication.SyncDemo.BR.SampleBC;
import com.example.abcd.myapplication.SyncDemo.DB.DBController;
import com.example.abcd.myapplication.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SyncMainActivity extends AppCompatActivity {

    private String TAG = SyncMainActivity.class.getSimpleName();

    // DB Class to perform DB related operations
    DBController controller = new DBController(this);

    // Progress Dialog Object
    ProgressDialog prgDialog;

    HashMap<String, String> queryValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_main);

        // Get User records from SQLite DB
        ArrayList<HashMap<String, String>> userList = controller.getAllUsers();

        // If users exists in SQLite DB
        if (userList.size() != 0) {

            // Set the User Array list in ListView
            ListAdapter adapter = new SimpleAdapter(SyncMainActivity.this, userList, R.layout.view_user_entry, new String[] {
                    "userId", "userName" }, new int[] { R.id.userId, R.id.userName });
            ListView myList = (ListView) findViewById(android.R.id.list);
            myList.setAdapter(adapter);
        }
        // Initialize Progress Dialog properties
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Transferring Data from Remote MySQL DB and Syncing SQLite. Please wait...");
        prgDialog.setCancelable(false);

        // BroadCase Receiver Intent Object
        Intent alarmIntent = new Intent(this, SampleBC.class);

        // Pending Intent Object
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Alarm Manager Object
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        // Alarm Manager calls BroadCast for every Ten seconds (10 * 1000), BroadCase further calls service to check if new records are inserted in
        // Remote MySQL DB
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 5000, 10 * 1000, pendingIntent);
    }

    // Options Menu (ActionBar Menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // When Options Menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here.
        int id = item.getItemId();

        // When Sync action button is clicked
        if (id == R.id.refresh) {

            // Transfer data from remote MySQL DB to SQLite on Android and perform Sync
            syncSQLiteMySQLDB();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to Sync MySQL to SQLite DB
    private void syncSQLiteMySQLDB() {

        // Show ProgressBar
        prgDialog.show();

        String url = "http://192.168.57.1/mysqlsqlitesync/getusers.php";

        // Make a request to getusers.php
        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Check your log cat for JSON response
                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jArray = new JSONArray(response.toString());

                            //JSONObject jRes = new JSONObject(response.toString());

                            //Log.d("Response -- ", "" + jRes);

                            // Hide ProgressBar
                            prgDialog.hide();

                            // Update SQLite DB with response sent by getusers.php
                            updateSQLite(response.toString());

                        } catch (JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Adding request to request queue
        AppController.getInstance()
                .addToRequestQueue(strRequest);

    }

    public void updateSQLite(String response){

        ArrayList<HashMap<String, String>> usersynclist;
        usersynclist = new ArrayList<HashMap<String, String>>();

        // Create GSON object
        Gson gson = new GsonBuilder().create();
        try {
            // Extract JSON array from the response
            JSONArray arr = new JSONArray(response.toString());

            System.out.println(arr.length());

            // If no of array elements is not zero
            if(arr.length() != 0){

                // Loop through each array element, get JSON object which has userid and username
                for (int i = 0; i < arr.length(); i++) {

                    // Get JSON object
                    JSONObject obj = (JSONObject) arr.get(i);

                    System.out.println(obj.get("userId"));
                    System.out.println(obj.get("userName"));

                    // DB QueryValues Object to insert into SQLite
                    queryValues = new HashMap<String, String>();

                    // Add userID extracted from Object
                    queryValues.put("userId", obj.get("userId").toString());

                    // Add userName extracted from Object
                    queryValues.put("userName", obj.get("userName").toString());

                    // Insert User into SQLite DB
                    controller.insertUser(queryValues);

                    HashMap<String, String> map = new HashMap<String, String>();

                    // Add status for each User in Hashmap
                    map.put("Id", obj.get("userId").toString());
                    map.put("status", "1");
                    usersynclist.add(map);
                }

                // Inform Remote MySQL DB about the completion of Sync activity by passing Sync status of Users
                updateMySQLSyncSts(gson.toJson(usersynclist));

                // Reload the Main Activity
                reloadActivity();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Method to inform remote MySQL DB about completion of Sync activity
    public void updateMySQLSyncSts(String json) {

        System.out.println(json);

        final String sJson = json;

        String url = "http://192.168.57.1/mysqlsqlitesync/updatesyncsts.php";

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Check your log cat for JSON response
                        Log.d(TAG, response.toString());

                        Toast.makeText(getApplicationContext(),	"MySQL DB has been informed about Sync activity", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("syncsts", sJson);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance()
                .addToRequestQueue(strRequest);
        
    }

    // Reload MainActivity
    public void reloadActivity() {
        Intent objIntent = new Intent(this, SyncMainActivity.class);
        startActivity(objIntent);
    }

}
