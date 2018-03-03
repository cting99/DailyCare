package cting.com.robin.support.toothcare.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.support.toothcare.utils.TimeFormatHelper;

public class TimeSlice implements IRobinListItem,Parcelable{

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
