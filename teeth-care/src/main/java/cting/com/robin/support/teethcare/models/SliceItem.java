package cting.com.robin.support.teethcare.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;

import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.support.teethcare.BR;

public abstract class SliceItem extends BaseObservable
        implements IRobinListItem{
    protected String from;
    protected String to;
    protected String diff;

    public SliceItem() {
    }

    @Bindable
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
        notifyPropertyChanged(BR.from);
        notifyPropertyChanged(BR.diff);
        notifyPropertyChanged(BR.diffInNumeric);
    }

    @Bindable
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
        notifyPropertyChanged(BR.to);
        notifyPropertyChanged(BR.diff);
        notifyPropertyChanged(BR.diffInNumeric);
    }

    @Bindable
    public abstract String getDiff();

    @Bindable
    public abstract long getDiffInNumeric();


    @Override
    public String toString() {
        return "SliceItem{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", diff='" + diff + '\'' +
                '}';
    }
}
