package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LibrariesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tourists");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LibrariesActivity.class);
        return intent;
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickMuseums(View view) {
        Intent intent = TouristsActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickParks(View view) {
        Intent intent = ParksActivity.newIntent(this);
        startActivity(intent);
    }
}
