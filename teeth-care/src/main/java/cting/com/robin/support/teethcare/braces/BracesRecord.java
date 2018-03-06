package cting.com.robin.support.teethcare.braces;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collections;

import cting.com.robin.support.teethcare.models.IRecord;

public class BracesRecord extends IRecord implements Comparable<BracesRecord> {
    @Expose
    private DateSlice dateSlice = new DateSlice();

    @Expose
    private String totalTime;

    public BracesRecord() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public DateSlice getDateSlice() {
        return dateSlice;
    }

    public String getDateSliceString() {
        return dateSlice.toString();
    }

    public void setDateSlice(DateSlice dateSlice) {
        this.dateSlice = dateSlice;
    }

    public String getTotal() {
        total = String.valueOf(getTotalNumeric());
        return total;
    }

    @Override
    public long getTotalNumeric() {
        return dateSlice.getDiffInNumeric();
    }


/*
    public void setTotal(int diff) {
        this.diff = diff;
    }
*/

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public int compareTo(@NonNull BracesRecord o) {
        return o.index - index;
    }

    @Override
    public String toString() {
        return "Braces{" + index + ":" + dateSlice + '}';
    }

    public void setFromDate(String date) {
        dateSlice.setFrom(date);
    }

    public void setToDate(String date) {
        dateSlice.setTo(date);
    }


    public static class Builder {
        private BracesRecord record = new BracesRecord();

        public BracesRecord build() {
            return record;
        }

        public Builder index(int index) {
            record.index = index;
            return this;
        }

        public Builder dateSlice(DateSlice dateSlice) {
            record.dateSlice = dateSlice;
            return this;
        }

        public Builder total(String total) {
            record.total = total;
            return this;
        }

        public static ArrayList<BracesRecord> sampleData() {
            ArrayList<BracesRecord> list = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                list.add(new Builder()
                        .index(i)
                        .dateSlice(DateSlice.Builder.sampleData(i))
                        .build());
            }
            Collections.sort(list);
            return list;
        }
    }
}
