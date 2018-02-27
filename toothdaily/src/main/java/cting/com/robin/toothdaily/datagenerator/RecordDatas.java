package cting.com.robin.toothdaily.datagenerator;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.toothdaily.model.DailyRecord;
import cting.com.robin.toothdaily.model.TimePeriod;

/**
 * Created by cting on 2018/2/26.
 */

public class RecordDatas {

//    private static Map<Integer, ArrayList<DailyRecord>> maps;
    public static final String TAG = "cting/tooth/RecordDatas";
    public static final String FILE_INVISALIGN = FileHelper.DIR + "invisalign_raw.txt";


    public static final ArrayList<DailyRecord> getData() {
        ArrayList<DailyRecord> records = new ArrayList<>();
        Map<Integer, ArrayList<DailyRecord>> map = readInvisalignFile();
        if (map != null && map.size() > 0) {
            records = map.get(2);
        } else {
            records.add(new DailyRecord("2018/2/25", "", "0:00,8:30,9:22,11:45,12:45,18:19,19:15,24:00"));
            records.add(new DailyRecord("2018/2/26", "", "0:00,8:36,9:25,11:46,12:20,18:07,19:20,24:00"));
        }
        return records;
    }

    public static Map<Integer, ArrayList<DailyRecord>> readInvisalignFile() {
        Map<Integer, ArrayList<DailyRecord>> map = null;
        try {
            map = readFile(FILE_INVISALIGN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //test
        /*if (map != null && map.size() > 0) {
            for (Integer category : map.keySet()) {
                Log.i(TAG, category+":"+map.get(category));
            }
        }*/
        return map;
    }

    private static Map<Integer, ArrayList<DailyRecord>> readFile(@NonNull String fileName) throws Exception {
        InputStream in = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        Map<Integer, ArrayList<DailyRecord>> map=new HashMap<>();
        int category = -1;
        DailyRecord dailyRecord = null;
        while ((line = reader.readLine()) != null) {
            String[] items = line.split("\t");
//                Log.i(TAG, "readFile: read items="+ Arrays.toString(items));
            if (isCategoryLine(items)) {
                category = readCategory(items[0]);
            } else if (isDeailyRecordLine(items)) {
                dailyRecord = readDailyRecord(items);
            }
            if (category >= 0 && dailyRecord != null) {
                addDailyRecordToMap(map, Integer.valueOf(category), dailyRecord);
            }
        }
        in.close();
        return map;
    }

    private static void addDailyRecordToMap(Map<Integer, ArrayList<DailyRecord>> map, Integer category, DailyRecord dailyRecord) {
        ArrayList<DailyRecord> records= map.get(category);
        if (records == null) {
            records = new ArrayList<>();
            map.put(Integer.valueOf(category), records);
//            Log.i(TAG, "addDailyRecordToMap: category=" + category);
        }
        records.add(dailyRecord);
    }

    private static final boolean isCategoryLine(String[] lineItems) {
        return lineItems != null && lineItems.length == 2;
    }
    private static final int readCategory(String categoryStr) {
        return StringHelper.getNumbers(categoryStr);
    }

    private static boolean isDeailyRecordLine(String[] lineItems) {
        return lineItems != null && lineItems.length > 0 && TextUtils.isDigitsOnly(lineItems[0]);
    }

    private static DailyRecord readDailyRecord(String[] lineItems) {
        DailyRecord record = new DailyRecord();
        int count = lineItems.length;

        record.setDate(lineItems[1]);
        record.setTotalDuration(lineItems[2]);
        record.setNotes(lineItems[3]);

        ArrayList<TimePeriod> periods = new ArrayList<>(count - 4);
        TimePeriod period = null;
        String item = null;
        for (int i = 4; i < count; i++) {
            item = lineItems[i];
            if (TextUtils.isEmpty(item)) {
                continue;
            }
            if (period == null) {
                period = new TimePeriod();
                period.setStartTime(item);
            } else {
                if ("0:00".equals(item)) {
                    item = "24:00";
                }
                period.setEndTime(item);
                periods.add(period);
                period = null;
            }
        }
        record.setPeriodList(periods);

        return record;
    }
}
