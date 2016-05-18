package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ReportProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.bins_text_title));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ReportProblemActivity.class);
    }

    public void onClickCollections(View view) {
        Intent intent = CollectionActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickRecycling(View view) {
        Intent intent = RecyclingActivity.newIntent(this);
        startActivity(intent);
    }
}
