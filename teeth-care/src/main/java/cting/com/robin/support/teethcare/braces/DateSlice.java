package cting.com.robin.support.teethcare.braces;

import cting.com.robin.support.teethcare.models.SliceItem;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class DateSlice extends SliceItem {
    public static final String pattern = TimeFormatHelper.DATA_FORMAT;

    @Override
    public String getDiff() {
        return String.valueOf(getDiffInNumeric());
    }

    @Override
    public long getDiffInNumeric() {  // day count
        return TimeFormatHelper.getDayCountByDate(from, to);
    }


    public static DateSlice generateByLine(String line) {
        String[] items = line.split(",");
        if (items == null || items.length < 2) {
            return null;
        }
        DateSlice slice = new DateSlice();
        slice.setFrom(items[0]);
        slice.setTo(items[1]);
        return slice;
    }
    public static int calculateDayCount(String line) {
        DateSlice slice = generateByLine(line);
        if (slice != null) {
            return (int) slice.getDiffInNumeric();
        } else {
            return 0;
        }
    }

    public static String calculateTotalTime(String line) {
        long timeMillions = 0;
/*
        DateSlice slice = generateByLine(line);
        if (slice != null) {
            for (:
                 ) {

            }
        } else {
            return 0;
        }*/
        return TimeFormatHelper.formatDuration(timeMillions);
    }
}
