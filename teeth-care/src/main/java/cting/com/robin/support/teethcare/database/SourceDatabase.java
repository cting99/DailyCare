package cting.com.robin.support.teethcare.database;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailRecord;
import cting.com.robin.support.teethcare.daily.today.LastDayRecord;
import cting.com.robin.support.teethcare.repository.DataGenerator;
import cting.com.robin.support.teethcare.repository.MySource;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

import static cting.com.robin.support.teethcare.braces.BracesRecord.BRACES_PROJECTION;
import static cting.com.robin.support.teethcare.daily.DailyRecord.DAILY_PROJECTION;
import static cting.com.robin.support.teethcare.database.DailyTeeth.*;

public class SourceDatabase {
    public static final String TAG = "cting/SourceDatabase";

    public static final String ORDER_BY_DATE_DESC = COLUMN_DAY_DATE + " DESC";
    public static final String ORDER_BY_BRACES_DESC = COLUMN_BRACES_INDEX + " DESC";
    public static final String ORDER_BY_DAY_INDEX_DESC = COLUMN_DAY_INDEX + " DESC";
    private Context mContext;
    private SQLiteDatabase mDB;
    private SQLiteOpenHelper mOpenHelper;

    public SourceDatabase(Context context) {
        mContext = context;
        mOpenHelper = new MyDBHelper(mContext);
        mDB = mOpenHelper.getWritableDatabase();
    }

    private void open() {
        mDB = mOpenHelper.getWritableDatabase();
    }

    private void close() {
        mDB.close();
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(mDB, TABLE, null);
    }

    public SQLiteDatabase getDB() {
        return mDB;
    }

    public void initDB(Context context) {
        DataGenerator generator = MySource.getDataGenerator(context);
        Log.i(TAG, "initDB from " + generator.getName());
        insertBatchItems(generator.getList(context));
    }

    public long insertItem(DBItem item) {
        if (item == null) {
            Log.w(TAG, "insertItem: item empty");
            return 0;
        }
        open();
        long rowId = mDB.insert(TABLE, null, item.toValues());
        close();
        return rowId;
    }

    public long insertItem(String date, int bracesIndex) {
        if (TextUtils.isEmpty(date)) {
            Log.w(TAG, "insertItem: date empty");
            return 0;
        }
        open();
        long rowId = mDB.insert(TABLE, null, DBItem.Builder.buildSimple(date, bracesIndex).toValues());
        close();
        return rowId;
    }

    private void insertDefaultFirstItem() {
        DBItem item = DBItem.Builder.createDefault();
        Log.i(TAG, "insertDefaultFirstItem: " + item);
        insertItem(item);
    }

    public long insertBatchItems(ArrayList<DBItem> items) {
        if (items == null || items.size() == 0) {
            Log.w(TAG, "insertBatchItems: items empty");
            return -1;
        }
        open();
        mDB.beginTransaction();
        boolean flag = true;
        long lastRowId = -1;
        try {
            for (DBItem item : items) {
                lastRowId = mDB.insert(TABLE, null, item.toValues());
                if (lastRowId < 0) {
                    flag = false;
                    Log.e(TAG, "insertBatchItems failure: " + item);
                    break;
                }
            }
            if (flag) {
                mDB.setTransactionSuccessful();
            }
            return lastRowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            mDB.endTransaction();
            Log.i(TAG, "insertBatchItems: success=" + flag + ",count=" + items.size());
            close();
        }
    }

//    // startDate exist,create the day after startDate until the endDate
//    // return dayIndex
//    public int insertAbsentItems(String startDate, String endDate) {
//        ArrayList<DBItem> items = new ArrayList<>();
//        SimpleDateFormat format = new SimpleDateFormat(TimeFormatHelper.DATA_FORMAT);
//        Calendar calendar = Calendar.getInstance();
//        try {
//            int dayCount = TimeFormatHelper.getDayCountByDate(startDate, endDate);
//            calendar.setTime(format.parse(startDate));
//            for (int i = 0; i < dayCount; i++) {
//                calendar.add(Calendar.DATE, 1);
//                String date = format.format(calendar.getTimeInMillis());
//                items.add(DBItem.Builder.buildSimple(date));
//            }
//            insertBatchItems(items);
//            return queryLastDayIndex();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }

