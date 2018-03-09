package cting.com.robin.support.teethcare.braces;

import com.google.gson.annotations.Expose;
import cting.com.robin.support.teethcare.models.IRecord;

public class BracesRecord extends IRecord{
    @Expose
    private int dayCount;

    public BracesRecord() {
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    @Override
    public String toString() {
        return "Braces[" + index + "] " + date + ", "+dayCount+" days, " + totalTime;
    }

}
