package cting.com.robin.support.toothcare.activities;

import android.util.Log;

import java.util.List;

import cting.com.robin.support.commom.activities.TextActivity;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;

public class ToolActivity extends TextActivity {
    public static final String TAG = "cting/tooth/tool";

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();

        appendMessage("progress list:\n" + SampleDatas.getProgressRecords(this) + "\n\n");
        appendMessage("daily record list:\n" + SampleDatas.getDailyRecords(this) + "\n\n");
    }
}
