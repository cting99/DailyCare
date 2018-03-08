package cting.com.robin.support.teethcare.models;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import cting.com.robin.support.recyclerview.model.IRobinListItem;

public abstract class IRecord extends BaseObservable
        implements IRobinListItem,Comparable<IRecord> {

    @Expose
    protected int index;
    @Expose
    protected String date;
    @Expose
    protected String totalTime;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public int compareTo(@NonNull IRecord o) {
        return o.index - index; // DES-ordered
    }

}
