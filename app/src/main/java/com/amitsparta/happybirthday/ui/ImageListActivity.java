package com.amitsparta.happybirthday.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.amitsparta.happybirthday.Adapters.ImageAdapter;
import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.DataFiles.Image;
import com.amitsparta.happybirthday.HelperClasses.BackgroundImageScanner;
import com.amitsparta.happybirthday.HelperClasses.FileIO;
import com.amitsparta.happybirthday.R;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    public static final String FOLDER_INTENT_EXTRA = "folderName";
    private final String FOLDER_BUNDLE_KEY = "bundleKey";
    static boolean NeedReloading = true;
    private ImageAdapter adapter;
    private Folder folder;
    private ProgressBar progressBar;
    private ArrayList<Image> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);

        Intent intent = getIntent();
        if (intent.hasExtra(FOLDER_INTENT_EXTRA)) {
            folder = (Folder) intent.getSerializableExtra(FOLDER_INTENT_EXTRA);
        } else if (savedInstanceState != null ||
                savedInstanceState.getSerializable(FOLDER_BUNDLE_KEY) != null) {
            folder = (Folder) savedInstanceState.getSerializable(FOLDER_BUNDLE_KEY);
        }

        imageList = new ArrayList();
        RecyclerView recyclerView = findViewById(R.id.image_list);
        adapter = new ImageAdapter(getApplicationContext(), imageList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (outState == null) {
            outState = new Bundle();
        }
        outState.putSerializable(FOLDER_BUNDLE_KEY, folder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ImageListActivity.NeedReloading) {
            scanForMoreFolders();
            ImageListActivity.NeedReloading = false;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void scanForMoreFolders() {
        BackgroundImageScanner imageScanner = new BackgroundImageScanner(folder.getFolderPath(), Image.IMAGE_MODE);
        imageScanner.getImageList().observe(this, new Observer<ArrayList<Image>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Image> images) {
                ArrayList<Image> tempArr = new ArrayList(images);

                imageList.clear();
                imageList.addAll(tempArr);
                FileIO.clearFile(folder, Image.IMAGE_MODE);

                if (FileIO.writeFolderToFile(folder, Image.IMAGE_MODE)) {
                    Log.i("Image Activity", "Written to file");
                } else {
                    Log.i("Image Activity", "Unable to write to file");
                }
                adapter.notifyItemInserted(imageList.size());
                adapter.notifyDataSetChanged();

                if (imageList.size() > 0)
                    progressBar.setVisibility(View.GONE);
            }
        });
    }
}
