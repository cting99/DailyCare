package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

class SourceRawFile extends DataGenerator {
    public static final String FILE_INVISALIGN_ASSET = "invisalign_raw.txt";
    public static final String TAG = "cting/SourceRawFile";

    public SourceRawFile() {
        super("Default raw file");
    }

    @Override
    public void forceLoad(Context context) {
        super.forceLoad(context);
        try {
            readFile(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void readFile(Context context) throws Exception {
        InputStream in = context.getAssets().open(FILE_INVISALIGN_ASSET);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        BracesRecord braces = null;
        DailyRecord daily = null;
        Log.i(TAG, "readFile: " + FILE_INVISALIGN_ASSET);

        while ((line = reader.readLine()) != null) {
            String[] items = line.trim().split("\t");
//            Log.i(TAG, "readFile: read items=" + Arrays.toString(items));
            if (isBracesLine(items)) {
                // read new braces
                braces = readBracesRecord(items);
            } else if (isDailyRecordLine(items)) {
                // read new daily
                daily = readDailyRecord(items);
                // update this braces
                if (braces != null) {
                    if (TextUtils.isEmpty(braces.getDate())) {
                        braces.setDate(daily.getDate());
                    }
                    braces.setDayCount(braces.getDayCount() + 1);
                }
            }
        }
        in.close();
    }


    private boolean isBracesLine(String[] lineItems) {
        return lineItems != null && lineItems.length == 2;
    }

    private BracesRecord readBracesRecord(String[] items) {
        BracesRecord braces = new BracesRecord();
        braces.setIndex(StringHelper.getNumbers(items[0]));
        braces.setTotalTime(formatTotalTime(items[1]));
        mBracesList.add(braces);
        Log.i(TAG, "readBracesRecord: " + braces);
        return braces;
    }

    @NonNull
    private String formatTotalTime(String time) {
        //format 18:55 as 18h 55m
        return time.replace(":", "h ") + "m";
    }

    private boolean isDailyRecordLine(String[] lineItems) {
        return lineItems != null && lineItems.length > 0 && TextUtils.isDigitsOnly(lineItems[0]);
    }

    private DailyRecord readDailyRecord(String[] lineItems) {
        DailyRecord daily = new DailyRecord();
        daily.setIndex(mDailyList.size() + 1);
        daily.setDate(TimeFormatHelper.reFormat(lineItems[1], "yyyy/M/d", TimeFormatHelper.DATA_FORMAT));
        daily.setTotalTime(formatTotalTime(lineItems[2]));
        daily.setNote(lineItems[3]);

        int totalSize = lineItems.length;
        StringBuilder sb = new StringBuilder();

        for (int i = 4; i < totalSize; i++) {
            if (TextUtils.isEmpty(lineItems[i])) {
                continue;
            }
            if (i == totalSize - 1) {
                if ("0:00".equals(lineItems[i])) {
                    sb.append("24:00");
                } else {
                    sb.append(lineItems[i]);
                }
            } else {
                sb.append(lineItems[i]);
                sb.append(",");
            }
        }
        daily.setLine(sb.toString());
        mDailyList.add(daily);
        Log.i(TAG, "readDailyRecord: " + daily);
        return daily;
    }
}
