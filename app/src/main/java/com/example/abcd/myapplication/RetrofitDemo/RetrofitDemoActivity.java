package com.example.abcd.myapplication.RetrofitDemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abcd.myapplication.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDemoActivity extends AppCompatActivity {

    Button btn;
    TextView tv;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);

        btn = (Button) findViewById(R.id.buttonRF);
        tv = (TextView) findViewById(R.id.textViewRF);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hofficedemo1.ondemandcrm.co/public/Apptesting/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HofficeAPIClient client = retrofit.create(HofficeAPIClient.class);

        Call<JsonObject> jsonCall = client.getData();
        jsonCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String jsonString = response.body().toString();
                //Gson gson = new Gson();
                str = jsonString;
                //Log.i("respons", String.valueOf(de));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

//        client.getData();

        Log.d("str ", str);

       /* response.enqueue(new Callback<HofficeAPI>() {
            @Override
            public void onResponse(Call<HofficeAPI> call, Response<HofficeAPI> response) {

                int statusCode = response.code();
                HofficeAPI responseBody = response.body();

                Log.d("RF response","on statusCode" + statusCode + ", on responseBody" + responseBody);
            }

            @Override
            public void onFailure(Call<HofficeAPI> call, Throwable t) {

            }
        });*/
    }
}
