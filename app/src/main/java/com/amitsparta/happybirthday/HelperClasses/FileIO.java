package com.amitsparta.happybirthday.HelperClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.amitsparta.happybirthday.DataFiles.Folder;
import com.amitsparta.happybirthday.DataFiles.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public final class FileIO {
    private FileIO() {
    }

    /*
     Folder related functions.
     */

    public static boolean hasFolderList() {
        File file = new File(Folder.HIDDEN_FILE_PATH + Folder.HIDDEN_FOLDER_LIST_FILE_NAME);
        return file.exists();
    }

    public static boolean writeFolderToFile(ArrayList<Folder> folderList) {
        File file = new File(Folder.HIDDEN_FILE_PATH);
        if (file.mkdir()) {
            File file1 = new File(Folder.HIDDEN_FILE_PATH + "/.nomedia");
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream(
                new File(Folder.HIDDEN_FILE_PATH + Folder.HIDDEN_FOLDER_LIST_FILE_NAME))) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(folderList);
            objectOutputStream.close();
            Log.i("Folder Activity", "Successfully written.");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Folder Activity", "Writing error");
            return false;
        }
    }

    public static ArrayList<Folder> getFolderFromFile() {
        File file = new File(Folder.HIDDEN_FILE_PATH);
        try (FileInputStream inputStream = new FileInputStream(
                new File(file.getAbsolutePath(), Folder.HIDDEN_FOLDER_LIST_FILE_NAME))) {

            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ArrayList a = (ArrayList<Folder>) objectInputStream.readObject();
            Log.i("Folder Activity", "Successfully read from file.");
            objectInputStream.close();
            return a;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.i("Folder Activity", "File empty.");
            return null;
        }
    }

    public static void clearFile() {
        if (FileIO.hasFolderList()) {
            File file = new File(Folder.HIDDEN_FILE_PATH, Folder.HIDDEN_FOLDER_LIST_FILE_NAME);
            file.delete();
        }
    }

    /*
      Thumbnail related functions.
     */
    public static boolean hasThumbnailFile(String fileName) {
        File file = new File(Folder.HIDDEN_FILE_PATH, fileName);
        return file.exists();
    }

    public static Bitmap getThumbnail(String fileName) {
        File file = new File(Folder.HIDDEN_FILE_PATH, fileName);
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static void storeThumbnail(Bitmap bitmap, String fileName) {
        File file = new File(Folder.HIDDEN_FILE_PATH, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteThumbnail(Image image) {
        File file = new File(Folder.HIDDEN_FILE_PATH, image.getFileName());
        if (file.exists()) {
            if (file.delete()) {
                image.setFileName(null);
                return true;
            }
        }
        return false;
    }

    /*
      Single image related functions.
     */

    public static boolean deleteImage(Image image) {
        File file = new File(image.getFilePath());
        if (file.delete()) {
            return deleteThumbnail(image);
        }
        return false;
    }

    public static boolean renameImage(Image image, String name) {
        File file = new File(image.getFilePath());
        name += image.getFileName().substring(image.getFileName().lastIndexOf('.'));
        if (file.renameTo(new File(image.getReferencePath(), name))) {
            image.setFileName(name);
            return true;
        }
        return false;
    }

}
