package cting.com.robin.toothdaily.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.ViewDataBinding;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.toothdaily.R;
import cting.com.robin.toothdaily.activities.DailyRecordActivity;
import cting.com.robin.toothdaily.databinding.ProgressDetailListItemBinding;
import cting.com.robin.toothdaily.datagenerator.SampleDatas;
import cting.com.robin.toothdaily.model.ProgressDetailItem;

public class ProgressDetailFragment extends RobinListFragment<ProgressDetailItem,ProgressDetailListItemBinding> {

    public ProgressDetailFragment() {
    }

    @Override
    protected ArrayList<ProgressDetailItem> newData() {
        return SampleDatas.getProgressDetailData();
    }

    @Override
    public void onItemClick(ProgressDetailItem item) {
        Intent intent = new Intent(getContext(), DailyRecordActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(ProgressDetailItem item) {
        return false;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.progress_detail_list_item;
    }

    @Override
    public void bindItemData(ProgressDetailItem item, ProgressDetailListItemBinding binding) {
        binding.setDetail(item);
    }
}
