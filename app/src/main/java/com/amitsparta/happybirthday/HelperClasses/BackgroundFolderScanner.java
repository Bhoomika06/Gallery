package com.amitsparta.happybirthday.HelperClasses;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.amitsparta.happybirthday.DataFiles.Folder;

import java.io.File;
import java.util.ArrayList;

public class BackgroundFolderScanner extends ViewModel {

    private File root;
    private ArrayList<Folder> folderList;
    private MutableLiveData<ArrayList<Folder>> folderListLiveData;
    private int mode;

    public BackgroundFolderScanner(File root, int mode) {
        this.root = root;
        folderList = new ArrayList<>();
        this.mode = mode;
    }

    @SuppressLint("StaticFieldLeak")
    public LiveData<ArrayList<Folder>> getFolderList() {
        if (folderListLiveData == null) {
            folderListLiveData = new MutableLiveData<>();
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    folderListLiveData.postValue(ImageDetector.collectImages(root, mode));
                    return null;
                }
            }.execute();
            folderListLiveData.setValue(folderList);
        }
        return folderListLiveData;
    }
}
