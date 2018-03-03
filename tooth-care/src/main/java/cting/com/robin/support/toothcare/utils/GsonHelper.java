package cting.com.robin.support.toothcare.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.toothcare.models.ProgressRecord;

public class GsonHelper {

    public static final String TAG = "cting/util/gson";

    public static final boolean writeToGson(String fileName, ArrayList<ProgressRecord> dataList) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        Wrapper wrapper = new Wrapper();
        wrapper.setList(dataList);
        String content = gson.toJson(wrapper);
        boolean ret = FileHelper.writeToExternal(fileName, content);
        Log.i(TAG, "writeToGson, success:" + ret + ",content:" + content);
        return ret;
    }

    public static class Wrapper{
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
