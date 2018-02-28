package cting.com.robin.support.recyclerview.fragments;

import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
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
        extends Fragment
        implements RobinListAdapter.Callbacks<I, B> {

    public static final String TAG = "cting/list/fragment";

    protected RecyclerView mRecyclerView;

    protected ArrayList<I> mDataList;
    protected RobinListAdapter<I, B> mAdapter;

    protected abstract ArrayList<I> newData();

    public RobinListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robin_list, container, false);
        setupRecyclerView((RecyclerView) view.findViewById(R.id.recycler_view));
        return view;
    }

    protected void setupRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        updateApapter();
    }

    protected void updateApapter() {
        if (mAdapter == null) {
            mAdapter = newAdapter();
            mAdapter.setCallbacks(this);
            mDataList = newData();
            mRecyclerView.setAdapter(mAdapter);
        }
        if (mDataList != null) {
            mAdapter.setDataList(mDataList);
        }
    }

    protected RobinListAdapter newAdapter() {
        return new RobinListAdapter(getContext());
    }
}
