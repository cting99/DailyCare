package cting.com.robin.support.teethcare.database;


class BracesTable extends BaseRecordColumns{
    public static final String TABLE_BRACES_RECORD = "braces_record";

    public static final String COLUMN_DAY_COUNT = "day_count";

    public static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_INDEX,
            COLUMN_DATE,
            COLUMN_TOTAL_TIME,
            COLUMN_DAY_COUNT,
    };

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_BRACES_RECORD + "("
                    + COLUMN_ID +" TEXT PRIMARY_KEY,"
                    + COLUMN_INDEX +" INTEGER,"
                    + COLUMN_DATE +" TEXT,"
                    + COLUMN_TOTAL_TIME +" TEXT,"
                    + COLUMN_DAY_COUNT+" INTEGER"
            +")";

    public static final String DELETE_TABLE =
            "DROP TABLE " + TABLE_BRACES_RECORD;
}
