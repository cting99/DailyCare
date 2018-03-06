package cting.com.robin.support.teethcare.models;

import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;

import cting.com.robin.support.recyclerview.model.IRobinListItem;

public abstract class IRecord extends BaseObservable
        implements IRobinListItem {

    @Expose
    protected int index;
    @Expose
    protected String total;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public abstract String getTotal();

    public abstract long getTotalNumeric();

}
