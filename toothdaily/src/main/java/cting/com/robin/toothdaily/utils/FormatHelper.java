package cting.com.robin.toothdaily.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatHelper {
    public static final String TAG = "cting/teethdaily/helper";

    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    public static final String TIME_FORMAT = "HH:mm";
    public static final String TIME_FORMAT_ERROR = "--";

    public static final String formatTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    public static final long getDuration(String startTimeText, String endTimeText) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date startDate = dateFormat.parse(startTimeText);
            Date endDate = dateFormat.parse(endTimeText);
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
}
