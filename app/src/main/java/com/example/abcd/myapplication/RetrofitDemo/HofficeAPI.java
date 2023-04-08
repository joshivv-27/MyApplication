package com.example.abcd.myapplication.RetrofitDemo;

import com.google.gson.annotations.Expose;

/**
 * Created by abcd on 2/16/17.
 */

public class HofficeAPI {

    @Expose
    private String test;

    public HofficeAPI() {
    }

    public HofficeAPI(String test) {
        this.test = test;
    }
}
