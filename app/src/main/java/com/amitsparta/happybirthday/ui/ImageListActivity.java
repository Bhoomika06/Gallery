package com.amitsparta.happybirthday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.amitsparta.happybirthday.Adapters.ImageAdapter;
import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.R;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    public static final String FOLDER_INTENT_EXTRA = "folderName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);

        Intent intent = getIntent();
        Folder folder = null;
        if (intent.hasExtra(FOLDER_INTENT_EXTRA)) {
            folder = (Folder) intent.getSerializableExtra(FOLDER_INTENT_EXTRA);
        }

        ArrayList imageList = folder.getImages();
        RecyclerView recyclerView = findViewById(R.id.image_list);
        ImageAdapter adapter = new ImageAdapter(getApplicationContext(), imageList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
