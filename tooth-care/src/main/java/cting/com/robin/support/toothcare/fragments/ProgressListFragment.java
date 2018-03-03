package cting.com.robin.support.toothcare.fragments;


import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.activities.ProgressDetailActivity;
import cting.com.robin.support.toothcare.databinding.ProgressListItemBinding;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;
import cting.com.robin.support.toothcare.models.ProgressRecord;

public class ProgressListFragment extends RobinListFragment<ProgressRecord, ProgressListItemBinding> {
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

}
