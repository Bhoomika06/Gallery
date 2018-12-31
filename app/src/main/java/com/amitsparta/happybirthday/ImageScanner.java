package com.amitsparta.happybirthday;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.File;
import java.util.HashSet;

class ImageScanner extends AsyncTaskLoader<HashSet<Folder>>{

    private File root;
    private HashSet<Folder> folderList;

    ImageScanner(Context context, File root) {
        super(context);
        this.root = root;
        folderList = new HashSet<>();
    }

    @Override
    public HashSet<Folder> loadInBackground() {
        collectImages(root);
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
