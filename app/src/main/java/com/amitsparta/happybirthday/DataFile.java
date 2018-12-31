package com.amitsparta.happybirthday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class DataFile {

    private String filePath;
    private Bitmap image;

    DataFile(File file) {
        this.filePath = file.getAbsolutePath();
        image = BitmapFactory.decodeFile(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public Bitmap getImage() {
        return image;
    }
}
