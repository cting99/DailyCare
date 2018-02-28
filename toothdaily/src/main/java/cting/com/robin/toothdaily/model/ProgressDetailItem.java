package cting.com.robin.toothdaily.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.toothdaily.BR;

public class ProgressDetailItem extends BaseObservable implements IRobinListItem {

    private int dayIndex;
    private String date;
    private String totalTime;

    public ProgressDetailItem() {
    }

    @Bindable
    public String getDayIndex() {
        return String.valueOf(dayIndex);
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
        notifyPropertyChanged(BR.dayIndex);
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

    @Override
    public String toString() {
        return "ProgressDetailItem{" +
                "dayIndex=" + dayIndex +
                ", date='" + date + '\'' +
                ", totalTime='" + totalTime + '\'' +
                '}';
    }
}
