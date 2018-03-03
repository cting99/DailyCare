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
import cting.com.robin.support.toothcare.activities.DailyRecordDetailActivity;
import cting.com.robin.support.toothcare.databinding.DailyRecordListItemBinding;
import cting.com.robin.support.toothcare.databinding.ProgressDetailBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;

public class ProgressDetailFragment extends RobinListFragment<DailyRecord, DailyRecordListItemBinding> {
    private ProgressRecord progressRecord;

    public ProgressDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ProgressRecord.PROGRESS_INDEX)) {
            progressRecord = new ProgressRecord(bundle);
        }
        Log.i(TAG, "onCreate: progressRecord=" + progressRecord);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProgressDetailBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.progress_detail, container, false);
        binding.setProgressRecord(progressRecord);
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateApapter();
        return binding.getRoot();
    }

    @Override
    public void onItemClick(DailyRecord item) {
        DailyRecordDetailActivity.launchWithBundle(getContext(),item.toBundle());
    }

    @Override
    public boolean onItemLongClick(DailyRecord item) {
        return false;
    }

    @Override
    protected ArrayList<DailyRecord> newData() {
        return progressRecord.getDailyRecords();
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