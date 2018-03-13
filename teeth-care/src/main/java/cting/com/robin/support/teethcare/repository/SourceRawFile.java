package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import cting.com.robin.support.common.utils.StringHelper;
import cting.com.robin.support.teethcare.database.DBItem;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

class SourceRawFile extends DataGenerator {
    public static final String FILE_INVISALIGN_ASSET = "invisalign_raw.txt";

    public SourceRawFile() {
        super("Default raw file");
    }

    @Override
    public void forceLoad(Context context) {
        try {
            readFile(context);
        } catch (Exception e) {
            Log.e(TAG, "forceLoad exception:"+e.getLocalizedMessage() );
            e.printStackTrace();
        }
    }


    public void readFile(Context context) throws Exception {
        InputStream in = context.getAssets().open(FILE_INVISALIGN_ASSET);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        Log.i(TAG, "readFile: " + FILE_INVISALIGN_ASSET);

        int currentBracesIndex = -1;
        while ((line = reader.readLine()) != null) {
            String[] items = line.trim().split("\t");
//            Log.i(TAG, "readFile: read items=" + Arrays.toString(items));
            if (isBracesLine(items)) {
                currentBracesIndex = getBracesIndex(items);
            } else if (isDailyRecordLine(items)) {
                if (currentBracesIndex != -1) {
                    list.add(readDBItem(items, currentBracesIndex));
                } else {
                    Log.e(TAG, "readFile error line:" + line);
                }
            }
        }
        in.close();
    }

    private int getBracesIndex(String[] items) {
        return StringHelper.getNumbers(items[0]);
    }


    private boolean isBracesLine(String[] lineItems) {
        return lineItems != null && lineItems.length == 2;
    }

    private boolean isDailyRecordLine(String[] lineItems) {
        return lineItems != null && lineItems.length > 0 && TextUtils.isDigitsOnly(lineItems[0]);
    }

    private DBItem readDBItem(String[] lineItems, int bracesIndex) {
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

        return new DBItem.Builder()
//                .dayIndex(list.size() + 1)
                .bracesIndex(bracesIndex)
                .dayDate(TimeFormatHelper.reFormat(lineItems[1], "yyyy/M/d", TimeFormatHelper.DATA_FORMAT))
                .timeLine(sb.toString())
                .timeMinutes(formatTotalTime(lineItems[2]))
                .note(lineItems[3])
                .build();
    }

    private int formatTotalTime(String time) {
        //format 18:55 (means 18 hours and 55 minutes) as 18*60+55=1135 minutes
        String[] array = time.split(":");
        try {
            return Integer.valueOf(array[0]) * 60 + Integer.valueOf(array[1]);
        } catch (NumberFormatException e) {
            Log.i(TAG, "formatTotalTime,failure:" + time);
            e.printStackTrace();
            return 0;
        }
    }
}
