package cting.com.robin.support.toothcare.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.databinding.DailyRecordDetailBinding;
import cting.com.robin.support.toothcare.databinding.TimeSliceItemBinding;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;

public class DailyRecordDetailFragment extends RobinListFragment<TimeSlice,TimeSliceItemBinding> {

    private DailyRecord dailyRecord;

    public DailyRecordDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(DailyRecord.DAY_INDEX)) {
            dailyRecord = new DailyRecord(bundle);
        }
        Log.i(TAG, "onCreate: dailyRecord=" + dailyRecord);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DailyRecordDetailBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.daily_record_detail, container, false);
        binding.setDailyRecord(dailyRecord);
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateApapter();
        return binding.getRoot();
    }

    @Override
    public void onItemClick(TimeSlice item) {

    }

    @Override
    public boolean onItemLongClick(TimeSlice item) {
        return false;
    }

    @Override
    protected ArrayList<TimeSlice> newData() {
        return dailyRecord.getTimeSliceList();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.time_slice_item;
    }

    @Override
    public void bindItemData(TimeSlice item, TimeSliceItemBinding binding) {
        binding.setTimeSlice(item);
    }
}
