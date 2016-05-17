package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ParksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parks);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tourists");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ParksActivity.class);
    }

    public void onClickMuseums(View view) {
        Intent intent = TouristsActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickLibraries(View view) {
        Intent intent = LibrariesActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickBack(View view) {
        finish();
    }
}
