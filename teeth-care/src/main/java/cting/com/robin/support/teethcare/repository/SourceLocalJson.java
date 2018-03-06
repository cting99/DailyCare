package cting.com.robin.support.teethcare.repository;

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

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;
import cting.com.robin.support.teethcare.utils.GsonHelper.BracesWrapper;
import cting.com.robin.support.teethcare.utils.GsonHelper.DailyWrapper;

import static cting.com.robin.support.teethcare.utils.GsonHelper.FILE_INVISALIGN_BRACES_JSON;
import static cting.com.robin.support.teethcare.utils.GsonHelper.FILE_INVISALIGN_DAILY_JSON;


class SourceLocalJson extends DataGenerator {
    public static final String TAG = "cting/SourceLocalJson";

    @Override
    public void forceLoad(Context context) {
        super.forceLoad(context);
        mBracesList = readBraces(context);
        mDailyList = readDaily(context);
    }

    public static final ArrayList<DailyRecord> readDaily(Context context) {
        String fileName = FILE_INVISALIGN_DAILY_JSON;
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
            DailyWrapper items = gson.fromJson(reader, DailyWrapper.class);
            Log.i(TAG, "readDaily: " + items.getList().size());
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


    public static final ArrayList<BracesRecord> readBraces(Context context) {
        String fileName = FILE_INVISALIGN_BRACES_JSON;
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
            BracesWrapper items = gson.fromJson(reader, BracesWrapper.class);
            Log.i(TAG, "readBraces: " + items.getList().size());
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

}
