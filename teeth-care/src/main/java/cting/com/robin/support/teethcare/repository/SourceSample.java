package cting.com.robin.support.teethcare.repository;

import android.content.Context;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.utils.TimeSliceHelper;

class SourceSample extends DataGenerator {

    public SourceSample() {
        super("Sample data");
    }

    @Override
    public void forceLoad(Context context) {
        super.forceLoad(context);

        // daily record
        DailyRecord dailyRecord;
        String date_ = "2017/11/0";
        for (int i = 1; i < 30; i++) {
            if (i == 10) {
                date_ = "2017/11/";
            }
            dailyRecord = new DailyRecord();
            dailyRecord.setIndex(i);
            String line = "00:00,08:30,09:20,11:50,12:20,18:20,19:05,24:00";
            dailyRecord.setLine(line);
            dailyRecord.setTotalTime(TimeSliceHelper.calculateTotalTime(line));
            dailyRecord.setDate(date_ + i);
            dailyRecord.setNote("Test day " + i);
            mDailyList.add(dailyRecord);
        }

        // braces record
        BracesRecord bracesRecord;
        for (int i = 0; i < 16; i++) {
            bracesRecord = new BracesRecord();
            bracesRecord.setIndex(i);
            bracesRecord.setDate("2017/11/01");
            bracesRecord.setTotalTime("172h 31m");
            bracesRecord.setDayCount(8);
            mBracesList.add(bracesRecord);
        }

    }


}
