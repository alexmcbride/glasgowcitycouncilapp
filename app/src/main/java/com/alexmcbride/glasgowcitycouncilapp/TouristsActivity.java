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

import com.alexmcbride.glasgowcitycouncilapp.DbSchema.MuseumTable;

public class TouristsActivity extends AppCompatActivity {
    private ListView mListMuseums;
    private CursorAdapter mMuseumsAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, TouristsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourists);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tourists");
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

        mMuseumsAdapter = db.getMuseums(this, android.R.layout.simple_list_item_1,
                new String[]{MuseumTable.Columns.NAME},
                new int[]{android.R.id.text1});

        mListMuseums.setAdapter(mMuseumsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Cursor cursor = mMuseumsAdapter.getCursor();
        if (cursor != null) {
            cursor.close();
        }
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickMuseums(View view) {
    }

    public void onClickLibraries(View view) {
        Intent intent = LibrariesActivity.newIntent(this);
        startActivity(intent);
    }

    public void onClickParks(View view) {
        Intent intent = ParksActivity.newIntent(this);
        startActivity(intent);
    }
}
