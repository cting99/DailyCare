package cting.com.robin.support.teethcare.daily;

import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cting.com.robin.support.teethcare.BR;
import cting.com.robin.support.teethcare.daily.detail.TimeSlice;
import cting.com.robin.support.teethcare.models.IRecord;
import cting.com.robin.support.teethcare.models.SliceItem;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class DailyRecord extends IRecord implements Comparable<DailyRecord> {
    @Expose
    private String date;
    @Expose
    private String note;
    @Expose
    private List<TimeSlice> timeSliceList = new ArrayList<>();

    public DailyRecord() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);

    }

    public List<TimeSlice> getSliceList() {
        return timeSliceList;
    }
/*
    public void setSliceList(List<TimeSlice> timeSliceList) {
        this.timeSliceList = timeSliceList;
    }
*/

    private void addTimeSlice(TimeSlice slice) {
        timeSliceList.add(slice);
    }

    public String getTotal() {
        total = TimeFormatHelper.formatDuration(getTotalNumeric());
        return total;
    }
/*
    public void setTotal(String diff) {
        this.diff = diff;
    }
*/

    @Override
    public long getTotalNumeric() {
        long totalMillions = 0;
        for (SliceItem item : timeSliceList) {
            totalMillions += item.getDiffInNumeric();
        }
        return totalMillions;
    }

    public static void sort() {

    }

    @Override
    public int compareTo(@NonNull DailyRecord o) {
        return o.index - index;
    }

    public static class Builder {
        private DailyRecord dailyRecord = new DailyRecord();

        public DailyRecord build() {
            return dailyRecord;
        }

        public Builder index(int index) {
            dailyRecord.index = index;
            return this;
        }

        public Builder date(String date) {
            dailyRecord.date = date;
            return this;
        }

        public Builder timeSliceList(ArrayList<TimeSlice> timeSliceList) {
            dailyRecord.timeSliceList.addAll(timeSliceList);
            return this;
        }

        public Builder timeSlice(TimeSlice slice) {
            dailyRecord.addTimeSlice(slice);
            return this;
        }

        public Builder note(String note) {
            dailyRecord.note = note;
            return this;
        }

        public static ArrayList<DailyRecord> sampleData() {
            ArrayList<DailyRecord> list = new ArrayList<>();
            String date_ = "2018/03/0";
            for (int i = 1; i < 30; i++) {
                if (i == 10) {
                    date_ = "2018/03/";
                }
                list.add(new Builder()
                        .index(i)
                        .date(date_ + i)
                        .timeSliceList(TimeSlice.Builder.sampleData())
                        .build());
            }
            Collections.sort(list);
            return list;
        }
    }
/*
    public static class DailyRecordBundle {
        public static final String DAY_INDEX = "DAY_INDEX";
        public static final String DATE = "DATE";
        public static final String NOTES = "NOTES";
        public static final String TIME_SLICE_LIST = "TIME_SLICE_LIST";
        public static final String TOTAL_TIME = "TOTAL_TIME";

        public static DailyRecord fromBundle(Bundle bundle) {
            Builder builder = new Builder();
            if (bundle != null) {
                builder.index(bundle.getInt(DAY_INDEX))
                        .date(bundle.getString(DATE))
                        .note(bundle.getString(NOTES))
                        .timeSliceList((ArrayList<TimeSlice>) );
            }
            return builder.build();
        }
    }*/


}
