package com.example.abcd.myapplication.send_CR;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.abcd.myapplication.R;

import java.util.List;

public class CRActivity extends AppCompatActivity {

    private static Activity sForegroundInstance;

    List<String> str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr);

    }

    public void onMakeError(View v) {
        str.add("error");
    }

    public static Activity getForegroundInstance()
    {
        return sForegroundInstance;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sForegroundInstance = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sForegroundInstance = this;
    }
}
