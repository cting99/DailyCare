package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;


public class SampleDatas {

    public static final String TAG = "cting/tooth/SampleDatas";

    public static final ArrayList<DailyRecord> getDailyRecords(Context context) {
        return RawFileParser.getDailyRecords(context);
    }

    public static final ArrayList<ProgressRecord> getProgressRecords(Context context) {
        return RawFileParser.getProgressRecords(context);
    }

    public static final int getDayIndex(Context context) {
        return getDailyRecords(context).size();
    }

    public static final int getProgressIndex(Context context) {
        return getProgressRecords(context).size();
    }
}
