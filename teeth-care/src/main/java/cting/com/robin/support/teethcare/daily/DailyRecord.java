package cting.com.robin.support.teethcare.daily;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;

import cting.com.robin.support.teethcare.BR;
import cting.com.robin.support.teethcare.models.IRecord;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class DailyRecord extends IRecord{
    @Expose
    private String note;
    @Expose
    protected String line;

    public DailyRecord() {
    }


    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "Day[" + index + "] " + date + ", " + totalTime + ", {" + line + "}, " + note;
    }
}
