package com.amitsparta.happybirthday.DataFiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.amitsparta.happybirthday.HelperClasses.FileIO;

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
        if (FileIO.hasThumbnailFile(fileName)) {
            return FileIO.getThumbnail(fileName);
        } else {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, bitmapOptions);
            FileIO.storeThumbnail(bitmap, fileName);
            return bitmap;
        }
    }

    public String getReferencePath() {
        int index = filePath.lastIndexOf(fileName);
        return filePath.substring(0, index - 1);
    }

    public Bitmap createImageOriginal() {
        return BitmapFactory.decodeFile(filePath);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        fileName = name;
    }
}
