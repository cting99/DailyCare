package cting.com.robin.support.teethcare.braces;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.R;
import cting.com.robin.support.teethcare.databinding.BracesRecordListItemBinding;
import cting.com.robin.support.teethcare.models.MyListFragment;
import cting.com.robin.support.teethcare.repository.MySource;

public class BracesListFragment extends MyListFragment<BracesRecord,BracesRecordListItemBinding> {
    @Override
    protected ArrayList<BracesRecord> newData() {
        return null;
    }

    @Override
    protected ArrayList<BracesRecord> loadDataFromService() {
        return MySource.getInstance().getGenerator().getBracesRecords(getContext());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.braces_record_list_item;
    }
}
