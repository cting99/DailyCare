package cting.com.robin.toothdaily.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import java.util.ArrayList;

import cting.com.robin.toothdaily.BR;
import cting.com.robin.toothdaily.utils.FormatHelper;


public class DailyRecord extends BaseObservable {

    public static final String TAG = "cting/tooth/DailyRecord";
    String date;
    String notes;
    ArrayList<TimePeriod> periodList;

    public DailyRecord(String date, String notes, String rawTimeList) {
        this.date = date;
        this.notes = notes;
        String[] list = rawTimeList.split(",");
        int count = list.length;
        periodList = new ArrayList<>(count / 2 + count % 2);
        TimePeriod period = null;
        for (int i = 0; i < count; i++) {
            if (period == null) {
                period = new TimePeriod();
                period.setStartTime(list[i]);
            } else {
                period.setEndTime(list[i]);
                periodList.add(period);
                period = null;
            }
        }
    }

    public DailyRecord(String date, String notes, ArrayList<TimePeriod> periodList) {
        this.date = date;
        this.notes = notes;
        this.periodList = periodList;
    }

    public DailyRecord() {
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
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        notifyPropertyChanged(BR.notes);
    }

    @Bindable
    public ArrayList<TimePeriod> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(ArrayList<TimePeriod> periodList) {
        this.periodList = periodList;
        notifyPropertyChanged(BR.periodList);
    }

    @Bindable
    public String getTotalCost() {
        long totalCost = 0;
        for (TimePeriod timePeriod : periodList) {
            totalCost += FormatHelper.getDuration(timePeriod.getStartTime(), timePeriod.getEndTime());
        }
        Log.i(TAG, "getTotalCost: " + totalCost);
        return FormatHelper.formatDuration(totalCost);
    }

}
