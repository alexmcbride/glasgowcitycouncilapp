package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class NewCommentActivity extends AppCompatActivity {
    private static final String EXTRA_POST_ID = "POST_ID";
    private static final String TAG = "NewCommentActivity";

    private long mPostId;
    private EditText mEditComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.comments_text_title));
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEditComment = (EditText)findViewById(R.id.editComment);

        mPostId = getIntent().getLongExtra(EXTRA_POST_ID, -1);
    }

    public static Intent newIntent(Context context, long postId) {
        Intent intent = new Intent(context, NewCommentActivity.class);
        intent.putExtra(EXTRA_POST_ID, postId);
        return intent;
    }

    public void onClickPostComment(View view) {
        String content = mEditComment.getText().toString();
        String username = MainActivity.getPrefsUsername(this);

        Comment comment = new Comment();
        comment.setPostId(mPostId);
        comment.setUsername(username);
        comment.setPosted(new Date());
        comment.setContent(content);

        DbHandler db = new DbHandler(this);
        db.addComment(comment);

        Intent intent = CommentConfirmActivity.newIntent(this, mPostId);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // overide the actionbar back button, we need to do this to provide the post id in the
        // intent.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = ViewPostActivity.newIntent(this, mPostId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
