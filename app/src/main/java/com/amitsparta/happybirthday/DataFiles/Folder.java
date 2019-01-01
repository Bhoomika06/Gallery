package com.amitsparta.happybirthday.DataFiles;

import java.io.Serializable;
import java.util.ArrayList;

public class Folder implements Serializable {

    private ArrayList<DataFile> images;
    private String folderPath;
    private String folderName;

    public static final String ABSOLUTE_FILE_PATH = "/storage/emulated/0";

    public Folder(String folderPath) {
        images = new ArrayList<>();
        this.folderPath = folderPath;
        createFolderName();
    }

    private void createFolderName() {
        int startIndex = ABSOLUTE_FILE_PATH.length();
        int endIndex = startIndex + 1;
        if (startIndex != folderPath.length()) {
            while (endIndex < folderPath.length() && folderPath.charAt(endIndex) != '/') {
                endIndex++;
            }
            folderName = folderPath.substring(startIndex + 1, endIndex);
        } else
            folderName = folderPath;
    }

    private String createFolderName(String folderPath) {
        int startIndex = ABSOLUTE_FILE_PATH.length();
        int endIndex = startIndex + 1;
        if (startIndex != folderPath.length()) {
            while (endIndex < folderPath.length() && folderPath.charAt(endIndex) != '/') {
                endIndex++;
            }
            return folderPath.substring(startIndex + 1, endIndex);
        } else
            return folderPath;
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
        return createFolderName(folder).equals(folderName);
    }

    public String getFolderPath() {
        return folderPath;
    }
}
