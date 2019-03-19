package com.amitsparta.happybirthday.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.amitsparta.happybirthday.Adapters.FolderAdapter;
import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.HelperClasses.BackgroundFolderScanner;
import com.amitsparta.happybirthday.HelperClasses.FileIO;
import com.amitsparta.happybirthday.R;

import java.io.File;
import java.util.ArrayList;

public class FolderActivity extends AppCompatActivity {

    static ArrayList<Folder> folderList;
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
        progressBar.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.folder_list);

        folderList = new ArrayList<Folder>();
        folderAdapter = new FolderAdapter(getApplicationContext(), folderList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        loadPreviousFolders();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(folderAdapter);

        scanForMoreFolders();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadPreviousFolders() {
        if (FileIO.hasFolderList(null, Folder.FOLDER_MODE)) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    folderList.clear();
                    ArrayList<Folder> tempArr = FileIO.getFolderFromFile(null, Folder.FOLDER_MODE);
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
                    folderAdapter.notifyItemInserted(folderList.size());
                }
            }.execute();
        }
    }

    private void scanForMoreFolders() {
        BackgroundFolderScanner imageScanner = new BackgroundFolderScanner(new File(Folder.ABSOLUTE_FILE_PATH), Folder.FOLDER_MODE);
        imageScanner.getFolderList().observe(this, new Observer<ArrayList<Folder>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Folder> folders) {
                ArrayList<Folder> tempArr = new ArrayList();
                tempArr.addAll(folders);
                folderList.clear();
                folderList.addAll(tempArr);
                FileIO.clearFile(null, Folder.FOLDER_MODE);
                if (FileIO.writeFolderToFile(folderList, Folder.FOLDER_MODE)) {
                    Log.i("Folder Activity", "Written to file");
                } else {
                    Log.i("Folder Activity", "Unable to write to file");
                }
                folderAdapter.notifyItemInserted(folderList.size());
                folderAdapter.notifyDataSetChanged();
                if (folderList.size() > 0)
                    progressBar.setVisibility(View.GONE);
            }
        });
    }
}