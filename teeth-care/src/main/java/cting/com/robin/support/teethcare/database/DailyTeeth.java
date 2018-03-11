package cting.com.robin.support.teethcare.database;


class DailyTeeth extends DBColumns {
    public static final String TABLE = "daily_teeth";

    protected static String column(String columnName, String type) {
        return columnName + " " + type;
    }
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
                    + column(COLUMN_DAY_INDEX,"INTEGER PRIMARY KEY autoincrement,")
                    + column(COLUMN_BRACES_INDEX,"INTEGER,")
                    + column(COLUMN_DAY_DATE,"TEXT,")
                    + column(COLUMN_TIME_LINE,"TEXT,")
                    + column(COLUMN_TIME_MINUTES,"INTEGER,")
                    + column(COLUMN_NOTE,"TEXT")
                    +")";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;
}
