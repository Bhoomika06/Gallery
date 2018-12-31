package com.amitsparta.happybirthday.DataFiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class DataFile {

    private String filePath;

    DataFile(File file) {
        this.filePath = file.getAbsolutePath();
    }

    public String getFilePath() {
        return filePath;
    }

    public Bitmap createImage() {
        return BitmapFactory.decodeFile(filePath);
    }
}
