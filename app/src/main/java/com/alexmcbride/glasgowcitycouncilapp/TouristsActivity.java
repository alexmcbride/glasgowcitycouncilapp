package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.alexmcbride.glasgowcitycouncilapp.DbSchema.MuseumTable;

public class TouristsActivity extends AppCompatActivity {
    private ListView mListMuseums;
    private CursorAdapter mMuseumsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourists);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.tourists_text_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mListMuseums = (ListView) findViewById(R.id.listMuseums);
        mListMuseums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = MuseumActivity.newIntent(TouristsActivity.this, id);
                startActivity(intent);
            }
        });

        DbHandler db = new DbHandler(this);
        Cursor cursor = db.getMuseumsCursor();

        mMuseumsAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{MuseumTable.Columns.NAME},
                new int[]{android.R.id.text1},
                0);
        mListMuseums.setAdapter(mMuseumsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mMuseumsAdapter != null) {
            mMuseumsAdapter.getCursor().close();
        }
    }

    public void onClickLibraries(View view) {
        startActivity(LibrariesActivity.newIntent(this));
    }

    public void onClickParks(View view) {
        startActivity(ParksActivity.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, TouristsActivity.class);
    }
}
