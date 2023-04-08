package com.example.abcd.myapplication.SpinnerDemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abcd.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity {

    List<String> list;

    ArrayAdapter<String> adapter;

    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        spn = (Spinner) findViewById(R.id.spinner);

        list = new ArrayList<>();

        genList();

        initSpinner();

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Position : " + position + ", ID : " + id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*
        spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Position : " + position + ", ID : " + id, Toast.LENGTH_LONG).show();

            }
        });

         */
    }

    private void initSpinner() {

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spn.setAdapter(adapter);
    }

    private void genList() {

        list.add("one");
        list.add("two");
        list.add("three");
    }
}
