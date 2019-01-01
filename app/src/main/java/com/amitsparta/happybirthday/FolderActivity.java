package com.amitsparta.happybirthday;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.amitsparta.happybirthday.Adapters.FolderAdapter;
import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.HelperClasses.ImageScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class FolderActivity extends AppCompatActivity {

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
        progressBar.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.folder_list);

        folderList = new ArrayList();
        folderAdapter = new FolderAdapter(getApplicationContext(), folderList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(folderAdapter);

        ImageScanner imageScanner = new ImageScanner(new File(Folder.ABSOLUTE_FILE_PATH));
        imageScanner.getFolderList().observe(this, new Observer<HashSet<Folder>>() {
            @Override
            public void onChanged(@Nullable HashSet<Folder> folders) {
                folderList.clear();
                folderList.addAll(folders);
                folderAdapter.notifyItemInserted(folderList.size());
                if (folderList.size() > 0)
                    progressBar.setVisibility(View.GONE);
            }
        });
    }
}