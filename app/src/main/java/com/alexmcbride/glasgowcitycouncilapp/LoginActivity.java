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
    private static final int REQUEST_REGISTER = 1;
    private static final String EXTRA_REGISTER_SUCCESS = "REGISTER_SUCCESS";
    private static final String EXTRA_REDIRECT = "REDIRECT";
    private static final String LOG = "LoginActivity";

    private EditText mEditUsername;
    private EditText mEditPassword;
    private TextView mTextErrorMessage;
    private boolean mRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        mRedirect = intent.getBooleanExtra(EXTRA_REDIRECT, false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comments");
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

            Intent intent = CommentsActivity.newIntent(this);
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

    public void onClickBack(View view) {
        finish();
    }
}

