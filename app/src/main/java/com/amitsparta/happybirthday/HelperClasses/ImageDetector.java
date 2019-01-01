package com.amitsparta.happybirthday.HelperClasses;

import android.support.annotation.NonNull;

import java.io.File;

class ImageDetector {

    private ImageDetector() {
    }

    private static final String[] supportedExtensions = {"jpg", "jpeg", "png", "bmp", "JPG", "JPEG", "PNG", "BMP"};

    public static Boolean checkIfImage(@NonNull File file) {
        String filePath = file.getAbsolutePath();
        for (String extensions : supportedExtensions) {
            if (filePath.endsWith(extensions)) {
                if (file.length() > 50000) {
                    return true;
                } else {
                    return null;
                }
            }
        }
        return false;
    }

}
