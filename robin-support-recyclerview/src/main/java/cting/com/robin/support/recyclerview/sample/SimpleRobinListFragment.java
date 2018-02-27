package cting.com.robin.support.recyclerview.sample;

import android.widget.Toast;
import java.util.ArrayList;
import cting.com.robin.support.recyclerview.R;
import cting.com.robin.support.recyclerview.databinding.SimpleRobinListItemBinding;
import cting.com.robin.support.recyclerview.fragments.RobinListFragment;

public class SimpleRobinListFragment extends RobinListFragment<SimpleListItem,SimpleRobinListItemBinding> {

    @Override
    protected ArrayList<SimpleListItem> newData() {
        ArrayList<SimpleListItem> items = new ArrayList<>();
        items.add(new SimpleListItem("Andy"));
        items.add(new SimpleListItem("Bruce"));
        items.add(new SimpleListItem("Lily"));
        items.add(new SimpleListItem("Lucy"));
        items.add(new SimpleListItem("Anna"));
        return items;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.simple_robin_list_item;
    }

    @Override
    public void bindItemData(SimpleListItem item, SimpleRobinListItemBinding binding) {
        binding.setItem(item);

    }


    @Override
    public void onItemClick(SimpleListItem item) {
        Toast.makeText(getContext(), "click " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(SimpleListItem item) {
        return false;
    }
}
