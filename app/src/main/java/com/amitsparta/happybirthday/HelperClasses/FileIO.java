package com.amitsparta.happybirthday.HelperClasses;

import android.util.Log;

import com.amitsparta.happybirthday.DataFiles.Folder;

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
                new File(file.getAbsolutePath() + Folder.HIDDEN_FOLDER_LIST_FILE_NAME))) {

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
            File file = new File(Folder.HIDDEN_FILE_PATH + Folder.HIDDEN_FOLDER_LIST_FILE_NAME);
            file.delete();
        }
    }
}
