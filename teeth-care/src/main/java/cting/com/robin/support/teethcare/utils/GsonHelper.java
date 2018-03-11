package cting.com.robin.support.teethcare.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.teethcare.database.DBItem;

public class GsonHelper {
    public static final String TAG = "cting/GsonHelper";

    public static final String FILE_INVISALIGN_JSON = FileHelper.EXTERNAL_DIR + "invisalign_daily_teeth.json";

    public static boolean saveFromDB(Context context,ArrayList<DBItem> list) {
        String fileName = FILE_INVISALIGN_JSON;
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat(TimeFormatHelper.DATA_FORMAT)
                .setPrettyPrinting()
                .create();

        Wrapper wrapper = new Wrapper();

        wrapper.setList(list);
        String content = gson.toJson(wrapper);
        boolean ret = FileHelper.writeToExternal(fileName, content);
        Log.w(TAG, "backup from database to file " + fileName + ", success:" + ret);
        return ret;

    }

    public static class Wrapper {
        @Expose
        private ArrayList<DBItem> list;

        public Wrapper() {
        }

        public void setList(ArrayList<DBItem> braces) {
            this.list = braces;
        }

        public ArrayList<DBItem> getList() {
            return list;
        }
    }

}
