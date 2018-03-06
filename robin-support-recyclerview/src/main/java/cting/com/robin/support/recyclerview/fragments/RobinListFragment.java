package cting.com.robin.support.recyclerview.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.R;
import cting.com.robin.support.recyclerview.adapters.RobinListAdapter;
import cting.com.robin.support.recyclerview.model.IRobinListItem;

public abstract class RobinListFragment<I extends IRobinListItem, B extends ViewDataBinding>
        extends FragmentWithMenu
        implements RobinListAdapter.Callbacks<I, B> {
    public String TAG = "cting/list/";

    protected RecyclerView mRecyclerView;
    protected RobinListAdapter<I,B> mAdapter;
    protected ArrayList<I> mDataList;
    protected abstract ArrayList<I> newData();

    public RobinListFragment() {
        TAG += getClass().getSimpleName();
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
        if (mAdapter != null) {
            mDataList = dataList;
            mAdapter.setDataList(mDataList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(I item, int position) {

    }

    @Override
    public boolean onItemLongClick(I item, int position) {
        return false;
    }

    @Override
    public void bindItemData(I item, B binding, int position) {

    }

}
