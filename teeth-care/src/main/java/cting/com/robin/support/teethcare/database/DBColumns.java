package cting.com.robin.support.teethcare.database;

import static cting.com.robin.support.teethcare.database.DailyTeeth.TABLE;

public class DBColumns {
//    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAY_INDEX = "day_index";
    public static final String COLUMN_BRACES_INDEX = "braces_index";
    public static final String COLUMN_DAY_DATE = "day_date";
    public static final String COLUMN_TIME_LINE = "time_line";
    public static final String COLUMN_TIME_MINUTES = "time_minutes";
    public static final String COLUMN_NOTE = "note";

    public static final String[] ALL_COLUMNS = {
            COLUMN_DAY_INDEX,
            COLUMN_BRACES_INDEX,
            COLUMN_DAY_DATE,
            COLUMN_TIME_LINE,
            COLUMN_TIME_MINUTES,
            COLUMN_NOTE,
    };

}