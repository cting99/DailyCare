package cting.com.robin.support.teethcare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MyDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "daily_teeth.db";
    public static final int VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DailyTable.CREATE_TABLE);
        db.execSQL(BracesTable.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DailyTable.DELETE_TABLE);
        db.execSQL(BracesTable.DELETE_TABLE);
        onCreate(db);
    }
}
