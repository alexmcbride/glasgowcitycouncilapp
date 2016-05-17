package com.alexmcbride.glasgowcitycouncilapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecyclingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tourists");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
