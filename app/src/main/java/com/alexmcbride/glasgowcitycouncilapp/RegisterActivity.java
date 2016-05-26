package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEditUsername;
    private EditText mEditPassword;
    private EditText mEditFirstName;
    private EditText mEditLastName;
    private EditText mEditEmailAddress;
    private TextView mTextErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.comments_text_title);
        actionBar.setSubtitle(R.string.comments_text_register);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEditUsername = (EditText)findViewById(R.id.editUsername);
        mEditPassword = (EditText)findViewById(R.id.editPassword);
        mEditFirstName = (EditText)findViewById(R.id.editFirstName);
        mEditLastName = (EditText)findViewById(R.id.editLastName);
        mEditEmailAddress = (EditText)findViewById(R.id.editEmailAddress);
        mTextErrorMessage = (TextView)findViewById(R.id.textErrorMessage);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    public void onClickRegister(View view) {
        String username = mEditUsername.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        String firstName = mEditFirstName.getText().toString().trim();
        String lastName = mEditLastName.getText().toString().trim();
        String emailAddress = mEditEmailAddress.getText().toString().trim();

        if (username.length() == 0) {
            mTextErrorMessage.setText(R.string.register_text_username_blank);
            return;
        }

        if (password.length() == 0) {
            mTextErrorMessage.setText(R.string.register_text_password_blank);
            return;
        }

        if (firstName.length() == 0) {
            mTextErrorMessage.setText(R.string.register_text_first_name_blank);
            return;
        }

        if (lastName.length() == 0) {
            mTextErrorMessage.setText(R.string.register_text_last_name_blank);
            return;
        }

        if (emailAddress.length() == 0) {
            mTextErrorMessage.setText(R.string.register_text_email_address_blank);
            return;
        }

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);

        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(emailAddress);

        // check user isn't already registered
        DbHandler db = new DbHandler(this);
        if (!db.userExists(user.getLogin().getUsername())) {
            db.addLogin(login);
            db.addUser(user);

            startActivity(RegisterConfirmActivity.newIntent(this));
        }
        else {
            mTextErrorMessage.setText(R.string.register_text_username_exists);
        }
    }

    public void onClickClear(View view) {
        mEditUsername.setText("");
        mEditPassword.setText("");
        mEditFirstName.setText("");
        mEditLastName.setText("");
        mEditEmailAddress.setText("");
        mTextErrorMessage.setText("");
    }
}
