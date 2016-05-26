package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        actionBar.setTitle(R.string.bins_text_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ReportProblemActivity.class);
    }

    public void onClickCollections(View view) {
        startActivity(CollectionActivity.newIntent(this));
    }

    public void onClickRecycling(View view) {
        startActivity(RecyclingActivity.newIntent(this));
    }

    public void onClickReportProblem(View view) {
        String url = "https://iweb.itouchvision.com/portal/f?p=citizen:login:::NO:RP:UID:B4B7F31D4CTN301SC4342E04V0007DF010G0683E";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
