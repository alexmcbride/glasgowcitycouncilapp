package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bins & Recycling");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onCollections(View view) {

    }

    public void onRecycling(View view) {

    }

    public void onProblems(View view) {

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CollectionActivity.class);
    }
}
