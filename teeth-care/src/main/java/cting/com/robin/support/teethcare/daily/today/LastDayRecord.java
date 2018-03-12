package cting.com.robin.support.teethcare.daily.today;

import android.database.Cursor;

import cting.com.robin.support.teethcare.database.DBColumns;

import static cting.com.robin.support.teethcare.braces.BracesRecord.COLUMN_DAY_COUNT;
import static cting.com.robin.support.teethcare.database.DBColumns.*;

public class LastDayRecord {

    private int dayIndex;
    private String dayDate;
    private int bracesIndex;
    private int bracesDayCount;

    public int getDayIndex() {
        return dayIndex;
    }

    public String getDayDate() {
        return dayDate;
    }

    public int getBracesIndex() {
        return bracesIndex;
    }

    public int getBracesDayCount() {
        return bracesDayCount;
    }

    public LastDayRecord(int dayIndex, String dayDate, int bracesIndex, int bracesDayCount) {
        this.dayIndex = dayIndex;
        this.dayDate = dayDate;
        this.bracesIndex = bracesIndex;
        this.bracesDayCount = bracesDayCount;
    }

    public static final String[] LAST_DAY_PROJECTION = new String[]{
            COLUMN_DAY_INDEX,
            COLUMN_DAY_DATE,
            COLUMN_BRACES_INDEX,
            "COUNT(" + COLUMN_BRACES_INDEX + ") as " + COLUMN_DAY_COUNT
    };
    public static LastDayRecord fromCursor(Cursor cursor) {
        return new LastDayRecord(
                cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_INDEX)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DAY_DATE)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_BRACES_INDEX)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_COUNT))
        );
    }
}
