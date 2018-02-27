package cting.com.robin.support.recyclerview.sample;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cting.com.robin.support.recyclerview.BR;
import cting.com.robin.support.recyclerview.model.IRobinListItem;

public class SimpleListItem extends BaseObservable implements IRobinListItem {

    private String title;

    public SimpleListItem(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Override
    public String toString() {
        return "SimpleListItem{" +
                "title='" + title + '\'' +
                '}';
    }
}
