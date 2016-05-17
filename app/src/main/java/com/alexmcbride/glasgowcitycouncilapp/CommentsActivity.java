package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final String TAG = "CommentsActivity";
    private CursorAdapter mCursorAdapter;
    private ListView mListPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comments");
        actionBar.setDisplayHomeAsUpEnabled(true);

        DbHandler db = new DbHandler(this);
        Cursor cursor = db.getPostsCursor();
        mCursorAdapter = new PostCursorAdapter(this, cursor);

        mListPosts = ((ListView)findViewById(R.id.listPosts));
        mListPosts.setAdapter(mCursorAdapter);
        mListPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = ViewPostActivity.newIntent(CommentsActivity.this, id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mCursorAdapter != null) {
            mCursorAdapter.getCursor().close();
        }
    }

    public void onClickNewPost(View view) {
        String username = MainActivity.getPrefsUsername(this);

        // check if we're logged in, if not then redirect to login.
        if (username == null) {
            Intent intent = LoginActivity.newIntent(this);
            startActivity(intent);
        }
        else {
            Intent intent = NewPostActivity.newIntent(this);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comments, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String username = MainActivity.getPrefsUsername(this);
        switch (item.getItemId()) {
            case R.id.action_logout: {
                if (username == null) {
                    Toast.makeText(CommentsActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = LogoutActivity.newIntent(this);
                    startActivity(intent);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CommentsActivity.class);
    }

    private class PostCursorAdapter extends CursorAdapter {
        public PostCursorAdapter(Context context, Cursor c) {
            super(context, c, -1);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_item_post, null);
            updateView(view, cursor);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            updateView(view, cursor);
        }

        private void updateView(View view, Cursor cursor){
            TextView textTitle = (TextView)view.findViewById(R.id.textTitle);
            TextView textPosted = (TextView)view.findViewById(R.id.textPosted);

            DbCursorWrapper wrapper = new DbCursorWrapper(cursor);
            Post post = wrapper.getPost();

            CharSequence time = DateUtils.getRelativeTimeSpanString(post.getPosted().getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

            textTitle.setText(post.getTitle());
            textPosted.setText(getString(R.string.post_text_posted, time, post.getUsername()));
        }
    }
}
