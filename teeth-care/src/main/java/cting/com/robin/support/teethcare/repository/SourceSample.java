package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cting.com.robin.support.teethcare.database.DBItem;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;
import cting.com.robin.support.teethcare.utils.TimeSliceHelper;

class SourceSample extends DataGenerator {

    public SourceSample() {
        super("Sample data");
    }

    @Override
    public void forceLoad(Context context) {
        DBItem.Builder builder;

        DateFormat format = new SimpleDateFormat(TimeFormatHelper.DATA_FORMAT);
        try {
            Date startDate = format.parse("2017/10/30");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            String date;
            String timeLine = "00:00,08:30,09:20,11:50,12:20,18:20,19:05,24:00";
            int minutes = TimeSliceHelper.lineToMinutes(timeLine);
            for (int i = 1; i < 53; i++) {
                date = format.format(calendar.getTime());
                for (int j = 1; j < 8; j++) {
                    builder = new DBItem.Builder();
                    int dayIndex = (8 * i + j);
                    builder/*.dayIndex(dayIndex)*/
                            .bracesIndex(i)
                            .dayDate(date)
                            .timeLine(timeLine)
                            .timeMinutes(minutes)
                            .note("this is day " + dayIndex)
                            .build();

                    calendar.add(Calendar.DATE, 1);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
