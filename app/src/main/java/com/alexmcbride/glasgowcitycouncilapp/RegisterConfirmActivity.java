package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterConfirmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.comments_text_title);
        actionBar.setSubtitle(R.string.comments_text_registered);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterConfirmActivity.class);
    }

    public void onClickLogin(View view) {
        startActivity(LoginActivity.newIntent(this));
    }
}
