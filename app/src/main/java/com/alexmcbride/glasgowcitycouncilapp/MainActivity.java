package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "GCC_PREFS";
    private static final String PREFS_USERNAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");

        // show logged in text.
        TextView textLoggedIn = (TextView) findViewById(R.id.textLoggedIn);
        String username = getPrefsUsername(this);
        if (username == null) {
            textLoggedIn.setVisibility(View.GONE);
        }
        else {
            textLoggedIn.setVisibility(View.VISIBLE);
            textLoggedIn.setText(getString(R.string.main_text_logged_in, username));
        }
    }

    public void onClickNews(View view) {
        Intent intent = NewsActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickTourists(View view) {
        Intent intent = TouristsActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickBins(View view) {
        Intent intent = CollectionActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickComments(View view) {
        Intent intent = CommentsActivity.newIntent(this);
        startActivity(intent);
    }

    public static SharedPreferences getSharedPrefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(SHARED_PREFS_NAME, 0);
    }

    public static String getPrefsUsername(Context context) {
        SharedPreferences prefs = getSharedPrefs(context);
        return prefs.getString(PREFS_USERNAME, null);
    }

    public static void setPrefsUsername(Context context, String username) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_USERNAME, username);
        editor.apply();
    }

    public static void clearPrefsUsername(Context context) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PREFS_USERNAME);
        editor.apply();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
