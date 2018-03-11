package cting.com.robin.support.teethcare.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.daily.detail.TimeSlice;

public class TimeSliceHelper {

    public static int lineToMinutes(String timeLine) {
        if (TextUtils.isEmpty(timeLine)) {
            return 0;
        }
        int minutes = 0;
        String[] lineItems = timeLine.split(",");
        int count = lineItems.length;
        for (int i = 0; i < count; i += 2) {
            if (i + 1 < count) {
                minutes += TimeFormatHelper.getDuration(lineItems[i], lineItems[i + 1]) / TimeFormatHelper.ONE_MINUTE;
            }
        }
        return minutes;
    }

    public static ArrayList<TimeSlice> generateListFromLine(String line) {
        if (TextUtils.isEmpty(line)) {
            return null;
        }
        ArrayList<TimeSlice> list = new ArrayList<TimeSlice>(5);
        String[] lineItems = line.split(",");
        int count = lineItems.length;
        TimeSlice slice;
        for (int i = 0; i < count; i++) {
            slice = new TimeSlice();
            slice.setFrom(lineItems[i]);
            if (i + 1 >= count) {
                slice.setTo("");
            } else {
                slice.setTo(lineItems[++i]);
            }
            list.add(slice);
        }
        return list;
    }

    public static String calculateTotalTime(String line) {
        ArrayList<TimeSlice> list = generateListFromLine(line);
        if (list == null) {
            return null;
        }
        long timeMillions = 0;
        for (TimeSlice slice : list) {
            timeMillions += slice.getDiffInNumeric();
        }
        return TimeFormatHelper.formatDuration(timeMillions);
    }

    public static final void toLine(TimeSlice slice, StringBuilder builder, boolean lastOne) {
        builder.append(slice.getFrom())
                .append(",")
                .append(slice.getTo());
        if (!lastOne) {
            builder.append(",");
        }
    }

    public static String toLine(ArrayList<TimeSlice> list) {
        StringBuilder builder = new StringBuilder();
        int lastIndex = list.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            toLine(list.get(i), builder, i == lastIndex);
        }
        return builder.toString();
    }
}