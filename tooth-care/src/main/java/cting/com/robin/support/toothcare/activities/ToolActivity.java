package cting.com.robin.support.toothcare.activities;

import cting.com.robin.support.commom.activities.TextActivity;
import cting.com.robin.support.toothcare.datagenerator.SampleDatas;

public class ToolActivity extends TextActivity {
    public static final String TAG = "cting/tooth/tool";

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();

//        appendMessage("progress list:\n" + SampleDatas.getInstance().getProgressRecords(this) + "\n\n");
//        appendMessage("daily record list:\n" + SampleDatas.getInstance().getDailyRecords(this) + "\n\n");
    }
}
