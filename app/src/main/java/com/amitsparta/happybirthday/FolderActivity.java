package com.amitsparta.happybirthday;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.amitsparta.happybirthday.Adapters.FolderAdapter;
import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.DataFiles.ImageScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class FolderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HashSet<Folder>> {

    private ArrayList folderList;
    private FolderAdapter folderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

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
        File file = new File("/storage/emulated/0");
        Log.i("LoaderInfo", "Created");
        return new ImageScanner(getApplicationContext(), file);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<HashSet<Folder>> loader, HashSet<Folder> data) {
        Log.i("LoaderInfo", "size " + folderList.size());
        folderList.clear();
        folderList.addAll(data);
        Log.i("LoaderInfo", "size " + folderList.size());

        Log.i("LoaderInfo", "Ended");
        folderAdapter.notifyItemInserted(folderList.size());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<HashSet<Folder>> loader) {
        folderAdapter = null;
        Log.i("LoaderInfo", "Reset ho gaya");
    }
}