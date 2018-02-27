package cting.com.robin.toothdaily.model;

import cting.com.robin.support.recyclerview.model.IRobinListItem;

public class ProgressItem implements IRobinListItem{
    private int progressIndex;
    private int dayCount;
    private String totalTime;

    public ProgressItem() {
    }

    public String getProgressIndex() {
        return String.valueOf(progressIndex);
    }

    public void setProgressIndex(int progressIndex) {
        this.progressIndex = progressIndex;
    }

    public String getDayCount() {
        return String.valueOf(dayCount);
    }

    public void setDayCount(int dayCount) {
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
        return "ProgressItem{" +
                "progressIndex=" + progressIndex +
                ", dayCount=" + dayCount +
                ", totalTime=" + totalTime +
                '}';
    }
}
