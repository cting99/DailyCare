package cting.com.robin.support.toothcare.models;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.model.IRobinListItem;

public class DailyRecord implements IRobinListItem {

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
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

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
}
