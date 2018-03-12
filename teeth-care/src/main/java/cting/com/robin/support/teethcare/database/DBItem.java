package cting.com.robin.support.teethcare.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.Expose;

import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_BRACES_INDEX;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_DAY_DATE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_DAY_INDEX;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_NOTE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_TIME_LINE;
import static cting.com.robin.support.teethcare.database.DBColumns.COLUMN_TIME_MINUTES;

public class DBItem {
    @Expose
    private int dayIndex;
    @Expose
    private int bracesIndex;
    @Expose
    private String dayDate;
    @Expose
    private String timeLine;
    @Expose
    private int timeMinutes;
    @Expose
    private String note;

    public DBItem() {
    }

    private int getDayIndex() {
        return dayIndex;
    }

    private void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    private int getBracesIndex() {
        return bracesIndex;
    }

    private void setBracesIndex(int bracesIndex) {
        this.bracesIndex = bracesIndex;
    }

    private String getDayDate() {
        return dayDate;
    }

    private void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    private String getTimeLine() {
        return timeLine;
    }

    private void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    private int getTimeMinutes() {
        return timeMinutes;
    }

    private void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    private String getNote() {
        return note;
    }

    private void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "DBItem{" +
                "dayIndex=" + dayIndex +
                ", bracesIndex=" + bracesIndex +
                ", dayDate='" + dayDate + '\'' +
                ", timeLine='" + timeLine + '\'' +
                ", timeMinutes=" + timeMinutes +
                ", note='" + note + '\'' +
                '}';
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
//        values.put(DBColumns.COLUMN_DAY_INDEX, dayIndex);//COLUMN_DAY_INDEX is primary key autoincrement
        values.put(COLUMN_BRACES_INDEX, bracesIndex);
        values.put(COLUMN_DAY_DATE, dayDate);
        values.put(COLUMN_TIME_LINE, timeLine);
        values.put(COLUMN_TIME_MINUTES, timeMinutes);
        values.put(COLUMN_NOTE, note);
        return values;
    }

    public static DBItem fromCursor(Cursor cursor) {
        DBItem item;
        item = new DBItem.Builder()
                .bracesIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_BRACES_INDEX)))
                .dayDate(cursor.getString(cursor.getColumnIndex(COLUMN_DAY_DATE)))
                .timeLine(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_LINE)))
                .timeMinutes(cursor.getInt(cursor.getColumnIndex(COLUMN_TIME_MINUTES)))
                .note(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)))
                .build();
        item.setDayIndex(cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_INDEX)));
        return item;
    }

    public static class Builder {
        private DBItem item;

        public Builder() {
            item=new DBItem();
        }

        public DBItem build() {
            return item;
        }

        public Builder dayIndex(int dayIndex) {   //day index is primary key autoincrement
            item.dayIndex = dayIndex;
            return this;
        }

        public Builder bracesIndex(int bracesIndex) {
            item.bracesIndex = bracesIndex;
            return this;
        }

        public Builder dayDate(String dayDate) {
            item.dayDate = dayDate;
            return this;
        }

        public Builder timeLine(String timeLine) {
            item.timeLine = timeLine;
            return this;
        }

        public Builder timeMinutes(int timeMinutes) {
            item.timeMinutes = timeMinutes;
            return this;
        }

        public Builder note(String note) {
            item.note = note;
            return this;
        }

        public static DBItem createDefault() {
            return new Builder()
                    .bracesIndex(1)
                    .dayDate(TimeFormatHelper.formatToday())
                    .timeLine("00:00")
                    .timeMinutes(0)
                    .note("")
                    .build();
        }

        public static DBItem buildSimple(String date, int bracesIndex) {
            return new Builder()
                    .bracesIndex(bracesIndex)   // not decide current braces index
                    .dayDate(date)
                    .timeLine("00:00")
                    .timeMinutes(0)
                    .note("")
                    .build();
        }
    }
}
