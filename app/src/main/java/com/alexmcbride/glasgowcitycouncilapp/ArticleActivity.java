package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ArticleActivity extends AppCompatActivity {
    private static final String EXTRA_ARTICLE_ID = "ARTICLE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.news_text_title);

        // get article.
        DbHandler db = new DbHandler(this);
        long articleId = getIntent().getLongExtra(EXTRA_ARTICLE_ID, -1);
        Article article = db.getArticle(articleId);

        // get widgets
        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        TextView textContent = (TextView) findViewById(R.id.textContent);

        // update ui.
        textTitle.setText(article.getTitle());
        textContent.setText(article.getContent());
    }

    public static Intent newIntent(Context context, long id) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, id);
        return intent;
    }

    public void onClickComment(View view) {
        startActivity(CommentsActivity.newIntent(this));
    }
}
