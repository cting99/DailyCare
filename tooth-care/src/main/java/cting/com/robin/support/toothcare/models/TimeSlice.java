package cting.com.robin.support.toothcare.models;

import cting.com.robin.support.recyclerview.model.IRobinListItem;

public class TimeSlice implements IRobinListItem{
    private String startTime;
    private String endTime;

    public TimeSlice() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TimeSlice{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
