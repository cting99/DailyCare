package cting.com.robin.support.toothcare.datagenerator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;


public class SampleDatas {

    public static final String TAG = "cting/tooth/SampleDatas";

    public static final ArrayList<DailyRecord> getDailyRecords() {
        return RawFileParser.getDailyRecords();
    }

    public static final ArrayList<ProgressRecord> getProgressRecords() {
        return RawFileParser.getProgressRecords();
    }
/*
    public static final Map<Integer, ArrayList<DailyRecord>> getFullRecordsMap() {
        try {
            if (map == null || map.size() == 0) {
                map = RawFileParser.readFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //test
        *//*if (map != null && map.size() > 0) {
            for (Integer category : map.keySet()) {
                Log.i(TAG, category+":"+map.get(category));
            }
        }*//*
        return map;
    }

    public static final ArrayList<DailyRecord> getDailyRecordList() {
        return null;
    }

    public static final ArrayList<ProgressItem> getProgressItems() {
        ArrayList<ProgressItem> list = new ArrayList<>();
        Map<Integer, ArrayList<DailyRecord>> map = SampleDatas.getFullRecordsMap();
        if (map != null && map.size() > 0) {
            Object[] keySetArray = map.keySet().toArray();
            Arrays.sort(keySetArray);

            ProgressItem item = null;
            ArrayList<DailyRecord> records;
            int progressTimeCount = 0;
            for (Object category : keySetArray) {
                progressTimeCount = 0;
                records = map.get(category);
                item = new ProgressItem();
                item.setProgressIndex((Integer) category);
                item.setDayCount(records.size());
                for (DailyRecord record : records) {
                    progressTimeCount += record.getTotalTime();
                }
                item.setTotalTime(FormatHelper.formatDuration(progressTimeCount));
                list.add(item);
            }
        }
        Log.i(TAG, "getProgressItems: " + list);
        return list;
    }

    public static final ArrayList<ProgressDetailItem> getProgressDetailData() {
        ArrayList<ProgressDetailItem> items = new ArrayList<>();

        //for test
        ProgressDetailItem item = new ProgressDetailItem();
        item.setDayIndex(1);
        item.setDate("2017/10/31");
        item.setTotalTime("19h 20m");
        items.add(item);

        item = new ProgressDetailItem();
        item.setDayIndex(2);
        item.setDate("2017/11/1");
        item.setTotalTime("18h 20m");
        items.add(item);


        item = new ProgressDetailItem();
        item.setDayIndex(3);
        item.setDate("2017/11/2");
        item.setTotalTime("20h 13m");
        items.add(item);

        Log.i(TAG, "getProgressDetailData: "+items);
        return items;
    }

    public static final ArrayList<DailyRecord> getDailyRecord() {
        ArrayList<DailyRecord> records = new ArrayList<>();
        Map<Integer, ArrayList<DailyRecord>> map = getFullRecordsMap();
        if (map != null && map.size() > 0) {
            records = map.get(2);
        } else {
            records.add(new DailyRecord("2018/2/25", "", "0:00,8:30,9:22,11:45,12:45,18:19,19:15,24:00"));
            records.add(new DailyRecord("2018/2/26", "", "0:00,8:36,9:25,11:46,12:20,18:07,19:20,24:00"));
        }
        return records;
    }*/
}
