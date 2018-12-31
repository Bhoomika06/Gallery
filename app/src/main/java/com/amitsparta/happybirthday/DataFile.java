package com.amitsparta.happybirthday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DataFile {

    private String filePath;
    private Bitmap image;

    DataFile(String filePath) {
        this.filePath = filePath;
        image = BitmapFactory.decodeFile(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public Bitmap getImage() {
        return image;
    }
}
