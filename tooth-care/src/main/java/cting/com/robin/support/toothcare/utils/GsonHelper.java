package cting.com.robin.support.toothcare.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.toothcare.datagenerator.JsonFileParser;
import cting.com.robin.support.toothcare.models.ProgressRecord;

public class GsonHelper {

    public static final String TAG = "cting/util/gson";

    public static final boolean writeToGson(Context context, ArrayList<ProgressRecord> dataList) {
        String fileName = JsonFileParser.FILE_INVISALIGN_JSON;
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setDateFormat(TimeFormatHelper.DATA_FORMAT)
                .setPrettyPrinting()
                .create();

        Wrapper wrapper = new Wrapper();
        wrapper.setList(dataList);
        String content = gson.toJson(wrapper);
        boolean ret = FileHelper.writeToExternal(fileName, content);
        Toast.makeText(context, "writeToGson" + fileName + " success:" + ret, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "writeToGson" + fileName + " success:" + ret);
        return ret;
    }

    public static final ArrayList<ProgressRecord> readFromJson(Context context) {
        String fileName = JsonFileParser.FILE_INVISALIGN_JSON;
        FileReader reader = null;
        try {
            File file = new File(fileName);
            reader = new FileReader(file);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .setDateFormat(TimeFormatHelper.DATA_FORMAT)
                    .setPrettyPrinting()
                    .create();
            GsonHelper.Wrapper items = gson.fromJson(reader, GsonHelper.Wrapper.class);
            return items.getList();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "readFromJson,exception1:" + e.getLocalizedMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "readFromJson,exception2:" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Wrapper {
        @Expose
        private ArrayList<ProgressRecord> list;

        public Wrapper() {
        }

        public void setList(ArrayList<ProgressRecord> list) {
            this.list = list;
        }

        public ArrayList<ProgressRecord> getList() {
            return list;
        }
    }
}
