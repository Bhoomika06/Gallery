package com.amitsparta.happybirthday.DataFiles;

import android.support.annotation.NonNull;

import java.io.File;

class ImageDetector {

    private ImageDetector() {
    }

    private static final String[] supportedExtensions = {"jpg", "jpeg", "png", "bmp", "JPG", "JPEG", "PNG", "BMP"};

    public static boolean checkIfImage(@NonNull File file) {
        String filePath = file.getAbsolutePath();
        for (String extensions : supportedExtensions) {
            if (filePath.endsWith(extensions)) {
                return true;
            }
        }
        return false;
    }

}
