package cting.com.robin.support.recyclerview.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.R;
import cting.com.robin.support.recyclerview.adapters.RobinListAdapter;
import cting.com.robin.support.recyclerview.model.IRobinListItem;

public abstract class RobinListFragment<I extends IRobinListItem, B extends ViewDataBinding>
        extends Fragment
        implements RobinListAdapter.Callbacks<I, B> {

    public static final String TAG = "cting/list/fragment";

    protected RecyclerView mRecyclerView;
    protected RobinListAdapter<I,B> mAdapter;
    protected ArrayList<I> mDataList;
    protected abstract ArrayList<I> newData();

    public RobinListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robin_list, container, false);
        setupRecyclerView((RecyclerView) view.findViewById(R.id.recycler_view));
        setDefaultAdapter(this);
        setDataList(newData());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_robin_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_export) {
            exportData(mDataList);
        } else if (i == R.id.action_import) {
            importData();
//            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    protected void exportData(ArrayList<I>dataList) {
    }

    protected void importData() {
    }

    protected void setupRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    protected void setupAdapter(RobinListAdapter adapter, RobinListAdapter.Callbacks callback) {
        mAdapter = adapter;
        mAdapter.setCallbacks(callback);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void setDefaultAdapter(RobinListAdapter.Callbacks<I,B> callback) {
        setupAdapter(new RobinListAdapter(getContext()), callback);
    }

    protected void setDataList(ArrayList<I> dataList) {
        mDataList = dataList;
        mAdapter.setDataList(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(I item) {

    }

    @Override
    public boolean onItemLongClick(I item) {
        return false;
    }

    @Override
    public void bindItemData(I item, B binding) {

    }

}
