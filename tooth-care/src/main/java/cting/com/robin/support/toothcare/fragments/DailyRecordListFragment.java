package cting.com.robin.support.toothcare.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.activities.DailyRecordDetailActivity;
import cting.com.robin.support.toothcare.databinding.DailyRecordListItemBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleData;
import cting.com.robin.support.toothcare.models.DailyRecord;

public class DailyRecordListFragment extends RobinListFragment<DailyRecord, DailyRecordListItemBinding> {

    public DailyRecordListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        addAddMenu(menu);
    }

    @Override
    protected void selectMenuAdd() {
//        int dayIndex = SampleData.getDayIndex(getContext());
//        DailyRecord record = DailyRecord.newRecord(dayIndex + 1);
//        DailyRecordDetailActivity.launchWithBundle(getContext(), record.toBundle());
    }

    @Override
    public void onItemClick(DailyRecord item) {
        Bundle b = item.toBundle();
        DailyRecordDetailActivity.launchWithBundle(getContext(),b);
    }

    @Override
    protected ArrayList<DailyRecord> newData() {
        return SampleData.getInstance().getDailyRecords();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.daily_record_list_item;
    }

}
