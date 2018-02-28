package cting.com.robin.toothdaily.fragments;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.toothdaily.R;
import cting.com.robin.toothdaily.activities.ProgressDetailActivity;
import cting.com.robin.toothdaily.databinding.ProgressListItemBinding;
import cting.com.robin.toothdaily.datagenerator.SampleDatas;
import cting.com.robin.toothdaily.model.DailyRecord;
import cting.com.robin.toothdaily.model.ProgressItem;
import cting.com.robin.toothdaily.utils.FormatHelper;

public class ProgressListFragment extends RobinListFragment<ProgressItem,ProgressListItemBinding> {
    public static final String TAG = "cting/tooth/pglist";

    public ProgressListFragment() {
    }

    @Override
    public void onItemClick(ProgressItem item) {
        Intent intent = new Intent(getContext(), ProgressDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(ProgressItem item) {
        return false;
    }

    @Override
    protected ArrayList<ProgressItem> newData() {
        return SampleDatas.getProgressItems();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.progress_list_item;
    }

    @Override
    public void bindItemData(ProgressItem item, ProgressListItemBinding binding) {
        binding.setItem(item);
    }

}
