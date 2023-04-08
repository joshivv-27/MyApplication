package com.example.abcd.myapplication.SyncDemo.BR;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.abcd.myapplication.SyncDemo.service.MyService;
import com.example.abcd.myapplication.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaibhav on 1/27/17.
 */

public class SampleBC extends BroadcastReceiver {

    private String TAG = SampleBC.class.getSimpleName();

    static int noOfTimes = 0;

    Context mContext;

    // Method gets called when Broad Case is issued from MainActivity for every 10 seconds
    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;

        noOfTimes++;
        Toast.makeText(context, "BC Service Running for " + noOfTimes + " times", Toast.LENGTH_SHORT).show();

        String url = "http://192.168.57.1/mysqlsqlitesync/getdbrowcount.php";

        // Checks if new records are inserted in Remote MySQL DB to proceed with Sync operation
        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Check your log cat for response
                        Log.d(TAG, response.toString());

                        try {

                            JSONObject jRes = new JSONObject(response.toString());

                            Log.d("Response -- ", "" + jRes);

                            System.out.println(jRes.get("count"));

                            // If the count value is not zero, call MyService to display notification
                            if(jRes.getInt("count") != 0){

                                final Intent intnt = new Intent(mContext, MyService.class);

                                // Set unsynced count in intent data
                                intnt.putExtra("intntdata", "Unsynced Rows Count "+jRes.getInt("count"));

                                // Call MyService
                                mContext.startService(intnt);

                            } else {

                                Toast.makeText(mContext, "Sync not needed", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Adding request to request queue
        AppController.getInstance()
                .addToRequestQueue(strRequest);

    }

}
