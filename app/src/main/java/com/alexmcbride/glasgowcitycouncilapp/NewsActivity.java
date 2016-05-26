package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NewsActivity extends AppCompatActivity {
    private SimpleCursorAdapter mSimpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.news_text_title);

        DbHandler db = new DbHandler(this);
        Cursor cursor = db.getArticlesCursor();

        ListView listArticles = (ListView)findViewById(R.id.listArticles);
        listArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = ArticleActivity.newIntent(NewsActivity.this, id);
                startActivity(intent);
            }
        });

        mSimpleCursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{DbSchema.ArticleTable.Columns.TITLE},
                new int[]{android.R.id.text1},
                0);
        listArticles.setAdapter(mSimpleCursorAdapter);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NewsActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSimpleCursorAdapter != null) {
            mSimpleCursorAdapter.getCursor().close();
        }
    }
}
