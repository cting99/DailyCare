package cting.com.robin.support.teethcare.braces;

import android.database.Cursor;

import cting.com.robin.support.teethcare.models.IRecord;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_BRACES_INDEX;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_DAY_DATE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_NOTE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_TIME_MINUTES;

public class BracesRecord extends IRecord {
    private int index;
    private String dateRange;
    private int dayCount;
    private String totalTime;

    public BracesRecord() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "BracesRecord{" +
                "index=" + index +
                ", dateRange='" + dateRange + '\'' +
                ", dayCount=" + dayCount +
                ", totalTime=" + totalTime +
                '}';
    }


    public static final String COLUMN_DAY_COUNT = "braces_day_count";
    public static final String COLUMN_BRACES_START_DAY = "braces_start_day";
    public static final String COLUMN_BRACES_END_DAY = "braces_end_day";
    public static final String COLUMN_BRACES_TOTAL_TIME = "braces_total_time";

    public static final String[] BRACES_PROJECTION = new String[]{
            COLUMN_BRACES_INDEX,
            "COUNT("+COLUMN_BRACES_INDEX+") as " + COLUMN_DAY_COUNT,
            "MIN(" + COLUMN_DAY_DATE + ") as " + COLUMN_BRACES_START_DAY,
            "MAX(" + COLUMN_DAY_DATE + ") as " + COLUMN_BRACES_END_DAY,
            "SUM(" + COLUMN_TIME_MINUTES + ") as " + COLUMN_BRACES_TOTAL_TIME,
            COLUMN_NOTE,
    };

    public static BracesRecord fromCursor(Cursor cursor) {
        BracesRecord record = new BracesRecord();
        record.setIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_BRACES_INDEX)));
        record.setDayCount(cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_COUNT)));
        record.setTotalTime(TimeFormatHelper.minutesToHourMinutes(cursor.getInt(cursor.getColumnIndex(COLUMN_BRACES_TOTAL_TIME))));
        String startDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BRACES_START_DAY));
        String endDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BRACES_END_DAY));
        StringBuilder sb = new StringBuilder(23);
        sb.append(startDateStr)
                .append(" ~ ")
                .append(endDateStr);
        record.setDateRange(sb.toString());
        return record;
    }
}
