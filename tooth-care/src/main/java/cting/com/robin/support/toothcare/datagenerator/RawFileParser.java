package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;

public class RawFileParser {

    public static final String TAG = "cting/tooth/data";
    public static final String FILE_INVISALIGN = "invisalign_raw.txt";

    private static ArrayList<ProgressRecord> progressRecords = new ArrayList<>();
    private static ArrayList<DailyRecord> dailyRecords = new ArrayList<>();

    public static void readFile(Context context) throws Exception {
        InputStream in = context.getAssets().open(FILE_INVISALIGN);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        ProgressRecord progressRecord = null;
        DailyRecord dailyRecord = null;
        int currentProgressIndex=-1;
        Log.i(TAG, "readFile: " + FILE_INVISALIGN);

        while ((line = reader.readLine()) != null) {
            String[] items = line.split("\t");
//            Log.i(TAG, "readFile: read items=" + Arrays.toString(items));
            if (isProgressLine(items)) {
                currentProgressIndex = readProgressIndex(items[0]);
                progressRecord = new ProgressRecord();
                progressRecord.setProgressIndex(currentProgressIndex);
                progressRecords.add(progressRecord);
//                Log.i(TAG, "readFile: add progress " + progressIndex);
            } else if (isDeailyRecordLine(items)) {
                dailyRecord = readDailyRecord(items);
                dailyRecords.add(dailyRecord);
//                Log.i(TAG, "readFile: add daily " + dailyRecord.getDate());
                if (progressRecord!=null) {
                    progressRecord.getDailyRecords().add(dailyRecord);
                }
            }
        }
        in.close();


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

    public static final ArrayList<DailyRecord> getDailyRecords(Context context) {
        if (dailyRecords == null || dailyRecords.size() == 0) {
            try {
                readFile(context);
            } catch (Exception e) {
                Log.i(TAG, "getDailyRecords: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return dailyRecords;
    }

    public static final ArrayList<ProgressRecord> getProgressRecords(Context context) {
        if (progressRecords == null || progressRecords.size() == 0) {
            try {
                readFile(context);
            } catch (Exception e) {
                Log.i(TAG, "getProgressRecords: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return progressRecords;
    }

    private static final boolean isProgressLine(String[] lineItems) {
        return lineItems != null && lineItems.length == 2;
    }

    private static final int readProgressIndex(String categoryStr) {
        return StringHelper.getNumbers(categoryStr);
    }

    private static boolean isDeailyRecordLine(String[] lineItems) {
        return lineItems != null && lineItems.length > 0 && TextUtils.isDigitsOnly(lineItems[0]);
    }

    private static DailyRecord readDailyRecord(String[] lineItems) {
        DailyRecord record = new DailyRecord();
        int count = lineItems.length;

        record.setDayIndex(dailyRecords.size() + 1);
        record.setDate(lineItems[1]);
//        record.setTotalTime(lineItems[2]);
        record.setNotes(lineItems[3]);

        ArrayList<TimeSlice> periods = new ArrayList<>(count - 4);
        TimeSlice timeSlice = null;
        String item = null;
        for (int i = 4; i < count; i++) {
            item = lineItems[i];
            if (TextUtils.isEmpty(item)) {
                continue;
            }
            timeSlice = new TimeSlice();
            timeSlice.setStartTime(item);
            if (i + 1 >= count) {
                timeSlice.setEndTime("24:00");
            } else {
                item = lineItems[++i];
                timeSlice.setEndTime("0:00".equals(item) ? "24:00" : item);
            }
//            Log.i(TAG, "readDailyRecord: add TimeSlice:" + timeSlice);
            periods.add(timeSlice);

        }
        record.setTimeSliceList(periods);

        return record;
    }
}
