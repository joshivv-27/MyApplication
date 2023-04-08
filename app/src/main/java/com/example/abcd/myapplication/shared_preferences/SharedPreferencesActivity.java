package com.example.abcd.myapplication.shared_preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.abcd.myapplication.R;

public class SharedPreferencesActivity extends AppCompatActivity {

    Switch switchOnOff;
    Button btnCheck;

    SharedPreferences sp;

    Editor myEditor;
    int PRIVATE_MODE = 0;

    private static final String spFileName = "mySP";

    private  static final String isOn = "isOnOrOff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        sp = this.getSharedPreferences(spFileName, PRIVATE_MODE);

        myEditor = sp.edit();

        btnCheck = (Button) findViewById(R.id.btnCheck);

        switchOnOff = (Switch) findViewById(R.id.swOnOff);

        switchOnOff.setChecked(false);

        setDefaults();

        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    myEditor.putBoolean(isOn,true);
                    myEditor.commit();

                } else  {

                    myEditor.putBoolean(isOn,false);
                    myEditor.commit();

                }

            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!sp.getBoolean(isOn,false)) {

                    Toast.makeText(getApplicationContext(), "Switch is OFF.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Switch is ON.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private void setDefaults() {

        myEditor.putBoolean(isOn,false);
        myEditor.commit();
    }
}
