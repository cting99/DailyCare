package cting.com.robin.support.toothcare.datagenerator;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;

public class RawFileParser {

    public static final String TAG = "cting/tooth/data";
    public static final String FILE_INVISALIGN = FileHelper.DIR + "invisalign_raw.txt";

    private static ArrayList<ProgressRecord> progressRecords = new ArrayList<>();
    private static ArrayList<DailyRecord> dailyRecords = new ArrayList<>();

    public static void readFile() throws Exception {
        InputStream in = new FileInputStream(FILE_INVISALIGN);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        int progressIndex = -1;
        ProgressRecord progressRecord = null;
        DailyRecord dailyRecord = null;
        Log.i(TAG, "readFile: " + FILE_INVISALIGN);

        while ((line = reader.readLine()) != null) {
            String[] items = line.split("\t");
//            Log.i(TAG, "readFile: read items="+ Arrays.toString(items));
            if (isProgressLine(items)) {
                progressIndex = readProgress(items[0]);
                progressRecord = new ProgressRecord();
                progressRecord.setProgressIndex(progressIndex);
                progressRecords.add(progressRecord);
                Log.i(TAG, "readFile: add progress " + progressIndex);
            } else if (isDeailyRecordLine(items)) {
                dailyRecord = readDailyRecord(items);
                dailyRecords.add(dailyRecord);
                Log.i(TAG, "readFile: add daily " + dailyRecord.getDate());
                if (progressRecord != null) {
                    if (progressIndex != -1) {
                        progressRecord.setStartDate(dailyRecord.getDate());
                        progressIndex = -1;
                    }
                    progressRecord.setEndDate(dailyRecord.getDate());
                }
            }
        }
        in.close();
    }

    public static final ArrayList<DailyRecord> getDailyRecords() {
        if (dailyRecords == null || dailyRecords.size() == 0) {
            try {
                readFile();
            } catch (Exception e) {
                Log.i(TAG, "getDailyRecords: "+e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return dailyRecords;
    }

    public static final ArrayList<ProgressRecord> getProgressRecords() {
        if (progressRecords == null || progressRecords.size() == 0) {
            try {
                readFile();
            } catch (Exception e) {
                Log.i(TAG, "getProgressRecords: "+e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return progressRecords;
    }

    private static final boolean isProgressLine(String[] lineItems) {
        return lineItems != null && lineItems.length == 2;
    }
    private static final int readProgress(String categoryStr) {
        int index = StringHelper.getNumbers(categoryStr);
        return index;
    }

    private static boolean isDeailyRecordLine(String[] lineItems) {
        return lineItems != null && lineItems.length > 0 && TextUtils.isDigitsOnly(lineItems[0]);
    }

    private static DailyRecord readDailyRecord(String[] lineItems) {
        DailyRecord record = new DailyRecord();
        int count = lineItems.length;

        record.setDate(lineItems[1]);
        record.setTotalTime(lineItems[2]);
        record.setNotes(lineItems[3]);

        ArrayList<TimeSlice> periods = new ArrayList<>(count - 4);
        TimeSlice timeSlice = null;
        String item = null;
        for (int i = 4; i < count; i++) {
            item = lineItems[i];
            if (TextUtils.isEmpty(item)) {
                continue;
            }
            if (timeSlice == null) {
                timeSlice = new TimeSlice();
                timeSlice.setStartTime(item);
            } else {
                if ("0:00".equals(item)) {
                    item = "24:00";
                }
                timeSlice.setEndTime(item);
                periods.add(timeSlice);
                timeSlice = null;
            }
        }
        record.setTimeSliceList(periods);

        return record;
    }
}
