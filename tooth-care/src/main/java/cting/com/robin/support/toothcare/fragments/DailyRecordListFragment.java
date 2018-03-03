package cting.com.robin.support.toothcare.fragments;


import android.os.Bundle;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.activities.DailyRecordDetailActivity;
import cting.com.robin.support.toothcare.databinding.DailyRecordListItemBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;
import cting.com.robin.support.toothcare.models.DailyRecord;

public class DailyRecordListFragment extends RobinListFragment<DailyRecord, DailyRecordListItemBinding> {

    public DailyRecordListFragment() {
    }

    @Override
    public void onItemClick(DailyRecord item) {
        Bundle b = item.toBundle();
        DailyRecordDetailActivity.launchWithBundle(getContext(),b);
    }

    @Override
    public boolean onItemLongClick(DailyRecord item) {
        return false;
    }

    @Override
    protected ArrayList<DailyRecord> newData() {
        return SampleDatas.getDailyRecords(getContext());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.daily_record_list_item;
    }

    @Override
    public void bindItemData(DailyRecord item, DailyRecordListItemBinding binding) {
        binding.setDailyRecord(item);
    }
}
