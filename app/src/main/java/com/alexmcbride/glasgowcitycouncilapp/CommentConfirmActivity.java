package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class CommentConfirmActivity extends AppCompatActivity {
    private static final String EXTRA_POST_ID = "POST_ID";

    private long mPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_confirm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.comments_text_title));
        actionBar.setDisplayHomeAsUpEnabled(true);

        mPostId = getIntent().getLongExtra(EXTRA_POST_ID, -1);
    }

    public void onClickContinue(View view) {
        handleContinue();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // overide the actionbar back button, we need to do this to provide the post id in the
        // intent.
        switch (item.getItemId()) {
            case android.R.id.home:
                handleContinue();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleContinue() {
        Intent intent = ViewPostActivity.newIntent(this, mPostId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public static Intent newIntent(Context context, long postId) {
        Intent intent = new Intent(context, CommentConfirmActivity.class);
        intent.putExtra(EXTRA_POST_ID, postId);
        return intent;
    }
}
