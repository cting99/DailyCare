package cting.com.robin.toothdaily.utils;

import java.util.ArrayList;
import java.util.Map;

import cting.com.robin.toothdaily.model.DailyRecord;

/**
 * Created by cting on 2018/2/26.
 */

public class RecordDatas {

    private static Map<Integer, ArrayList<DailyRecord>> maps;


    public static final ArrayList<DailyRecord> getData() {
        ArrayList<DailyRecord> records = new ArrayList<>();
        records.add(new DailyRecord("2018/2/25", "", "0:00,8:30,9:22,11:45,12:45,18:19,19:15,24:00"));
        records.add(new DailyRecord("2018/2/26", "", "0:00,8:36,9:25,11:46,12:20,18:07,19:20,24:00"));
        return records;
    }
}
