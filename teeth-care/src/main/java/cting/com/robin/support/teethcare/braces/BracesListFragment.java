package cting.com.robin.support.teethcare.braces;

import cting.com.robin.support.teethcare.R;
import cting.com.robin.support.teethcare.databinding.BracesListItemBinding;
import cting.com.robin.support.teethcare.MyListFragment;

import static cting.com.robin.support.teethcare.repository.MyRepositoryService.DATA_TAG_BRACES_LIST;

public class BracesListFragment extends MyListFragment<BracesRecord,BracesListItemBinding> {

    @Override
    protected String getDataTag() {
        return DATA_TAG_BRACES_LIST;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.braces_list_item;
    }
}
