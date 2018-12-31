package com.amitsparta.happybirthday;

import java.util.ArrayList;

class Folder {

    private ArrayList<DataFile> images;
    private String folderName;

    Folder(String folderName) {
        images = new ArrayList<>();
        this.folderName = folderName;
    }

    public void add(DataFile dataFile) {
        images.add(dataFile);
    }

    public ArrayList<DataFile> getImages() {
        return images;
    }

    public String getFolderName() {
        return folderName;
    }

    public boolean compareFolders(String folder) {
        return folder.equals(folderName);
    }
}