package cting.com.robin.support.toothcare.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.support.toothcare.BR;
import cting.com.robin.support.toothcare.utils.TimeFormatHelper;

public class TimeSlice extends BaseObservable implements IRobinListItem,Parcelable {

    @Expose
    private String startTime;

    @Expose
    private String endTime;

    public TimeSlice() {
    }

    protected TimeSlice(Parcel in) {
        startTime = in.readString();
        endTime = in.readString();
    }

    public static final Creator<TimeSlice> CREATOR = new Creator<TimeSlice>() {
        @Override
        public TimeSlice createFromParcel(Parcel in) {
            return new TimeSlice(in);
        }

        @Override
        public TimeSlice[] newArray(int size) {
            return new TimeSlice[size];
        }
    };

    @Bindable
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
        notifyPropertyChanged(BR.duration);
    }

    @Bindable
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
        notifyPropertyChanged(BR.duration);
    }

    @Bindable
    public String getDuration() {
        return TimeFormatHelper.formatDuration(startTime, endTime);
    }

    public long getDurationMillions() {
        return TimeFormatHelper.getDuration(startTime, endTime);
    }

    @Override
    public String toString() {
        return "TimeSlice{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public static TimeSlice newRecord() {
        TimeSlice timeSlice = new TimeSlice();
        String startTime = TimeFormatHelper.formatNow();
        timeSlice.setStartTime(startTime);
        return timeSlice;
    }
    public boolean isEmpty() {
        return TextUtils.isEmpty(startTime) && TextUtils.isEmpty(endTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startTime);
        dest.writeString(endTime);
    }
}
