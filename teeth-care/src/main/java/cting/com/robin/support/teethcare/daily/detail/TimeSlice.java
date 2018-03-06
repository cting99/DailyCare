package cting.com.robin.support.teethcare.daily.detail;

import android.databinding.Bindable;
import android.text.TextUtils;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.models.IEntryState;
import cting.com.robin.support.teethcare.models.SliceItem;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class TimeSlice extends SliceItem implements IEntryState {
    public static final String pattern = TimeFormatHelper.TIME_FORMAT;

    @Override
    @Bindable
    public String getDiff() {
        return TimeFormatHelper.formatDuration(from, to);
    }

    @Override
    @Bindable
    public long getDiffInNumeric() {
        return TimeFormatHelper.getDuration(from, to);
    }


    // follows for StateButton in DailyDetailFragment(edit mode)
    public void close() {
        to = TimeFormatHelper.formatNow();
    }

    @Override
    public boolean notFinished() {
        return TextUtils.isEmpty(to) && !TextUtils.isEmpty(from);
    }

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(from + to);
    }
    // above for StateButton in DailyDetailFragment(edit mode)

    public static class Builder {
        private TimeSlice timeSlice = new TimeSlice();

        public TimeSlice build() {
            return timeSlice;
        }

        public Builder from(String from) {
            timeSlice.from = from;
            return this;
        }

        public Builder to(String to) {
            timeSlice.to = to;
            return this;
        }

        public Builder newNow() {
            timeSlice.from=TimeFormatHelper.formatNow();
            timeSlice.to ="";
            return this;
        }

        public static ArrayList<TimeSlice> sampleData() {
            ArrayList<TimeSlice> list = new ArrayList<>();
            list.add(new Builder().from("00:00").to("08:30").build());
            list.add(new Builder().from("09:20").to("11:45").build());
            list.add(new Builder().from("12:30").to("18:30").build());
            list.add(new Builder().from("19:05").to("24:00").build());
            return list;
        }
    }
}
