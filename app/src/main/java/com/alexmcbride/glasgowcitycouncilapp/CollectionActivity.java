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
        actionBar.setTitle(R.string.bins_text_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickRecycling(View view) {
        startActivity(RecyclingActivity.newIntent(this));
    }

    public void onClickProblems(View view) {
        startActivity(ReportProblemActivity.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CollectionActivity.class);
    }
}
