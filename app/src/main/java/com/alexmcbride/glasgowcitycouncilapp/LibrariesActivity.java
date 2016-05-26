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
        actionBar.setTitle(R.string.tourists_text_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LibrariesActivity.class);
    }

    public void onClickMuseums(View view) {
        startActivity(TouristsActivity.newIntent(this));
    }

    public void onClickParks(View view) {
        startActivity(ParksActivity.newIntent(this));
    }
}
