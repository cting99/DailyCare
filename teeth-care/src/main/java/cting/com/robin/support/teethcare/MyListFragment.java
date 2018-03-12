package cting.com.robin.support.teethcare;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.teethcare.models.IRecord;
import cting.com.robin.support.teethcare.repository.MessageEvent;
import cting.com.robin.support.teethcare.repository.MyRepositoryService;

public abstract class MyListFragment<I extends IRecord, B extends ViewDataBinding> extends RobinListFragment<I,B> {
    @Override
    protected ArrayList<I> newData() {
        return null;
    }

    protected abstract String getDataTag();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w(TAG, "onActivityCreated: start load action:" + getDataTag());
        if (!TextUtils.isEmpty(getDataTag())) {
            MyRepositoryService.startActionLoad(getContext(),getDataTag());
            EventBus.getDefault().register(this);
        }
//        setDataList(mDataList);//notify data change
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
//        setDataList(mDataList);//refresh list
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadFinished(MessageEvent<I> msg) {
        Log.i(TAG, "onLoadFinished: " + msg.getMessage() + " " + msg.isFinish());
        if (msg.isFinish() && TextUtils.equals(getDataTag(), msg.getMessage())) {
            mDataList = msg.getList();
            setDataList(mDataList);
        }
    }

}
