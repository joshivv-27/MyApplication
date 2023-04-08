package com.example.abcd.myapplication.date_picker;

import android.app.DatePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.abcd.myapplication.R;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity {

    EditText date;

    DatePickerDialog picker;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        date = (EditText) findViewById(R.id.edtDate);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar newCalendar = Calendar.getInstance();

                year = newCalendar.get(Calendar.YEAR);
                month = newCalendar.get(Calendar.MONTH);
                day = newCalendar.get(Calendar.DAY_OF_MONTH);

                picker = new DatePickerDialog(v.getContext(), new PickDate(), year, month, day);

                picker.show();

            }
        });
    }

    private class PickDate implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            view.updateDate(year, monthOfYear, dayOfMonth);

            date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
            picker.hide();
        }

    }

}