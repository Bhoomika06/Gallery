package com.amitsparta.happybirthday.HelperClasses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.amitsparta.happybirthday.DataFiles.DataFile;
import com.amitsparta.happybirthday.DataFiles.Folder;

import java.io.File;
import java.util.HashSet;

public class ImageScanner extends ViewModel {

    private File root;
    private HashSet<Folder> folderList;
    private MutableLiveData<HashSet<Folder>> folderListLiveData;

    public ImageScanner(File root) {
        this.root = root;
        folderList = new HashSet<>();
    }

    public LiveData<HashSet<Folder>> getFolderList() {
        if (folderListLiveData == null) {
            folderListLiveData = new MutableLiveData<>();
            collectImages(root);
            folderListLiveData.setValue(folderList);
        }
        return folderListLiveData;
    }

    private void collectImages(@NonNull File file) {
        File internalFiles[] = file.listFiles();
        if (internalFiles == null || file.getName().equals("Android"))
            return;
        for (File file1: internalFiles) {
            Boolean isImage = ImageDetector.checkIfImage(file1);
            if (isImage == null) {
                return;
            } else if (isImage) {
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
