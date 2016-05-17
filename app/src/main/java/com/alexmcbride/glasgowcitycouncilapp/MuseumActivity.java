package com.alexmcbride.glasgowcitycouncilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MuseumActivity extends AppCompatActivity {
    private static final String EXTRA_MUSEUM_ID = "MUSEUM_ID";
    private TextView mTextName;
    private TextView mTextDescription;
    private ImageView mImageMuseum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tourists");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mTextName = (TextView)findViewById(R.id.textName);
        mTextDescription = (TextView)findViewById(R.id.textDescription);
        mImageMuseum = (ImageView)findViewById(R.id.imageMuseum);

        // load museum from DB.
        Intent intent = getIntent();
        long id = intent.getLongExtra(EXTRA_MUSEUM_ID, -1);
        if (id > -1) {
            DbHandler db = new DbHandler(this);
            Museum museum = db.getMuseum(id);
            if (museum != null) {
                int resource = getResources().getIdentifier(museum.getImageSrc(), "drawable", getPackageName());
                mTextName.setText(museum.getName());
                mTextDescription.setText(museum.getDescription());
                mImageMuseum.setImageResource(resource);
            }
        }
    }

    public static Intent newIntent(Context context, long museumId) {
        Intent intent = new Intent(context, MuseumActivity.class);
        intent.putExtra(EXTRA_MUSEUM_ID, museumId);
        return intent;
    }

    public void onClickBack(View view) {
        finish();
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
