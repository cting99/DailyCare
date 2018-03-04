package cting.com.robin.support.toothcare.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.databinding.TimeSliceAddLayoutBinding;
import cting.com.robin.support.toothcare.databinding.TodayFragmentBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleData;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;
import cting.com.robin.support.toothcare.utils.TimeFormatHelper;


public class TodayFragment extends RobinListFragment<TimeSlice, TimeSliceAddLayoutBinding> implements View.OnClickListener {

    private DailyRecord mTodayRecord;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DailyRecord lastRecord = SampleData.getInstance().getLastDailyRecord();
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
            SampleData.getInstance().addDailyRecord(mTodayRecord);
        }

        // set title
        int progress = SampleData.getInstance().getProgressIndex();
        ProgressRecord lastProgress = SampleData.getInstance().getLastProgressRecord();
        getActivity().setTitle(getString(
                R.string.fragment_title_today,
                progress,
                lastProgress.getDayCount()));

        Log.i(TAG, "onCreate: today is Day[" + mTodayRecord.getDayIndex() + "]: " + mTodayRecord.getDate());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        addSaveMenu(menu);
    }

    @Override
    protected void selectMenuSave() {
        SampleData.getInstance().save(getContext());
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
    public void onClick(View v) {
//        v.setVisibility(View.GONE);
        if (v.getId() == R.id.add_new_time_slice_btn) {
            TimeSlice timeSlice = TimeSlice.newRecord();
            mDataList.add(timeSlice);
            setDataList(mDataList);
        }
    }
}
