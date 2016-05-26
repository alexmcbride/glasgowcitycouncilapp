package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPostActivity extends AppCompatActivity {
    private static final String EXTRA_POST_ID = "POST_ID";
    private static final String EXTRA_COMMENT_ADDED = "COMMENT_ADDED";
    private static final String EXTRA_POST_ADDED = "POST_ADDED";
    private long mPostId;
    private CommentCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        // setup actionbar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.comments_text_title);
        actionBar.setSubtitle(R.string.comments_text_view_post);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get widgets
        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        TextView textMeta = (TextView) findViewById(R.id.textMeta);
        TextView textComment = (TextView) findViewById(R.id.textComment);
        NonScrollListView listComments = (NonScrollListView) findViewById(R.id.listComments);
        TextView textNoComments = (TextView) findViewById(R.id.textNoComments);

        // get data from intent.
        Intent intent = getIntent();
        mPostId = intent.getLongExtra(EXTRA_POST_ID, -1);
        boolean commentAdded = intent.getBooleanExtra(EXTRA_COMMENT_ADDED, false);
        boolean postAdded = intent.getBooleanExtra(EXTRA_POST_ADDED, false);

        // get post from DB.
        DbHandler db = new DbHandler(this);
        Post post = db.getPost(mPostId);

        // get humanised time
        CharSequence time = DateUtils.getRelativeTimeSpanString(post.getPosted().getTime(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS);

        // update ui.
        textTitle.setText(post.getTitle());
        textMeta.setText(getString(R.string.view_post_text_posted, time, post.getUsername()));
        textComment.setText(post.getContent());

        // update comments listview.
        Cursor cursor = db.getCommentsCursor(mPostId);
        mCursorAdapter = new CommentCursorAdapter(this, cursor);
        listComments.setAdapter(mCursorAdapter);

        // show/hide no comments message.
        if (mCursorAdapter.getCount() == 0) {
            listComments.setVisibility(View.GONE);
            textNoComments.setVisibility(View.VISIBLE);
        }
        else {
            listComments.setVisibility(View.VISIBLE);
            textNoComments.setVisibility(View.GONE);
        }

        // make sure we scroll to the top of the scroll view on starting.
        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);

        // show messages about any new posts or comments made.
        if (commentAdded) {
            Toast.makeText(ViewPostActivity.this, R.string.comments_toast_new_comment, Toast.LENGTH_SHORT).show();
        }

        if (postAdded) {
            Toast.makeText(ViewPostActivity.this, R.string.comment_toast_new_post, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // cleanup cursor.
        if (mCursorAdapter != null) {
            mCursorAdapter.getCursor().close();
        }
    }

    public void onClickNewComment(View view) {
        // login if not already.
        String username = MainActivity.getPrefsUsername(this);
        if (username == null) {
            Intent intent = LoginActivity.newIntent(this);
            startActivity(intent);
        }
        else {
            startActivity(NewCommentActivity.newIntent(this, mPostId));
        }
    }

    public static Intent newIntent(Context context, long postId) {
        Intent intent = new Intent(context, ViewPostActivity.class);
        intent.putExtra(EXTRA_POST_ID, postId);
        return intent;
    }

    public static Intent newIntent(Context context, long postId, boolean postAdded, boolean commentAdded) {
        Intent intent = newIntent(context, postId);
        intent.putExtra(EXTRA_POST_ADDED, postAdded);
        intent.putExtra(EXTRA_COMMENT_ADDED, commentAdded);
        return intent;
    }

    private class CommentCursorAdapter extends CursorAdapter {
        public CommentCursorAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_item_comment, null);
            updateView(view, cursor);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            updateView(view, cursor);
        }

        private void updateView(View view, Cursor cursor) {
            DbCursorWrapper wrapper = new DbCursorWrapper(cursor);
            Comment comment = wrapper.getComment();

            TextView textComment = (TextView)view.findViewById(R.id.textComment);
            TextView textMeta = (TextView)view.findViewById(R.id.textMeta);

            CharSequence time = DateUtils.getRelativeTimeSpanString(comment.getPosted().getTime(),
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS);

            textComment.setText(comment.getContent());
            textMeta.setText(getString(R.string.comment_meta, time, comment.getUsername()));
        }
    }
}
