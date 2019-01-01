package com.amitsparta.happybirthday;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.amitsparta.happybirthday.Adapters.FolderAdapter;
import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.HelperClasses.ImageScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class FolderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HashSet<Folder>> {

    private ArrayList folderList;
    private FolderAdapter folderAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }

        progressBar = findViewById(R.id.progress_bar);

        RecyclerView recyclerView = findViewById(R.id.folder_list);

        folderList = new ArrayList();
        folderAdapter = new FolderAdapter(getApplicationContext(), folderList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(folderAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<HashSet<Folder>> onCreateLoader(int id, @Nullable Bundle args) {
        File file = new File(Folder.ABSOLUTE_FILE_PATH);
        Log.i("LoaderInfo", "Created");
        return new ImageScanner(getApplicationContext(), file);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<HashSet<Folder>> loader, HashSet<Folder> data) {
        Log.i("LoaderInfo", "size " + folderList.size());
        folderList.clear();
        folderList.addAll(data);
        Log.i("LoaderInfo", "size " + folderList.size());

        progressBar.setVisibility(View.GONE);
        Log.i("LoaderInfo", "Ended");
        folderAdapter.notifyItemInserted(folderList.size());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<HashSet<Folder>> loader) {
        folderAdapter = null;
        Log.i("LoaderInfo", "Reset ho gaya");
    }
}