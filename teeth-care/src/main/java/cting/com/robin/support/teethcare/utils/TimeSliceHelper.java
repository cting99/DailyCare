package cting.com.robin.support.teethcare.utils;

import android.util.Log;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.daily.detail.TimeSlice;

public class TimeSliceHelper {
    public static ArrayList<TimeSlice> generateListFromLine(String line) {
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