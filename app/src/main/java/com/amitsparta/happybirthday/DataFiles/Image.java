package com.amitsparta.happybirthday.DataFiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.Serializable;

public class Image implements Serializable {

    private String filePath;
    private String fileName;

    public Image(File file) {
        this.filePath = file.getAbsolutePath();
        fileName = file.getName();
    }

    public String getFilePath() {
        return filePath;
    }

    public Bitmap createThumbnail() {
        BitmapFactory.Options bitmap = new BitmapFactory.Options();
        bitmap.inSampleSize = 8;
        return BitmapFactory.decodeFile(filePath, bitmap);
    }

    public Bitmap createImageOriginal() {
        return BitmapFactory.decodeFile(filePath);
    }

    public String getFileName() {
        return fileName;
    }
}
