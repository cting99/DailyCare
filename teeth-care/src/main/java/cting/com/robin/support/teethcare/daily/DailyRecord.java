package cting.com.robin.support.teethcare.daily;

import android.database.Cursor;
import android.databinding.Bindable;

import cting.com.robin.support.teethcare.BR;
import cting.com.robin.support.teethcare.models.IRecord;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_DAY_DATE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_DAY_INDEX;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_NOTE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_TIME_MINUTES;

public class DailyRecord extends IRecord {

    private int index;
    private String date;
    private String totalTime;
    private String note;

    public DailyRecord() {
    }

    @Bindable
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        notifyPropertyChanged(BR.index);
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
        notifyPropertyChanged(BR.totalTime);
    }

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }

    @Override
    public String toString() {
        return "DailyRecord{" +
                "index=" + index +
                ", date='" + date + '\'' +
                ", totalTime=" + totalTime +
                ", note='" + note + '\'' +
                '}';
    }

    public static final String[] DAILY_PROJECTION = new String[]{
            COLUMN_DAY_INDEX,
            COLUMN_DAY_DATE,
            COLUMN_TIME_MINUTES,
            COLUMN_NOTE,
    };

    public static DailyRecord fromCursor(Cursor cursor) {
        DailyRecord record = new DailyRecord();
        record.setIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_INDEX)));
        record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DAY_DATE)));
        int timeInMinutes = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME_MINUTES));
        record.setTotalTime(TimeFormatHelper.minutesToHourMinutes(timeInMinutes));
        return record;
    }
}
