package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;
import cting.com.robin.support.toothcare.utils.GsonHelper;

public class RawFileParser extends DataFactory {
    public static final String FILE_INVISALIGN_ASSET = "invisalign_raw.txt";

    public static final String TAG = "cting/tooth/data";

    public RawFileParser(Context context) {
        super(context);
    }

    @Override
    public void load() {
        // should only call once in case of context invalidate
        try {
            readFile();
            sortDes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFile() throws Exception {
        InputStream in = context.getAssets().open(FILE_INVISALIGN_ASSET);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        ProgressRecord progressRecord = null;
        DailyRecord dailyRecord = null;
        int currentProgressIndex=-1;
        Log.i(TAG, "readFile: " + FILE_INVISALIGN_ASSET);

        while ((line = reader.readLine()) != null) {
            String[] items = line.split("\t");
//            Log.i(TAG, "readFile: read items=" + Arrays.toString(items));
            if (isProgressLine(items)) {
                currentProgressIndex = readProgressIndex(items[0]);
                progressRecord = new ProgressRecord();
                progressRecord.setProgressIndex(currentProgressIndex);
                progressRecords.add(progressRecord);
//                Log.i(TAG, "readFile: add progress " + progressIndex);
            } else if (isDailyRecordLine(items)) {
                dailyRecord = readDailyRecord(items);
                dailyRecords.add(dailyRecord);
//                Log.i(TAG, "readFile: add daily " + dailyRecord.getDate());
                if (progressRecord!=null) {
                    progressRecord.getDailyRecords().add(dailyRecord);
                }
            }
        }
        in.close();
    }

    private boolean isProgressLine(String[] lineItems) {
        return lineItems != null && lineItems.length == 2;
    }

    private int readProgressIndex(String categoryStr) {
        return StringHelper.getNumbers(categoryStr);
    }

    private boolean isDailyRecordLine(String[] lineItems) {
        return lineItems != null && lineItems.length > 0 && TextUtils.isDigitsOnly(lineItems[0]);
    }

    private DailyRecord readDailyRecord(String[] lineItems) {
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