    public ArrayList<DailyRecord> queryDays() {
        open();
        Cursor cursor = mDB.query(TABLE, DAILY_PROJECTION,
                null, null, null, null, ORDER_BY_DATE_DESC);
        ArrayList<DailyRecord> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(DailyRecord.fromCursor(cursor));
            }
            cursor.close();
        }
        close();
        return list;
    }


    public ArrayList<DailyRecord> queryDays(int bracesIndex) {
        open();
        Cursor cursor = mDB.query(TABLE, DAILY_PROJECTION,
                COLUMN_BRACES_INDEX + "=?", new String[]{String.valueOf(bracesIndex)},
                null, null, ORDER_BY_DATE_DESC);
        ArrayList<DailyRecord> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(DailyRecord.fromCursor(cursor));
            }
            cursor.close();
        }
        close();
        return list;
    }

    //select day_index from daily_teeth order by day_index DESC limit 1;
    private int queryLastDayIndex() {
        open();
        Cursor cursor = mDB.query(TABLE, new String[]{COLUMN_DAY_INDEX}, null, null,
                null, null, ORDER_BY_DAY_INDEX_DESC, "1");
        try {
            if (cursor != null && cursor.moveToNext()) {
                return cursor.getInt(0);
            } else {
                return -1;
            }
        } finally {
            close();
        }
    }

    //select * from daily_teeth order by day_index DESC limit 1;
    //select braces_index,day_date,COUNT(braces_index) from daily_teeth group by braces_index order by day_index DESC limit 1;
    public LastDayRecord queryLastDay() {
        open();
        Cursor cursor = mDB.query(TABLE, LastDayRecord.LAST_DAY_PROJECTION, null, null,
                COLUMN_BRACES_INDEX, null, ORDER_BY_DAY_INDEX_DESC, "1");
        try {
            if (cursor != null && cursor.moveToNext()) {
                LastDayRecord record = LastDayRecord.fromCursor(cursor);
                cursor.close();
                return record;
            } else {
                insertDefaultFirstItem();
                return queryLastDay();
            }
        } finally {
            close();
        }
    }

    public int updateDaily(DailyDetailRecord detailRecord) {
        open();
        int count = mDB.update(TABLE, detailRecord.toValues(detailRecord),
                COLUMN_DAY_INDEX + "=?", new String[]{String.valueOf(detailRecord.getIndex())});
        close();
        return count;
    }

    // select day_index,day_date,time_minutes,note,time_line from daily_teeth where day_index=xx order by day_index limit 1
    public DailyDetailRecord queryDayDetailRecord(int dayIndex) {
        open();
        Cursor cursor = mDB.query(TABLE, DailyDetailRecord.DAILY_DETAIL_PROJECTION,
                COLUMN_DAY_INDEX + "=?", new String[]{String.valueOf(dayIndex)},
                null, null, ORDER_BY_DAY_INDEX_DESC, "1");
        try {
            if (cursor != null && cursor.moveToNext()) {
                DailyDetailRecord record = DailyDetailRecord.detailFromCursor(cursor);
                cursor.close();
                return record;
            }
        } finally {
            close();
        }
        return null;
    }


    /*
    * select braces_index,
    *       COUNT(braces_index) as braces_day_count,
    *       MIN(day_date) as braces_start_day,
    *       MAX(day_date) as braces_end_day,
    *       SUM(time_minutes) as braces_total_time,
    *       note
    * from daily_teeth
    * order by braces_index desc;
    * */
    public ArrayList<BracesRecord> queryBraces() {
        open();
        Cursor cursor = mDB.query(TABLE, BRACES_PROJECTION,
                null, null, COLUMN_BRACES_INDEX, null, ORDER_BY_BRACES_DESC);
        ArrayList<BracesRecord> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(BracesRecord.fromCursor(cursor));
            }
            cursor.close();
        }
        close();
        Log.i(TAG, "queryBraces, size:" + list.size());
        return list;
    }

    public ArrayList<DBItem> queryAll() {
        open();
        ArrayList<DBItem> list = new ArrayList<>();
        Cursor cursor = mDB.query(TABLE, DailyTeeth.ALL_COLUMNS,
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(DBItem.fromCursor(cursor));
            }
            cursor.close();
        }
        Log.i(TAG, "queryAll, size:" + list.size());
        close();
        return list;
    }
}
