package com.amitsparta.happybirthday.HelperClasses;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.amitsparta.happybirthday.DataFiles.DataFile;
import com.amitsparta.happybirthday.DataFiles.Folder;

import java.io.File;
import java.util.HashSet;

public class ImageScanner extends AsyncTaskLoader<HashSet<Folder>> {

    private File root;
    private HashSet<Folder> folderList;

    public ImageScanner(Context context, File root) {
        super(context);
        this.root = root;
        folderList = new HashSet<>();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public HashSet<Folder> loadInBackground() {
        Log.i("LoaderInfo", "Started");
        collectImages(root);
        Log.i("LoaderInfo", "Done");
        return folderList;
    }

    private void collectImages(File file) {
        File internalFiles[] = file.listFiles();
        if (internalFiles == null || file.getName().equals("Android"))
            return;
        for (File file1: internalFiles) {
            if (ImageDetector.checkIfImage(file1)) {
                addFolderAndImage(file1);
            } else {
                collectImages(file1);
            }
        }
    }

    private void addFolderAndImage(File file) {
        Folder folder = checkForFolder(file);
        if (folder == null) {
            folder = new Folder(file.getParent());
            folder.add(new DataFile(file));
            folderList.add(folder);
        } else {
            folder.add(new DataFile(file));
        }
    }

    private Folder checkForFolder(File file) {
        for (Folder folder:folderList) {
            if (folder.compareFolders(file.getParent())) {
                return folder;
            }
        }
        return null;
    }

}
