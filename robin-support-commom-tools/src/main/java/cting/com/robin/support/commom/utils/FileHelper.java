package cting.com.robin.support.commom.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileHelper {
    private static final String TAG="cting/util/file";

    public static final String DIR_ROBIN_TOOL = "/RobinTool/";
    public static final String DIR = Environment.getExternalStorageDirectory() + DIR_ROBIN_TOOL;


    public static final File makeDirIfNotExist(String fileName) {
        File file = new File(fileName);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return file;
    }


    public static boolean writeFile(@NonNull String content, @NonNull String fileName) {
        OutputStream out = null;
        try {
            File file = makeDirIfNotExist(fileName);
            out = new FileOutputStream(file);
            out.write(content.getBytes());
            return true;
        } catch (IOException e) {
            Log.w(TAG, "exportToFile,exception1: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                Log.w(TAG, "exportToFile,exception2: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static String readFile(@NonNull String fileName) {
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        try {
            in = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
