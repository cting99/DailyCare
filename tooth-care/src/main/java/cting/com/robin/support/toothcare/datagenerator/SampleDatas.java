package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;

import java.util.ArrayList;

import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;


public class SampleDatas {

    public static final String TAG = "cting/tooth/SampleDatas";

    private static DataFactory mDataFactory;
    private static final SampleDatas mInstance = new SampleDatas();

    private SampleDatas() {
    }
/*
    public static SampleDatas getInstance() {
        return mInstance;
    }*/

    public static SampleDatas getInstance(Context context) {
        if (mDataFactory == null) {
//            setDataFactory(new RawFileParser(context));
            setDataFactory(new JsonFileParser(context));
        }
        return mInstance;
    }

    public static void setDataFactory(DataFactory dataFactory) {
        mDataFactory = dataFactory;
        mDataFactory.load();
    }

    // daily record
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

    // progress
    public ArrayList<ProgressRecord> getProgressRecords() {
        return mDataFactory.getProgressRecords();
    }

    public int getProgressIndex() {
        return getProgressRecords().size();
    }


    public void save(Context context) {
        mDataFactory.save(context);
    }
}
