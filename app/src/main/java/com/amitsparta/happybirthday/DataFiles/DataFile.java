package com.amitsparta.happybirthday.DataFiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.Serializable;

public class DataFile implements Serializable {

    private String filePath;

    DataFile(File file) {
        this.filePath = file.getAbsolutePath();
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
}
