package com.example.abcd.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abcd.myapplication.DB.DatabaseActivity;
import com.example.abcd.myapplication.FABDemo.FABActivity;
import com.example.abcd.myapplication.RetrofitDemo.RetrofitDemoActivity;
import com.example.abcd.myapplication.SpinnerDemo.SpinnerActivity;
import com.example.abcd.myapplication.SyncDemo.SyncMainActivity;
import com.example.abcd.myapplication.date_picker.DatePickerActivity;
import com.example.abcd.myapplication.mysqlDemo.MySQLDemoActivity;
import com.example.abcd.myapplication.send_CR.CRActivity;
import com.example.abcd.myapplication.shared_preferences.SharedPreferencesActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bDP, bDB, bSP, bSync, bCR, bSpinner, bFAB, bRF, bMySQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bDP = (Button) findViewById(R.id.btnDP);
        bDB = (Button) findViewById(R.id.btnDB);
        bSP = (Button) findViewById(R.id.btnSP);
        bSync = (Button) findViewById(R.id.btnSync);
        bCR = (Button) findViewById(R.id.btnCR);
        bSpinner = (Button) findViewById(R.id.btnSpin);
        bFAB = (Button) findViewById(R.id.btnFAB);
        bRF = (Button) findViewById(R.id.btnRF);
        bMySQL = (Button) findViewById(R.id.btnMySQL);

        bDP.setOnClickListener(this);
        bDB.setOnClickListener(this);
        bSP.setOnClickListener(this);
        bSync.setOnClickListener(this);
        bCR.setOnClickListener(this);
        bSpinner.setOnClickListener(this);
        bFAB.setOnClickListener(this);
        bRF.setOnClickListener(this);
        bMySQL.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnDP :
                startActivity(new Intent(getApplicationContext(), DatePickerActivity.class));
                break;

            case R.id.btnDB :
                startActivity(new Intent(getApplicationContext(), DatabaseActivity.class));
                break;

            case R.id.btnSP :
                startActivity(new Intent(getApplicationContext(), SharedPreferencesActivity.class));
                break;

            case R.id.btnSync :
                startActivity(new Intent(getApplicationContext(), SyncMainActivity.class));
                break;

            case R.id.btnCR :
                startActivity(new Intent(getApplicationContext(), CRActivity.class));
                break;

            case R.id.btnSpin :
                startActivity(new Intent(getApplicationContext(), SpinnerActivity.class));
                break;

            case R.id.btnFAB :
                startActivity(new Intent(getApplicationContext(), FABActivity.class));
                break;

            case R.id.btnRF :
                startActivity(new Intent(getApplicationContext(), RetrofitDemoActivity.class));
                break;

            case R.id.btnMySQL :
                startActivity(new Intent(getApplicationContext(), MySQLDemoActivity.class));
                break;

            default:
                break;
        }

    }
}
