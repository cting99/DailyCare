package cting.com.robin.support.toothcare.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.activities.ProgressDetailActivity;
import cting.com.robin.support.toothcare.databinding.ProgressListItemBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.models.TimeSlice;
import cting.com.robin.support.toothcare.utils.GsonHelper;

public class ProgressListFragment extends RobinListFragment<ProgressRecord, ProgressListItemBinding> {

    public static final String EXPORT_FILE_NAME = FileHelper.DIR + "tooth_daily.json";

    public ProgressListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onItemClick(ProgressRecord item) {
        ProgressDetailActivity.launchWithBundle(getContext(), item.toBundle());
    }

    @Override
    protected ArrayList<ProgressRecord> newData() {
        return SampleDatas.getProgressRecords(getContext());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.progress_list_item;
    }

    @Override
    protected void exportData(ArrayList<ProgressRecord> dataList) {
        super.exportData(mDataList);

        boolean ret = GsonHelper.writeToGson(EXPORT_FILE_NAME, dataList);
        Toast.makeText(getContext(), "write to " + EXPORT_FILE_NAME + ": " + (ret ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void addNewItem() {
        int progressIndex = SampleDatas.getProgressIndex(getContext());
        ProgressRecord record = ProgressRecord.newRecord(progressIndex + 1);
        ProgressDetailActivity.launchWithBundle(getContext(), record.toBundle());
    }
}
