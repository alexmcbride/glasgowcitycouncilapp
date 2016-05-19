package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RecyclingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.bins_text_title));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickCollections(View view) {
        Intent intent = CollectionActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickProblems(View view) {
        Intent intent = ReportProblemActivity.newIntent(this);
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RecyclingActivity.class);
    }
}
