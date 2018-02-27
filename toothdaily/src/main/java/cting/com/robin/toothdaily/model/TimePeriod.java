package cting.com.robin.toothdaily.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import cting.com.robin.toothdaily.BR;
import cting.com.robin.toothdaily.utils.FormatHelper;

public class TimePeriod extends BaseObservable {

    public static final String TAG = "cting/tooth/TimePeriod";
    private String startTime;
    private String endTime;

    public TimePeriod() {
    }

    @Bindable
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
//        Log.i(TAG, "setStartTime: " + startTime);
        notifyPropertyChanged(BR.startTime);
        notifyPropertyChanged(BR.timeDuration);
        notifyPropertyChanged(BR.totalCost);
    }

    @Bindable
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
        notifyPropertyChanged(BR.timeDuration);
        notifyPropertyChanged(BR.totalCost);
    }

    @Bindable
    public String getTimeDuration() {
        return FormatHelper.formatDuration(startTime, endTime);
    }



    @Override
    public String toString() {
        return "{" + startTime +
                "," + endTime +
                '}';
    }
}
