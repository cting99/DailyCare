package cting.com.robin.support.teethcare.braces;

import cting.com.robin.support.teethcare.R;
import cting.com.robin.support.teethcare.databinding.BracesRecordListItemBinding;
import cting.com.robin.support.teethcare.MyListFragment;

import static cting.com.robin.support.teethcare.repository.MyRepositoryService.DATA_TAG_BRACES_LIST;

public class BracesListFragment extends MyListFragment<BracesRecord,BracesRecordListItemBinding> {

    @Override
    protected String getDataTag() {
        return DATA_TAG_BRACES_LIST;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.braces_record_list_item;
    }
}
