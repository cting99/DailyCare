package cting.com.robin.support.teethcare.models;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.support.teethcare.repository.MessageEvent;
import cting.com.robin.support.teethcare.repository.MyRepositoryService;

public abstract class MyListFragment<I extends IRobinListItem, B extends ViewDataBinding> extends RobinListFragment<I,B> {
    @Override
    protected ArrayList<I> newData() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyRepositoryService.startActionLoad(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataList(mDataList);//notify data change
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        setDataList(mDataList);//refresh list
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadFinished(MessageEvent msg) {
        Log.i(TAG, "onLoadFinished: " + msg.getMessage() + " " + msg.isFinish());
        if (msg.isFinish() && MyRepositoryService.MESSAGE_LOAD.equals(msg.getMessage())) {
            mDataList = loadDataFromService();
            setDataList(mDataList);
        }
    }

    protected abstract ArrayList<I> loadDataFromService();
}
