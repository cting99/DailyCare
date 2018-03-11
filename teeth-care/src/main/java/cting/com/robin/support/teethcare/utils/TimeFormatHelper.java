package cting.com.robin.support.teethcare.utils;

import android.text.TextUtils;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormatHelper {
    public static final String TAG = "cting/teethdaily/helper";

    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;

    public static final String TIME_FORMAT = "HH:mm";
    public static final String TIME_FORMAT_ERROR = "--";
    public static final String DATA_FORMAT = "yyyy/MM/dd";
    public static final String DATE_RANGE_FORMAT = "yyyy/MM/dd -- yyyy/MM/dd";

    public static String reFormat(String dateStr, String pattern, String newPattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try{
            Date date = format.parse(dateStr);
            return new SimpleDateFormat(newPattern).format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return dateStr;
    }

    private static final String format(long time, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(time));
    }

    public static final String formatTime(long time) {
        return format(time, TIME_FORMAT);
    }

    public static final String formatNow() {
        return formatTime(System.currentTimeMillis());
    }

    public static final String formatData(long time) {
        return format(time, DATA_FORMAT);
    }

    public static final String formatToday() {
        return formatData(System.currentTimeMillis());
    }


    public static boolean isToday(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
        try {
            Date date = dateFormat.parse(dateStr);
            return DateUtils.isToday(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSameDay(String dateStr1, String dateStr2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
        try {
            Date date1 = dateFormat.parse(dateStr1);
            Date date2 = dateFormat.parse(dateStr2);
            return date1.getTime() == date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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
        if (TextUtils.equals(startTimeText, endTimeText)) {
            return "0";
        }
        return formatDuration(getDuration(startTimeText, endTimeText));
    }

    public static final String formatDuration(long startTime, long endTime) {
        return formatDuration(endTime - startTime);
    }

    public static String minutesToHourMinutes(int timeInMinutes) {
        StringBuilder sb = new StringBuilder();
        int hour = (int) (timeInMinutes / 60);
        int minutes = (int) (timeInMinutes - hour * 60);
        if (hour > 0) {
            sb.append(hour).append("h");
            if (minutes > 0) {
                sb.append(" ");
            }
        }
        if (minutes > 0) {
            sb.append(minutes).append("m");
        }
        if (sb.length() == 0) {
            sb.append("0m");
        }
        return sb.toString();
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
        return getDayCountByDate(startDateText, endDateText, DATA_FORMAT);
    }

    public static int getDayCountByDate(String startDateText, String endDateText, String format) {
        long durationMillions = getDuration(startDateText, endDateText, format);
        int dayCount = (int) (durationMillions / ONE_DAY);
        return dayCount;
    }

    public static String formatDateRange(String startDateText, int dayCount) {
        SimpleDateFormat format = new SimpleDateFormat(DATA_FORMAT);
        String endDateText = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(startDateText));
            calendar.add(Calendar.DATE, dayCount);
            endDateText = format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            return startDateText + " ~ " + endDateText;
        }
    }


/*
    public static String formatDuration(long totalMillions, String pattern) {
        if (TIME_FORMAT.equals(pattern)) {
            return formatDuration(totalMillions);
        } else if (DATA_FORMAT.equals(pattern)) {
            return String.valueOf(totalMillions);
        }
        return "";
    }*/
}
