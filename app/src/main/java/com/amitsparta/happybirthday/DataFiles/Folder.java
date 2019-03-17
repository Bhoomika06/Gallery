package com.amitsparta.happybirthday.DataFiles;

import java.io.Serializable;
import java.util.ArrayList;

public class Folder implements Serializable {

    private ArrayList<Image> images;
    private String folderPath;
    private String folderName;

    public static final String ABSOLUTE_FILE_PATH = "/storage/emulated/0";

    public static final String HIDDEN_FILE_NAME = "/.galleryImages";

    public static final String HIDDEN_FILE_PATH = ABSOLUTE_FILE_PATH + HIDDEN_FILE_NAME;

    public static final String HIDDEN_FOLDER_LIST_FILE_NAME = "/folderList.ser";

    public static final int FOLDER_MODE = 1;
    public final String HIDDEN_FOLDER_LIST_FILE_NAME_PER_FOLDER;

    public Folder(String folderPath) {
        images = new ArrayList<>();
        this.folderPath = folderPath;
        createFolderName();
        HIDDEN_FOLDER_LIST_FILE_NAME_PER_FOLDER = "/" + folderName + ".ser";
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

    public void add(Image image) {
        images.add(image);
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public boolean compareFolders(String folder) {
        return createFolderName(folder).equals(folderName);
    }

    @Override
    public String toString() {
        return folderName;
    }
}
