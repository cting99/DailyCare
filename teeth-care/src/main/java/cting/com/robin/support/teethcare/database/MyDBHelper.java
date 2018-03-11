package cting.com.robin.support.teethcare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class MyDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "daily_teeth.db";
    public static final int DB_VERSION = 6;
    public static final String TAG = "cting/MyDBHelper";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        db.execSQL(DailyTeeth.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: oldVersion=" + oldVersion + ",newVersion=" + newVersion);
        db.execSQL(DailyTeeth.DELETE_TABLE);
        onCreate(db);
    }
}
