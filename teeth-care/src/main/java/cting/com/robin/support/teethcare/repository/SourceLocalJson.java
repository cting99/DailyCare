package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.database.DBItem;
import cting.com.robin.support.teethcare.utils.GsonHelper;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;
import cting.com.robin.support.teethcare.utils.GsonHelper.Wrapper;

import static cting.com.robin.support.teethcare.utils.GsonHelper.FILE_INVISALIGN_JSON;


class SourceLocalJson extends DataGenerator {
    public static final String TAG = "cting/SourceLocalJson";

    public SourceLocalJson() {
        super("Backup-Json-file");
    }

    @Override
    public void forceLoad(Context context) {
        readJson(context);
    }

    public final void readJson(Context context) {
        String fileName = FILE_INVISALIGN_JSON;
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
            Log.i(TAG, "readJson size: " + items.getList().size());
            list.addAll(items.getList());
        } catch (FileNotFoundException e) {
            Log.e(TAG, "readJson,exception1:" + e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "readJson,exception2:" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }


}
