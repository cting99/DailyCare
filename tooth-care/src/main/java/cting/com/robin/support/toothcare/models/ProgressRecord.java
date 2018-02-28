package cting.com.robin.support.toothcare.models;

import cting.com.robin.support.recyclerview.model.IRobinListItem;

/**
 * Created by cting on 2018/2/28.
 */

public class ProgressRecord implements IRobinListItem {
    private int progressIndex;
    private String startDate;
    private String endDate;
    private String dayCount;
    private String totalTime;

    public ProgressRecord() {
    }

    public int getProgressIndex() {
        return progressIndex;
    }

    public void setProgressIndex(int progressIndex) {
        this.progressIndex = progressIndex;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "ProgressRecord{" +
                "progressIndex=" + progressIndex +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", dayCount='" + dayCount + '\'' +
                ", totalTime='" + totalTime + '\'' +
                '}';
    }
}
