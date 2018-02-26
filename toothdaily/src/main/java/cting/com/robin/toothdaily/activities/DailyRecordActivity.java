package cting.com.robin.toothdaily.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import cting.com.robin.toothdaily.R;
import cting.com.robin.toothdaily.databinding.DailyRecordLayoutBinding;
import cting.com.robin.toothdaily.databinding.TimePeriodItemBinding;
import cting.com.robin.toothdaily.model.DailyRecord;
import cting.com.robin.toothdaily.model.TimePeriod;
import cting.com.robin.toothdaily.utils.FormatHelper;
import cting.com.robin.toothdaily.utils.RecordDatas;

public class DailyRecordActivity extends AppCompatActivity implements View.OnClickListener {

    DailyRecordLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.daily_record_layout);

        DailyRecord record = RecordDatas.getData().get(0);
        binding.setRecord(record);
        binding.setClickListener(this);

        ArrayList<TimePeriod> timePeriodItems = record.getPeriodList();
        for (TimePeriod item : timePeriodItems) {
            addPeriod(item);
        }
    }

    public View addPeriod(TimePeriod period) {
        if (period == null) {
            period = new TimePeriod();
            long now = System.currentTimeMillis();
            period.setStartTime(FormatHelper.formatTime(now));
        }
        TimePeriodItemBinding periodBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.time_period_item, binding.timePeriodContainer, true);
        periodBinding.setPeriod(period);
        return periodBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        View newItem = addPeriod(null);
        newItem.requestFocus();
    }
}
