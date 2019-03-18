package com.amitsparta.happybirthday.HelperClasses;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.DataFiles.Image;

import java.util.ArrayList;

public class BackgroundImageScanner extends ViewModel {

    private String root;
    private ArrayList<Image> imageList;
    private MutableLiveData<ArrayList<Image>> imageListLiveData;
    private int mode;

    public BackgroundImageScanner(String root, int mode) {
        this.root = root;
        imageList = new ArrayList<>();
        this.mode = mode;
    }

    @SuppressLint("StaticFieldLeak")
    public LiveData<ArrayList<Image>> getImageList() {
        if (imageListLiveData == null) {
            imageListLiveData = new MutableLiveData<>();
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    ArrayList<Folder> temp = ImageDetector.collectImages(root, mode);
                    if (temp != null && !temp.isEmpty())
                        imageListLiveData.postValue(temp.get(0).getImages());
                    return null;
                }
            }.execute();
            imageListLiveData.setValue(imageList);
        }
        return imageListLiveData;
    }

}
