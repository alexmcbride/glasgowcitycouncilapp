package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentsActivity extends AppCompatActivity {
    private static final String EXTRA_JUST_LOGGED_IN = "JUST_LOGGED_IN";
    private CursorAdapter mCursorAdapter;
    private TextView mTextWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // update actionbar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.comments_text_title);
        actionBar.setSubtitle(R.string.comments_text_view_posts);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // init db.
        DbHandler db = new DbHandler(this);
        Cursor cursor = db.getPostsCursor();
        mCursorAdapter = new PostCursorAdapter(this, cursor);

        // init widgets
        TextView textNoPosts = (TextView)findViewById(R.id.textNoPosts);
        ListView listPosts = ((ListView) findViewById(R.id.listPosts));
        listPosts.setAdapter(mCursorAdapter);
        listPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = ViewPostActivity.newIntent(CommentsActivity.this, id);
                startActivity(intent);
            }
        });

        // show/hide no posts message
        if (mCursorAdapter.getCount() == 0) {
            listPosts.setVisibility(View.GONE);
            textNoPosts.setVisibility(View.VISIBLE);
        }
        else {
            listPosts.setVisibility(View.VISIBLE);
            textNoPosts.setVisibility(View.GONE);
        }

        // get if user logged and and show/hide welcome message
        String username = MainActivity.getPrefsUsername(this);
        mTextWelcome = (TextView) findViewById(R.id.textWelcome);
        if (username == null) {
            mTextWelcome.setVisibility(View.GONE);
        }
        else {
            mTextWelcome.setText(getString(R.string.comments_welcome, username));
            mTextWelcome.setVisibility(View.VISIBLE);
        }

        // if we're redirecting from login screen then show just logged in message.
        boolean justLoggedIn = getIntent().getBooleanExtra(EXTRA_JUST_LOGGED_IN, false);
        if (justLoggedIn) {
            Toast.makeText(CommentsActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // cleanup cursor on exit...
        if (mCursorAdapter != null) {
            mCursorAdapter.getCursor().close();
        }
    }

    public void onClickNewPost(View view) {
        String username = MainActivity.getPrefsUsername(this);

        // if we're not logged in then redirect to login screen, otherwise go to new post screen.
        if (username == null) {
            startActivity(LoginActivity.newIntent(this));
        }
        else {
            startActivity(NewPostActivity.newIntent(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle options menu.
        switch (item.getItemId()) {
            case R.id.action_logout: {
                // log user out, or show error if not logged in.
                String username = MainActivity.getPrefsUsername(this);
                if (username == null) {
                    Toast.makeText(CommentsActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    // log user out.
                    MainActivity.clearPrefsUsername(this);
                    mTextWelcome.setVisibility(View.GONE);
                    Toast.makeText(CommentsActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntent(Context context) {
        return newIntent(context, false);
    }

    public static Intent newIntent(Context context, boolean justLoggedIn) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(EXTRA_JUST_LOGGED_IN, justLoggedIn);
        return intent;
    }

    private class PostCursorAdapter extends CursorAdapter {
        public PostCursorAdapter(Context context, Cursor c) {
            super(context, c, -1);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // create new view and update it.
            View view = getLayoutInflater().inflate(R.layout.list_item_post, null);
            updateView(view, cursor);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // update exiting view.
            updateView(view, cursor);
        }

        private void updateView(View view, Cursor cursor){
            // get widgets
            TextView textTitle = (TextView)view.findViewById(R.id.textTitle);
            TextView textPosted = (TextView)view.findViewById(R.id.textPosted);
            TextView textCommentCount = (TextView)view.findViewById(R.id.textCommentCount);

            // get db.
            DbCursorWrapper wrapper = new DbCursorWrapper(cursor);
            Post post = wrapper.getPost();

            // get humanised time.
            CharSequence time = DateUtils.getRelativeTimeSpanString(post.getPosted().getTime(),
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS);

            // update widgets
            textTitle.setText(post.getTitle());
            textPosted.setText(getString(R.string.post_text_posted, time, post.getUsername()));
            textCommentCount.setText(getString(R.string.comments_text_comment_count, post.getCommentCount()));
        }
    }
}
