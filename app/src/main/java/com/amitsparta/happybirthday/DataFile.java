package com.amitsparta.happybirthday;

public class DataFile {

    private String textToDisplay;
    private int image;

    DataFile(int image, String textToDisplay) {
        this.textToDisplay = textToDisplay;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getTextToDisplay() {
        return textToDisplay;
    }

}
