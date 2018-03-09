package cting.com.robin.support.teethcare.database;


class DailyTable extends BaseRecordColumns{
    public static final String TABLE_DAILY_RECORD = "daily_record";

    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_LINE = "line";

    public static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_INDEX,
            COLUMN_DATE,
            COLUMN_TOTAL_TIME,
            COLUMN_NOTE,
            COLUMN_LINE,
    };

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_DAILY_RECORD + "("
                    + COLUMN_ID +" TEXT PRIMARY_KEY,"
                    + COLUMN_INDEX +" INTEGER,"
                    + COLUMN_DATE +" TEXT,"
                    + COLUMN_TOTAL_TIME +" TEXT,"
                    + COLUMN_NOTE +" TEXT,"
                    + COLUMN_LINE +" TEXT"
            +")";

    public static final String DELETE_TABLE =
            "DROP TABLE " + TABLE_DAILY_RECORD;
}
