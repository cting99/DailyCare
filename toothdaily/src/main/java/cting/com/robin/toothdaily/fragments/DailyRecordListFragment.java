package cting.com.robin.toothdaily.fragments;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.toothdaily.databinding.DailyRecordLayoutBinding;
import cting.com.robin.toothdaily.datagenerator.SampleDatas;
import cting.com.robin.toothdaily.model.DailyRecord;

public class DailyRecordListFragment extends RobinListFragment<DailyRecord,DailyRecordLayoutBinding> {
    @Override
    public void onItemClick(DailyRecord item) {

    }

    @Override
    public boolean onItemLongClick(DailyRecord item) {
        return false;
    }

    @Override
    protected ArrayList<DailyRecord> newData() {
        return SampleDatas.getDailyRecordList();
    }

    @Override
    public int getItemLayoutId() {
        return 0;
    }

    @Override
    public void bindItemData(DailyRecord item, DailyRecordLayoutBinding binding) {

    }
}
