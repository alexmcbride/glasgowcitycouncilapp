package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class NewPostActivity extends AppCompatActivity {
    private static final String TAG = "NewPostActivity";
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mUsername = MainActivity.getPrefsUsername(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comments");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickAddPost(View view) {
        String title = ((EditText)findViewById(R.id.editTitle)).getText().toString().trim();
        String comment = ((EditText)findViewById(R.id.editComment)).getText().toString().trim();

        if (title.length() == 0) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (comment.length() == 0) {
            Toast.makeText(this, "Comment is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Post post = new Post();
        post.setTitle(title);
        post.setUsername(mUsername);
        post.setPosted(new Date());
        post.setContent(comment);

        DbHandler db = new DbHandler(this);
        db.addPost(post);

        Intent intent = ViewPostActivity.newIntent(this, post.getId());
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NewPostActivity.class);
    }
}
