package cting.com.robin.support.teethcare.daily.today;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;
import cting.com.robin.support.teethcare.database.DBItem;
import cting.com.robin.support.teethcare.database.SourceDatabase;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class NewDayManager implements DialogInterface.OnClickListener {
    private static final boolean ALWAYS_ASK = true;
    private static final int ID_CONFIRM_CREATE_NEW_BRACES = 1;

    public static final String TAG = "cting/NewDay";
    public static final int DEFAULT_BRACES_NEEDED_DAY_COUNT = 8;

    private SourceDatabase mDBSource;
    private Context mContext;
    private LastDayRecord mLastDay;
    private String mTodayDate;

    public NewDayManager(Context context) {
        mContext = context;
    }

    public void gotoToday() {
        mDBSource = new SourceDatabase(mContext);

        int todayDayIndex = -1;
        mTodayDate = TimeFormatHelper.formatToday();

        mLastDay = mDBSource.queryLastDay();
        int lastBracesDayCount = mLastDay.getBracesDayCount();
        String lastDate = mLastDay.getDayDate();

        int missedDayCount = TimeFormatHelper.getDayCountByDate(lastDate, mTodayDate);
        if (missedDayCount == 0) {
            //today exist
            todayDayIndex = mLastDay.getDayIndex();
        } else if (missedDayCount == 1) {
            //create today
            Log.i(TAG, "gotoToday: create today");
            if (lastBracesDayCount >= DEFAULT_BRACES_NEEDED_DAY_COUNT) {
                showDialog(ID_CONFIRM_CREATE_NEW_BRACES);
                return;
            } else {
                todayDayIndex = createNewDayForBraces(mLastDay.getBracesIndex());
            }
        } else {
            //create today and all absent days between today and last day
            Log.w(TAG, "gotoToday: insert from " + lastDate + " to " + mTodayDate);
            Toast.makeText(mContext, "insert from " + lastDate + " to " + mTodayDate, Toast.LENGTH_SHORT).show();

            ArrayList<DBItem> items = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat(TimeFormatHelper.DATA_FORMAT);
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(format.parse(lastDate));
                int bracesIndex = mLastDay.getBracesIndex();
                int bracesDayCount = mLastDay.getBracesDayCount();
                for (int i = 0; i < missedDayCount; i++) {
                    calendar.add(Calendar.DATE, 1);
                    String date = format.format(calendar.getTimeInMillis());
                    int currentBracesIndex = getAssumeBracesIndex(bracesDayCount, bracesIndex);
                    if (currentBracesIndex != bracesIndex) {
                        bracesDayCount = 1;
                        bracesIndex = currentBracesIndex;
                    } else {
                        bracesDayCount++;
                    }
                    Log.i(TAG, "add date=" + date + ",bracesIndex=" + bracesIndex);
                    items.add(DBItem.Builder.buildSimple(date, bracesIndex));
                }
                todayDayIndex = (int) mDBSource.insertBatchItems(items);
            } catch (ParseException e) {
                Log.e(TAG, "gotoToday exception:" + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        goNow(todayDayIndex);
    }

    private int createNewDayForBraces(int bracesIndex) {
        return (int) mDBSource.insertItem(mTodayDate, bracesIndex);

    }

    private void showDialog(int id) {
        switch (id) {
            case ID_CONFIRM_CREATE_NEW_BRACES:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Confirm")
                        .setMessage("Are you finish braces " + mLastDay.getBracesIndex() + "(Day" + mLastDay.getBracesDayCount() + ")")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int newDayIndex = createNewDayForBraces(mLastDay.getBracesIndex() + 1);
                                goNow(newDayIndex);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int newDayIndex = createNewDayForBraces(mLastDay.getBracesIndex());
                                goNow(newDayIndex);
                            }
                        });
                builder.setCancelable(false);
                builder.create().show();
                break;
        }
    }

    private int getAssumeBracesIndex(int lastBracesDayCount, int lastBracesIndex) {
        return lastBracesDayCount >= DEFAULT_BRACES_NEEDED_DAY_COUNT ? lastBracesIndex + 1 : lastBracesIndex;
    }

    public void goNow(int dayIndex) {
        if (dayIndex >= 0) {
            DailyDetailActivity.launch(mContext, dayIndex, true);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}