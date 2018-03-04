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
import cting.com.robin.support.toothcare.views.StateButton;


public class TodayFragment extends RobinListFragment<TimeSlice, TimeSliceAddLayoutBinding>
        implements StateButton.Callback {

    private DailyRecord mTodayRecord;
    private TimeSlice mCurrentTimeSlice;

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
        mCurrentTimeSlice = mTodayRecord.getLastTimeSliceList();

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
        validationTimeSlice();
        SampleData.getInstance().save(getContext());
        mAdapter.notifyDataSetChanged();
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
        validationTimeSlice();
        return mTodayRecord.getTimeSliceList();
    }

    private void validationTimeSlice() {
        int count = mTodayRecord.getTimeSliceList().size();
        TimeSlice slice;
        for (int i = 0; i < count; i++) {
            slice = mTodayRecord.getTimeSliceList().get(i);
            if (slice.isEmpty()) {
                mTodayRecord.getTimeSliceList().remove(slice);
                count -= 1;
            }
        }
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.time_slice_add_layout;
    }

    @Override
    public void bindItemData(TimeSlice item, TimeSliceAddLayoutBinding binding, int position) {
        super.bindItemData(item, binding, position);
        binding.addNewTimeSliceBtn.setCallback(this);
        binding.addNewTimeSliceBtn.setTimeSlice(item);
        binding.addNewTimeSliceBtn.setPosition(position, mTodayRecord.getTimeSliceList().size());
    }

    @Override
    public void onAdd() {
        mCurrentTimeSlice = TimeSlice.newRecord();
        mDataList.add(mCurrentTimeSlice);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete(int position) {
        mTodayRecord.getTimeSliceList().remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDone() {
        mCurrentTimeSlice.setEndTime(TimeFormatHelper.formatNow());
        mAdapter.notifyDataSetChanged();
    }
}
