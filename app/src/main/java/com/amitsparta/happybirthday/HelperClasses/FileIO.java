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
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public final class FileIO {
    private FileIO() {
    }

    /*
     Folder related functions.
     */

    public static boolean hasFolderList(Folder folder, int mode) {
        String path;
        if (mode == Image.IMAGE_MODE) {
            path = folder.HIDDEN_FOLDER_LIST_FILE_NAME_PER_FOLDER;
        } else {
            path = Folder.HIDDEN_FOLDER_LIST_FILE_NAME;
        }
        File file = new File(Folder.HIDDEN_FILE_PATH, path);
        return file.exists();
    }

    public static boolean writeFolderToFile(Folder folder, int mode) {
        ArrayList<Folder> folderArrayList = new ArrayList();
        folderArrayList.add(folder);
        return writeFolderToFile(folderArrayList, mode);
    }


    public static boolean writeFolderToFile(ArrayList<Folder> folderList, int mode) {
        File file = new File(Folder.HIDDEN_FILE_PATH);
        if (file.mkdir()) {
            File file1 = new File(Folder.HIDDEN_FILE_PATH + "/.nomedia");
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String hiddenFileName = null;
        if (mode == Image.IMAGE_MODE) {
            hiddenFileName = folderList.get(0).HIDDEN_FOLDER_LIST_FILE_NAME_PER_FOLDER;
        } else if (mode == Folder.FOLDER_MODE) {
            hiddenFileName = Folder.HIDDEN_FOLDER_LIST_FILE_NAME;
        }
        try (FileOutputStream outputStream = new FileOutputStream(
                new File(Folder.HIDDEN_FILE_PATH, hiddenFileName))) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            if (mode == Folder.FOLDER_MODE) {
                objectOutputStream.writeObject(folderList);
            } else {
                objectOutputStream.writeObject(folderList.get(0));
            }
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

    public static void clearFile(Folder folder, int mode) {
        if (FileIO.hasFolderList(folder, mode)) {
            String path;
            if (mode == Image.IMAGE_MODE) {
                path = folder.HIDDEN_FOLDER_LIST_FILE_NAME_PER_FOLDER;
            } else {
                path = Folder.HIDDEN_FOLDER_LIST_FILE_NAME;
            }
            File file = new File(Folder.HIDDEN_FILE_PATH, path);
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

    public static boolean copyImage(Image image, String dest) {
        File source = new File(image.getFilePath());
        File destination = new File(Folder.ABSOLUTE_FILE_PATH, dest + "/" + image.getFileName());
        FileChannel inputStream = null;
        FileChannel outputStream = null;
        try {
            inputStream = new FileInputStream(source).getChannel();
            outputStream = new FileOutputStream(destination).getChannel();
            outputStream.transferFrom(inputStream, 0, inputStream.size());
            inputStream.close();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean moveImage(Image image, String dest) {
        if (copyImage(image, dest)) {
            return deleteImage(image);
        }
        return false;
    }
}
