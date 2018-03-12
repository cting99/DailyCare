package cting.com.robin.support.teethcare.daily.today;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;
import cting.com.robin.support.teethcare.database.DBItem;
import cting.com.robin.support.teethcare.database.SourceDatabase;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class NewDayManager implements DialogInterface.OnClickListener {
    private static final boolean ALWAYS_ASK = true;
    public static final String TAG = "cting/NewDay";
    public static final int DEFAULT_BRACES_NEEDED_DAY_COUNT = 8;
    private SourceDatabase mDBSource;
    private Context mContext;

    public NewDayManager(Context context) {
        mContext = context;
    }

    public void gotoToday() {
        mDBSource = new SourceDatabase(mContext);
//

        LastDayRecord lastDay = mDBSource.queryLastDay();
        int lastBracesDayCount = lastDay.getBracesDayCount();
        String lastDate = lastDay.getDayDate();

        int todayDayIndex = -1;
        int todayBracesIndex = getAssumeBracesIndex(lastBracesDayCount, lastDay.getBracesIndex());
        String todayDate = TimeFormatHelper.formatToday();
        int missedDayCount = TimeFormatHelper.getDayCountByDate(lastDate, todayDate);

        if (missedDayCount==0) {
            //today exist
            todayDayIndex = lastDay.getDayIndex();
        } else if (missedDayCount == 1) {
            //create today
            Log.i(TAG, "gotoToday: create today");
            todayDayIndex = (int) mDBSource.insertItem(todayDate, todayBracesIndex);
        } else {
            //create today and all absent days between today and last day
            Log.w(TAG, "gotoToday: insert from " + lastDate + " to " + todayDate);
            Toast.makeText(mContext, "insert from " + lastDate + " to " + todayDate, Toast.LENGTH_SHORT).show();

            ArrayList<DBItem> items = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat(TimeFormatHelper.DATA_FORMAT);
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(format.parse(lastDate));
                int bracesIndex = lastDay.getBracesIndex();
                int bracesDayCount = lastDay.getBracesDayCount();
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

    private int getAssumeBracesIndex(int lastBracesDayCount,int lastBracesIndex) {
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