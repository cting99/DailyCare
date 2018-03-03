package cting.com.robin.toothdaily.datagenerator;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.toothdaily.model.DailyRecord;
import cting.com.robin.toothdaily.model.TimePeriod;

/**
 * Created by cting on 2018/2/28.
 */

public class RawFileParser {

    public static final String FILE_INVISALIGN = FileHelper.DIR + "invisalign_raw.txt";


    public static Map<Integer, ArrayList<DailyRecord>> readFile() throws Exception {
        InputStream in = new FileInputStream(FILE_INVISALIGN);
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
                map.put(category, new ArrayList<DailyRecord>());
            } else if (isDeailyRecordLine(items)) {
                dailyRecord = readDailyRecord(items);
                map.get(category).add(dailyRecord);
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