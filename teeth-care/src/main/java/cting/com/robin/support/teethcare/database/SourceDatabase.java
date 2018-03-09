package cting.com.robin.support.teethcare.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.utils.DBSelectionHelper;

import static cting.com.robin.support.teethcare.database.BracesTable.*;
import static cting.com.robin.support.teethcare.database.DailyTable.*;

public class SourceDatabase {

    public static final String ORDER_BY_DATE_DESC = COLUMN_DATE + " DESC";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mOpenHelper;

    public SourceDatabase(Context context) {
        mContext = context;
        mOpenHelper = new MyDBHelper(mContext);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void close() {
        mDatabase.close();
    }


    private ContentValues dailyRecordToValues(DailyRecord record) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_INDEX, record.getIndex());
        values.put(COLUMN_DATE, record.getDate());
        values.put(COLUMN_TOTAL_TIME, record.getTotalTime());
        values.put(COLUMN_NOTE, record.getNote());
        values.put(COLUMN_LINE, record.getLine());
        return values;
    }

    private DailyRecord cursorToDailyRecord(Cursor cursor) {
        DailyRecord record = new DailyRecord();
        record.setIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_INDEX)));
        record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
        record.setTotalTime(cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL_TIME)));
        record.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
        record.setLine(cursor.getString(cursor.getColumnIndex(COLUMN_LINE)));
        return record;
    }

    public long insertDaily(DailyRecord record) {
        ContentValues values = dailyRecordToValues(record);
        return mDatabase.insert(TABLE_DAILY_RECORD, null, values);
    }

    public boolean insertDailyList(ArrayList<DailyRecord> list) {
        mDatabase.beginTransaction();
        boolean flag = true;
        try {
            for (DailyRecord record : list) {
                if (insertDaily(record) < 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                mDatabase.setTransactionSuccessful();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mDatabase.endTransaction();
        }
    }

    public ArrayList<DailyRecord> queryDaily(String date) {
        String where = TextUtils.isEmpty(date) ? null : COLUMN_DATE + "=" + date;
        Cursor cursor = mDatabase.query(TABLE_DAILY_RECORD, DailyTable.ALL_COLUMNS,
                where, null, null, null, ORDER_BY_DATE_DESC);
        return cursorToDailyList(cursor);
    }

    private ArrayList<DailyRecord> cursorToDailyList(Cursor cursor) {
        ArrayList<DailyRecord> records = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                records.add(cursorToDailyRecord(cursor));
            }
            cursor.close();
        }
        return records;
    }

    public long getDailyCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, TABLE_DAILY_RECORD, null);
    }


    public DailyRecord queryTheDaily(String date) {
        Cursor cursor = mDatabase.query(DailyTable.TABLE_DAILY_RECORD, DailyTable.ALL_COLUMNS,
                COLUMN_DATE + "=?", new String[]{date}, null,
                null, null, "1");
        if (cursor != null && cursor.moveToNext()) {
            DailyRecord record = cursorToDailyRecord(cursor);
            cursor.close();
            return record;
        }
        return null;
    }

    public DailyRecord queryLastDay(Context mContext) {
        Cursor cursor = mDatabase.query(DailyTable.TABLE_DAILY_RECORD, DailyTable.ALL_COLUMNS,
                DBSelectionHelper.getMaxClause(TABLE_DAILY_RECORD, COLUMN_DATE),
                null, null,null, null, "1");
        if (cursor != null && cursor.moveToNext()) {
            DailyRecord record = cursorToDailyRecord(cursor);
            cursor.close();
            return record;
        }
        return null;
    }

    public void updateDaily(DailyRecord record) {
        mDatabase.update(DailyTable.TABLE_DAILY_RECORD, dailyRecordToValues(record)
                , COLUMN_INDEX + "=?", new String[]{String.valueOf(record.getIndex())});
    }


    /*
    *   Braces records
    * */

    /*private static final String wrapWithQuoteMark(String string) {
        return "\"" + string + "\"";
    }*/

    private ContentValues bracesRecordToValues(BracesRecord record) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_INDEX, record.getIndex());
        values.put(COLUMN_DATE, record.getDate());
        values.put(COLUMN_TOTAL_TIME, record.getTotalTime());
        values.put(COLUMN_DAY_COUNT, record.getDayCount());
        return values;
    }

    private BracesRecord cursorToBraces(Cursor cursor) {
        BracesRecord record = new BracesRecord();
        record.setIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_INDEX)));
        record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
        record.setTotalTime(cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL_TIME)));
        record.setDayCount(cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_COUNT)));
        return record;
    }

    private ArrayList<BracesRecord> cursorToBracesList(Cursor cursor) {
        ArrayList<BracesRecord> records = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                records.add(cursorToBraces(cursor));
            }
            cursor.close();
        }
        return records;
    }

    public long insertBraces(BracesRecord record) {
        return mDatabase.insert(TABLE_BRACES_RECORD, null, bracesRecordToValues(record));
    }

    public boolean insertBracesList(ArrayList<BracesRecord> list) {
        mDatabase.beginTransaction();
        boolean flag = true;
        try {
            for (BracesRecord record : list) {
                if (insertBraces(record) < 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                mDatabase.setTransactionSuccessful();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mDatabase.endTransaction();
        }
    }

    public ArrayList<BracesRecord> queryBraces(int i) {
        String where = i > 0 ? (COLUMN_INDEX + "=" + i) : null;
        Cursor cursor = mDatabase.query(TABLE_BRACES_RECORD, BracesTable.ALL_COLUMNS,
                where, null, null, null, ORDER_BY_DATE_DESC);
        return cursorToBracesList(cursor);
    }

    public long getRecordCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, TABLE_BRACES_RECORD, null);
    }

    public BracesRecord queryLastBraces(Context mContext) {
        Cursor cursor = mDatabase.query(TABLE_BRACES_RECORD, BracesTable.ALL_COLUMNS,
                DBSelectionHelper.getMaxClause(TABLE_BRACES_RECORD, COLUMN_INDEX),
                null, null, null, null, "1");
        if (cursor != null && cursor.moveToNext()) {
            BracesRecord record = cursorToBraces(cursor);
            cursor.close();
            return record;
        }
        return null;
    }
}
