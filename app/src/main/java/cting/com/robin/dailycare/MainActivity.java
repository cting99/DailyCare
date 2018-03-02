package cting.com.robin.dailycare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.commom.activities.TextActivity;
import cting.com.robin.support.commom.utils.FileHelper;

public class MainActivity extends TextActivity {

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        MyIntentService.startActionFoo(this, FileHelper.DIR + "invisalign_raw.txt");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataLoaded(MessageEvent msg) {
        Log.i(TAG, "onDataLoaded: ");
        setMessage(msg.getMsg());
    }
}
