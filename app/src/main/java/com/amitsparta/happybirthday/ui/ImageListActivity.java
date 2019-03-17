package com.amitsparta.happybirthday.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.amitsparta.happybirthday.HelperClasses.FileIO;
import com.amitsparta.happybirthday.HelperClasses.ImageDetector;
import com.amitsparta.happybirthday.R;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    public static final String FOLDER_INTENT_EXTRA = "folderName";
    private final String FOLDER_BUNDLE_KEY = "bundleKey";
    private ArrayList<Folder> folderList;
    private ImageAdapter adapter;
    private Folder folder;
    private ProgressBar progressBar;

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

        folderList = new ArrayList<Folder>();
        folderList.add(folder);
        ArrayList imageList = folderList.get(0).getImages();
        RecyclerView recyclerView = findViewById(R.id.image_list);
        adapter = new ImageAdapter(getApplicationContext(), imageList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        loadPreviousFolders();

        scanForMoreFolders();

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

    @SuppressLint("StaticFieldLeak")
    private void loadPreviousFolders() {
        if (FileIO.hasFolderList(folderList.get(0), Image.IMAGE_MODE)) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    if (folderList == null || folderList.isEmpty()) {
                        folderList = new ArrayList<Folder>();
                        folderList.add(folder);
                    }
                    ArrayList<Folder> tempArr = FileIO.getFolderFromFile();
                    if (tempArr != null) {
                        folderList.addAll(tempArr);
                    }
                    if (folderList == null) {
                        Log.i("Folder Activity", "Null returned.");
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyItemInserted(folderList.size());
                }
            }.execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void scanForMoreFolders() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                ArrayList<Folder> tempArr = new ArrayList();
                tempArr.addAll(ImageDetector.collectImages(folder.getFolderPath()));
                if (!tempArr.equals(folderList)) {
                    folderList.clear();
                    folderList.addAll(tempArr);
                    if (folderList != null && !folderList.isEmpty()) {
                        FileIO.clearFile(folderList.get(0), Image.IMAGE_MODE);

                        if (FileIO.writeFolderToFile(folderList, Image.IMAGE_MODE)) {
                            Log.i("Folder Activity", "Written to file");
                        } else {
                            Log.i("Folder Activity", "Unable to write to file");
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean bool) {
                if (bool) {
                    adapter.notifyItemInserted(folderList.get(0).getImages().size());
                    adapter.notifyDataSetChanged();
                }
                if (folderList.size() > 0)
                    progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }
}
