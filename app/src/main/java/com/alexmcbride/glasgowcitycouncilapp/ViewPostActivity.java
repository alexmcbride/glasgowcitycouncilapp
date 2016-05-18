package com.alexmcbride.glasgowcitycouncilapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class ViewPostActivity extends AppCompatActivity {
    private static final String EXTRA_POST_ID = "POST_ID";
    private static final String EXTRA_COMMENT_ID = "COMMENT_ID";
    private TextView mTextTitle;
    private TextView mTextMeta;
    private TextView mTextComment;
    private ListView mListComments;
    private TextView mTextNoComments;
    private long mPostId;
    private long mCommentId;
    private DbHandler mDbHandler;
    private CommentCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.comments_text_title));
        actionBar.setSubtitle(getString(R.string.comments_text_view_post));
        actionBar.setDisplayHomeAsUpEnabled(true);

        mTextTitle = (TextView) findViewById(R.id.textTitle);
        mTextMeta = (TextView) findViewById(R.id.textMeta);
        mTextComment = (TextView) findViewById(R.id.textComment);
        mListComments = (ListView) findViewById(R.id.listComments);
        mTextNoComments = (TextView) findViewById(R.id.textNoComments);

        Intent intent = getIntent();
        mPostId = intent.getLongExtra(EXTRA_POST_ID, -1);
        mCommentId = intent.getLongExtra(EXTRA_COMMENT_ID, -1);

        mDbHandler = new DbHandler(this);
        Post post = mDbHandler.getPost(mPostId);

        CharSequence time = DateUtils.getRelativeTimeSpanString(post.getPosted().getTime(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS);

        mTextTitle.setText(post.getTitle());
        mTextMeta.setText(getString(R.string.view_post_text_posted, time, post.getUsername()));
        mTextComment.setText(post.getContent());

        Cursor cursor = mDbHandler.getCommentsCursor(mPostId);
        mCursorAdapter = new CommentCursorAdapter(this, cursor);
        mListComments.setAdapter(mCursorAdapter);

        // show/hide no comments message.
        if (mCursorAdapter.getCount() == 0) {
            mListComments.setVisibility(View.GONE);
            mTextNoComments.setVisibility(View.VISIBLE);
        } else {
            mListComments.setVisibility(View.VISIBLE);
            mTextNoComments.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mCursorAdapter != null) {
            mCursorAdapter.getCursor().close();
        }
    }

    public static Intent newIntent(Context context, long postId) {
        Intent intent = new Intent(context, ViewPostActivity.class);
        intent.putExtra(EXTRA_POST_ID, postId);
        return intent;
    }

    public static Intent newIntent(Context context, long postId, long commentId) {
        Intent intent = newIntent(context, postId);
        intent.putExtra(EXTRA_COMMENT_ID, commentId);
        return intent;
    }

    public void onClickNewComment(View view) {
        // login if not already.
        String username = MainActivity.getPrefsUsername(this);
        if (username == null) {
            Intent intent = LoginActivity.newIntent(this);
            startActivity(intent);
        }
        else {
            Intent intent = NewCommentActivity.newIntent(this, mPostId);
            startActivity(intent);
        }
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

            // if this is the comment we've been passed then highlight it.
//            if (mCommentId == comment.getId()) {
//                RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.relativeLayout);
//                layout.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
//                layout.invalidate();
//            }
        }
    }
}
