package cting.com.robin.support.toothcare.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatHelper {
    public static final String TAG = "cting/teethdaily/helper";

    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;

    public static final String TIME_FORMAT = "HH:mm";
    public static final String TIME_FORMAT_ERROR = "--";

    public static final String formatTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    public static final long getDuration(String startTimeText, String endTimeText) {
        return getDuration(startTimeText, endTimeText, "HH:mm");
    }

    public static final long getDuration(String startText, String endText, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date startDate = dateFormat.parse(startText);
            Date endDate = dateFormat.parse(endText);
            return endDate.getTime() - startDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static final String formatDuration(String startTimeText, String endTimeText) {
        return formatDuration(getDuration(startTimeText, endTimeText));
    }

    public static final String formatDuration(long startTime, long endTime) {
        return formatDuration(endTime - startTime);
    }

    public static final String formatDuration(long duration) {
        int hour = (int) (duration / ONE_HOUR);
        int minute = (int) (duration % ONE_HOUR / ONE_MINUTE);

        StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append(String.format("%sh ", hour));
        }
        if (minute > 0) {
            sb.append(String.format("%sm", minute));
        }
        if (sb.length() == 0) {
            sb.append(TIME_FORMAT_ERROR);
        }
        return sb.toString();
    }

    public static int getDayCountByDate(String startDateText, String endDateText) {
        return getDayCountByDate(startDateText, endDateText, "yyyy/MM/dd");
    }

    public static int getDayCountByDate(String startDateText, String endDateText, String format) {
        long durationMillions = getDuration(startDateText, endDateText, format);
        int dayCount = (int) (durationMillions / ONE_DAY);
        return dayCount;
    }
}
