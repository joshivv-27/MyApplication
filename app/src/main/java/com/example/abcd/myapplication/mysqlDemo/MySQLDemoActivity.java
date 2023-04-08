package com.example.abcd.myapplication.mysqlDemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.abcd.myapplication.R;
import com.example.abcd.myapplication.helper.MySQLDBHelper;

public class MySQLDemoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_demo);

        btn = (Button) findViewById(R.id.btnGetMySQL);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                new MySQLDBHelper();
            }
        }).start();

    }
}
