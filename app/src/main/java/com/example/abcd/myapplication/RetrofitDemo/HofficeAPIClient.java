package com.example.abcd.myapplication.RetrofitDemo;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by abcd on 2/16/17.
 */

public interface HofficeAPIClient {

    @GET("/test.json")
    Call<JsonObject> getData();

}
