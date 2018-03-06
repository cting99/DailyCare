package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import cting.com.robin.support.commom.utils.StringHelper;
import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.daily.detail.TimeSlice;

class SourceRawFile extends DataGenerator {
    public static final String FILE_INVISALIGN_ASSET = "invisalign_raw.txt";
    public static final String TAG = "cting/SourceRawFile";

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
                //read new braces
                braces = readBracesRecord(items);
                mBracesList.add(braces);
            } else if (isDailyRecordLine(items)) {
                //read new daily
                daily = readDailyRecord(items);
                mDailyList.add(daily);

                //start this braces
                if (braces != null) {
                    if (TextUtils.isEmpty(braces.getDateSlice().getFrom())) {
                        braces.setFromDate(daily.getDate());
                    } else {
                        braces.setToDate(daily.getDate());
                    }
                }
            }
        }
        in.close();
    }


    private boolean isBracesLine(String[] lineItems) {
        return lineItems != null && lineItems.length == 2;
    }

    private BracesRecord readBracesRecord(String[] items) {
        int bracesIndex = StringHelper.getNumbers(items[0]);
        BracesRecord braces = new BracesRecord.Builder()
                .index(bracesIndex)
                .build();
        return braces;
    }

    private boolean isDailyRecordLine(String[] lineItems) {
        return lineItems != null && lineItems.length > 0 && TextUtils.isDigitsOnly(lineItems[0]);
    }

    private DailyRecord readDailyRecord(String[] lineItems) {
        DailyRecord.Builder builder= new DailyRecord.Builder()
                .index(mDailyList.size() + 1)
                .date(lineItems[1])
                .note(lineItems[3]);

        int count = lineItems.length;
        String from;
        String to;
        if ("0:00".equals(lineItems[count - 1])) {
            lineItems[count - 1] = "24:00";
        }
        for (int i = 4; i < count; i++) {
            from = lineItems[i];
            if (TextUtils.isEmpty(from)) {
                continue;
            }
            if (i + 1 >= count) {
                to = "24:00";
            } else {
                to = lineItems[++i];
            }
            TimeSlice timeSlice = new TimeSlice.Builder()
                    .from(from)
                    .to(to)
                    .build();
            builder.timeSlice(timeSlice);
        }
        return builder.build();
    }
}
