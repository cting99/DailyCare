package cting.com.robin.support.teethcare.daily.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;
import cting.com.robin.support.teethcare.utils.TimeSliceHelper;

import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_BRACES_INDEX;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_DAY_DATE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_DAY_INDEX;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_NOTE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_TIME_LINE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_TIME_MINUTES;


public class DailyDetailRecord extends DailyRecord {

    public static final String TAG = "cting/DailyDetail";
    private String timeLine;

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    public String getTimeLine() {
        return timeLine;
    }

    @Override
    public String toString() {
        return "DailyDetailRecord{" +
                "index=" + getIndex() +
                ", date='" + getDate() + '\'' +
                ", totalTime=" + getTotalTime() +
                ", note='" + getNote() + '\'' +
                "timeLine='" + timeLine + '\'' +
                '}';
    }

    public static final String[] DAILY_DETAIL_PROJECTION = new String[]{
            COLUMN_DAY_INDEX,
            COLUMN_DAY_DATE,
            COLUMN_TIME_MINUTES,
            COLUMN_NOTE,
            COLUMN_TIME_LINE,
    };
    public static DailyDetailRecord detailFromCursor(Cursor cursor) {
        DailyDetailRecord record = new DailyDetailRecord();
        record.setIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_INDEX)));
        record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DAY_DATE)));
        int timeInMinutes = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME_MINUTES));
        record.setTotalTime(TimeFormatHelper.minutesToHourMinutes(timeInMinutes));
        record.setTimeLine(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_LINE)));
        return record;
    }

    public static ContentValues toValues(DailyDetailRecord detailRecord) {
        ContentValues values = new ContentValues();
//        values.put(DBColumns.COLUMN_DAY_INDEX, dayIndex);//COLUMN_DAY_INDEX is primary key autoincrement
//        values.put(COLUMN_BRACES_INDEX, detailRecord.getBracesIndex());
//        values.put(COLUMN_DAY_DATE, detailRecord.getDate());
        values.put(COLUMN_TIME_LINE, detailRecord.getTimeLine());
        //detailRecord.getTotalTime() is format as: xxh xxm
        values.put(COLUMN_TIME_MINUTES, TimeSliceHelper.lineToMinutes(detailRecord.getTimeLine()));
        values.put(COLUMN_NOTE, detailRecord.getNote());
        Log.i(TAG, "toValues: " + values);
        return values;

    }

    /*
    public static final String[] DAILY_PROJECTION = new String[]{
            COLUMN_DAY_INDEX,
            COLUMN_DAY_DATE,
            COLUMN_TIME_MINUTES,
            COLUMN_NOTE,
    };

    public static DailyRecord fromCursor(Cursor cursor) {
        DailyRecord record = new DailyRecord();
        record.setIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_INDEX)));
        record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DAY_DATE)));
        int timeInMinutes = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME_MINUTES));
        record.setTotalTime(TimeFormatHelper.minutesToHourMinutes(timeInMinutes));
        return record;
    }*/
}
