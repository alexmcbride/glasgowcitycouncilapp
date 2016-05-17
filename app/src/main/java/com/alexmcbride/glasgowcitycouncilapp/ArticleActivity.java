package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alexmcbride.glasgowcitycouncilapp.DbSchema.CommentTable;

public class ArticleActivity extends AppCompatActivity {
    private static final String EXTRA_ARTICLE_ID = "ARTICLE_ID";

    private TextView mTextTitle;
    private TextView mTextContent;

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("News");

        Intent intent = getIntent();
        long articleId = intent.getLongExtra(EXTRA_ARTICLE_ID, 0);

        DbHandler db = new DbHandler(this);

        // get article.
        mArticle = db.getArticle(articleId);

        mTextTitle = (TextView)findViewById(R.id.textTitle);
        mTextContent = (TextView)findViewById(R.id.textContent);

        mTextTitle.setText(mArticle.getTitle());
        mTextContent.setText(mArticle.getContent());
    }

    public static Intent newIntent(Context context, long id) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, id);
        return intent;
    }

    public void onClickBack(View view) {
        finish();
    }
}
