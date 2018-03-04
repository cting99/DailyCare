package cting.com.robin.support.toothcare.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.activities.ProgressDetailActivity;
import cting.com.robin.support.toothcare.databinding.ProgressListItemBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.utils.GsonHelper;

public class ProgressListFragment extends RobinListFragment<ProgressRecord, ProgressListItemBinding> {

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
        return SampleDatas.getInstance(getContext()).getProgressRecords();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.progress_list_item;
    }

    @Override
    protected void exportData(ArrayList<ProgressRecord> dataList) {
        GsonHelper.writeToGson(getContext(), dataList);

    }

    @Override
    protected void addNewItem() {
//        int progressIndex = SampleDatas.getInstance().getProgressIndex(getContext());
//        ProgressRecord record = ProgressRecord.newRecord(progressIndex + 1);
//        ProgressDetailActivity.launchWithBundle(getContext(), record.toBundle());
    }
}
