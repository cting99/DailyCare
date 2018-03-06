package cting.com.robin.support.teethcare.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.repository.MySource;

public class GsonHelper {
    public static final String TAG = "cting/GsonHelper";

    public static final String FILE_INVISALIGN_BRACES_JSON = FileHelper.EXTERNAL_DIR + "invisalign_braces.json";
    public static final String FILE_INVISALIGN_DAILY_JSON = FileHelper.EXTERNAL_DIR + "invisalign_daily.json";

    public static final boolean saveDaily(Context context, ArrayList<DailyRecord> list) {
        String fileName = FILE_INVISALIGN_DAILY_JSON;
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setDateFormat(TimeFormatHelper.DATA_FORMAT)
                .setPrettyPrinting()
                .create();

        DailyWrapper wrapper = new DailyWrapper();
        wrapper.setList(list);
        String content = gson.toJson(wrapper);
        boolean ret = FileHelper.writeToExternal(fileName, content);
//        Toast.makeText(context, "saveDaily" + fileName + " success:" + ret, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "saveDaily " + fileName + " success:" + ret);
        return ret;
    }
    public static final boolean saveBraces(Context context, ArrayList<BracesRecord> list) {
        String fileName = FILE_INVISALIGN_BRACES_JSON;
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setDateFormat(TimeFormatHelper.DATA_FORMAT)
                .setPrettyPrinting()
                .create();

        BracesWrapper wrapper = new BracesWrapper();
        wrapper.setList(list);
        String content = gson.toJson(wrapper);
        boolean ret = FileHelper.writeToExternal(fileName, content);
//        Toast.makeText(context, "saveBraces" + fileName + " success:" + ret, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "saveBraces " + fileName + " success:" + ret);
        return ret;
    }

    public static class BracesWrapper {
        @Expose
        private ArrayList<BracesRecord> list;

        public BracesWrapper() {
        }

        public void setList(ArrayList<BracesRecord> list) {
            this.list = list;
        }

        public ArrayList<BracesRecord> getList() {
            return list;
        }
    }

    public static class DailyWrapper {
        @Expose
        private ArrayList<DailyRecord> list;

        public DailyWrapper() {
        }

        public void setList(ArrayList<DailyRecord> list) {
            this.list = list;
        }

        public ArrayList<DailyRecord> getList() {
            return list;
        }
    }
}
