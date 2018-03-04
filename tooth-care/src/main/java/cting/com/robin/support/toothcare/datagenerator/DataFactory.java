package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;
import cting.com.robin.support.toothcare.utils.GsonHelper;

public abstract class DataFactory {

    public static final String TAG = "cting/tooth/data";

    protected ArrayList<ProgressRecord> progressRecords = new ArrayList<>();
    protected ArrayList<DailyRecord> dailyRecords = new ArrayList<>();
    protected Context context;

    public DataFactory(Context context) {
        this.context = context;
    }

    public void sortDes() {
        Collections.sort(progressRecords, new Comparator<ProgressRecord>() {
            @Override
            public int compare(ProgressRecord o1, ProgressRecord o2) {
                return o2.getProgressIndex() - o1.getProgressIndex();
            }
        });
        Collections.sort(dailyRecords, new Comparator<DailyRecord>() {
            @Override
            public int compare(DailyRecord o1, DailyRecord o2) {
                return o2.getDayIndex() - o1.getDayIndex();
            }
        });
    }


    public ArrayList<ProgressRecord> getProgressRecords() {
        return progressRecords;
    }

    public ProgressRecord getLastProgressRecord() {
        if (progressRecords != null && progressRecords.size() > 0) {
            return progressRecords.get(0);
        } else {
            return null;
        }
    }


    public ArrayList<DailyRecord> getDailyRecords() {
        return dailyRecords;
    }

    public DailyRecord getLastDailyRecord() {
        if (dailyRecords != null && dailyRecords.size() > 0) {
            return dailyRecords.get(0);
        } else {
            return null;
        }
    }


    public boolean save(Context context) {
        return GsonHelper.writeToGson(context, progressRecords);
    }

    public abstract void load();
    public abstract void addDailyItem(DailyRecord dailyRecord);
}
