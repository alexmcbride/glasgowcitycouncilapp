package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comments");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginConfirmActivity.class);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickContinue(View view) {
        Intent intent = NewPostActivity.newIntent(this);
        startActivity(intent);
    }
}
