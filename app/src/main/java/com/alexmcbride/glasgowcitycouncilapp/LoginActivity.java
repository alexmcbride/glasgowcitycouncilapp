package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String LOG = "LoginActivity";

    private EditText mEditUsername;
    private EditText mEditPassword;
    private TextView mTextErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.comments_text_title));
        actionBar.setSubtitle(getString(R.string.comments_text_login));
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEditUsername = (EditText)findViewById(R.id.editUsername);
        mEditPassword = (EditText)findViewById(R.id.editPassword);
        mTextErrorMessage = (TextView)findViewById(R.id.textErrorMessage);
    }

    public void onClickLogin(View view) {
        String username = mEditUsername.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();

        if (username.length() == 0 || password.length() == 0) {
            mTextErrorMessage.setText(R.string.login_text_password_blank);
            return;
        }

        DbHandler db = new DbHandler(this);
        if (db.validateLogin(username, password)) {
            Log.d(LOG, "logged in: " + username);

            MainActivity.setPrefsUsername(this, username);

            Intent intent = CommentsActivity.newIntent(this, true);
            startActivity(intent);
        }
        else {
            mTextErrorMessage.setText(R.string.login_text_invalid_login);
        }
    }

    public void onClickRegister(View view) {
        Intent intent = RegisterActivity.newIntent(this);
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}

