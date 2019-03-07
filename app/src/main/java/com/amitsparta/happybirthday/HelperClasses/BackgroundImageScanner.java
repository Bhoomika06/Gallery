package com.amitsparta.happybirthday.HelperClasses;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.amitsparta.happybirthday.DataFiles.Folder;

import java.io.File;
import java.util.HashSet;

public class BackgroundImageScanner extends ViewModel {

    private File root;
    private HashSet<Folder> folderList;
    private MutableLiveData<HashSet<Folder>> folderListLiveData;

    public BackgroundImageScanner(File root) {
        this.root = root;
        folderList = new HashSet<>();
    }

    @SuppressLint("StaticFieldLeak")
    public LiveData<HashSet<Folder>> getFolderList() {
        if (folderListLiveData == null) {
            folderListLiveData = new MutableLiveData<>();
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    folderListLiveData.postValue(ImageDetector.collectImages(root, null));
                    return null;
                }
            }.execute();
            folderListLiveData.setValue(folderList);
        }
        return folderListLiveData;
    }
}
