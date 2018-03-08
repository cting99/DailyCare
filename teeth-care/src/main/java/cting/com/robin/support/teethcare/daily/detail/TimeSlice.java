package cting.com.robin.support.teethcare.daily.detail;

import android.databinding.Bindable;
import android.text.TextUtils;

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

}
