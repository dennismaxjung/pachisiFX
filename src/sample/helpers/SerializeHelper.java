package sample.helpers;

import sample.models.Board;

import java.io.*;

/**
 * Created by Dennis on 21.07.2017.
 */
public class SerializeHelper {

    public static void saveBinary(Object object, String path)
    {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(object);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void saveBinary(Object object, File file)
    {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(object);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static <T> T readBinary(String path)
    {
        FileInputStream fout = null;
        T object = null;
        try {
            fout = new FileInputStream(path);
            ObjectInputStream oos = new ObjectInputStream(fout);
            object = (T)oos.readObject();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static <T> T readBinary(File file)
    {
        FileInputStream fout = null;
        T object = null;
        try {
            fout = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fout);
            object = (T)oos.readObject();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
