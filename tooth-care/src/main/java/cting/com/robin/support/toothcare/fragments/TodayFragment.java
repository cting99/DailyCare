package cting.com.robin.support.toothcare.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.databinding.TimeSliceAddLayoutBinding;
import cting.com.robin.support.toothcare.databinding.TodayFragmentBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;
import cting.com.robin.support.toothcare.utils.TimeFormatHelper;


public class TodayFragment extends RobinListFragment<TimeSlice, TimeSliceAddLayoutBinding> implements View.OnClickListener {

    private DailyRecord mTodayRecord;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DailyRecord lastRecord = SampleDatas.getInstance(getContext()).getLastDailyRecord();
        String todayData = TimeFormatHelper.formatToday();
        Log.i(TAG, "onCreate: last record day is " + lastRecord.getDate() + ", today is " + todayData);
        if (lastRecord != null) {
            if (todayData.equals(lastRecord.getDate())) {
                mTodayRecord = lastRecord;
            } else {
                mTodayRecord = DailyRecord.newToday(lastRecord.getDayIndex() + 1);
            }
        } else {
            mTodayRecord = DailyRecord.newFirstDay();
            Log.i(TAG, "onCreate: its your first day!");
        }

        if (mTodayRecord != lastRecord) {
            SampleDatas.getInstance(getContext()).addDailyRecord(mTodayRecord);
        }

        Log.i(TAG, "onCreate: today is Day[" + mTodayRecord.getDayIndex() + "]: " + mTodayRecord.getDate());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TodayFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.today_fragment, container, false);
        binding.setDailyRecord(mTodayRecord);
        setupRecyclerView(binding.recyclerView);
        setDefaultAdapter(this);
        setDataList(newData());
        mRecyclerView.requestFocus();
//        binding.dateText.requestFocus();
        return binding.getRoot();
    }

    @Override
    protected void setupRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected ArrayList<TimeSlice> newData() {
        return mTodayRecord.getTimeSliceList();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.time_slice_add_layout;
    }

    @Override
    public void bindItemData(TimeSlice item, TimeSliceAddLayoutBinding binding) {
        super.bindItemData(item, binding);
        binding.setClickListener(this);
    }

    @Override
    protected void addNewItem() {
        TimeSlice timeSlice = TimeSlice.newRecord();
        mDataList.add(timeSlice);
        setDataList(mDataList);
    }

    @Override
    public void onClick(View v) {
        v.setVisibility(View.GONE);
        addNewItem();
    }

    @Override
    protected void saveData() {
        SampleDatas.getInstance(getContext()).save(getContext());
    }
}
