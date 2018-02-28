package cting.com.robin.support.toothcare.models;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.support.toothcare.utils.TimeFormatHelper;

public class DailyRecord implements IRobinListItem {
    public static final String TAG = "cting/tooth/DailyRecord";
    public static final String DAY_INDEX = "DAY_INDEX";
    public static final String DATE = "DATE";
    public static final String NOTES = "NOTES";
    public static final String TIME_SLICE_LIST = "TIME_SLICE_LIST";
    public static final String TOTAL_TIME = "TOTAL_TIME";

    private int dayIndex;
    private String date;
    private String notes;
    private ArrayList<TimeSlice> timeSliceList;
    private String totalTime;

    public DailyRecord() {
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<TimeSlice> getTimeSliceList() {
        return timeSliceList;
    }

    public void setTimeSliceList(ArrayList<TimeSlice> timeSliceList) {
        this.timeSliceList = timeSliceList;
    }

    public String getTotalTime() {
        long totalTimeMillions = 0;
        if (timeSliceList != null) {
            for (TimeSlice timeSlice : timeSliceList) {
                totalTimeMillions += timeSlice.getDurationMillions();
            }
        }
        totalTime = TimeFormatHelper.formatDuration(totalTimeMillions);
        return totalTime;
    }
/*
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }*/

    @Override
    public String toString() {
        return "DailyRecord{" +
                "dayIndex=" + dayIndex +
                ", date='" + date + '\'' +
                ", notes='" + notes + '\'' +
                ", timeSliceList=" + timeSliceList +
                ", totalTime='" + totalTime + '\'' +
                '}';
    }


    public DailyRecord(Bundle bundle) {
        if (bundle != null) {
            this.dayIndex = bundle.getInt(DAY_INDEX);
            this.date = bundle.getString(DATE);
            this.notes = bundle.getString(NOTES);
            this.timeSliceList = bundle.getParcelableArrayList(TIME_SLICE_LIST);
            this.totalTime = bundle.getString(TOTAL_TIME);
        }

    }
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(DAY_INDEX, this.dayIndex);
        bundle.putString(DATE, this.date);
        bundle.putString(NOTES, this.notes);
        bundle.putParcelableArrayList(TIME_SLICE_LIST, this.timeSliceList);
        bundle.putString(TOTAL_TIME, this.totalTime);
        return bundle;
    }
}
