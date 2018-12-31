package com.amitsparta.happybirthday;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HashSet<Folder>>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);



    }

    @NonNull
    @Override
    public Loader<HashSet<Folder>> onCreateLoader(int id, @Nullable Bundle args) {
        File file = new File("/storage/emulated/0");
        return new ImageScanner(getApplicationContext(), file);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<HashSet<Folder>> loader, HashSet<Folder> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<HashSet<Folder>> loader) {

    }
}