package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;


public class SampleData {

    public static final String TAG = "cting/tooth/SampleData";

    private static DataFactory mDataFactory;
    private static final SampleData mInstance = new SampleData();

    private SampleData() {
    }
/*
    public static SampleData getInstance() {
        return mInstance;
    }*/

    public static SampleData getInstance() {
        return mInstance;
    }

    public void load(DataFactory dataFactory) {
        mDataFactory = dataFactory;
        Log.i(TAG, "load,start--");
        mDataFactory.load();
        Log.i(TAG, "load,end--");
    }

    // daily record begin
    public ArrayList<DailyRecord> getDailyRecords() {
        return mDataFactory.getDailyRecords();
    }

    public DailyRecord getLastDailyRecord() {
        return mDataFactory.getLastDailyRecord();
    }

    public int getDayIndex() {
        return getDailyRecords().size();
    }

    public void addDailyRecord(DailyRecord dailyRecord) {
        mDataFactory.addDailyItem(dailyRecord);
    }
    // daily record end


    // progress begin
    public ArrayList<ProgressRecord> getProgressRecords() {
        return mDataFactory.getProgressRecords();
    }

    public ProgressRecord getLastProgressRecord() {
        return mDataFactory.getLastProgressRecord();
    }

    public int getProgressIndex() {
        return getProgressRecords().size();
    }
    // progress end

    public void save(Context context) {
        mDataFactory.save(context);
    }
}
