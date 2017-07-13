package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
public class Settings extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
    public void OnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
