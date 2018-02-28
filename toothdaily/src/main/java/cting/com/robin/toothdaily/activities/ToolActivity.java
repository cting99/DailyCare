package cting.com.robin.toothdaily.activities;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import cting.com.robin.support.commom.activities.TextActivity;
import cting.com.robin.toothdaily.datagenerator.SampleDatas;
import cting.com.robin.toothdaily.model.DailyRecord;

public class ToolActivity extends TextActivity {
    public static final String TAG = "cting/tooth/toolAct";

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();

        Map<Integer, ArrayList<DailyRecord>> map = SampleDatas.getFullRecordsMap();
        if (map != null && map.size() > 0) {
            Object[] keySetArray = map.keySet().toArray();
            Arrays.sort(keySetArray);
            appendMessage("category:" + Arrays.toString(keySetArray) + "\n\n");

            for (Object category : keySetArray) {
                appendMessage(category + ":\n");
                Log.i(TAG, category + ":\n");
                for (DailyRecord record : map.get(category)) {
                    appendMessage(record + "\n");
                    Log.i(TAG, record + "\n");
                }
                appendMessage("\n\n");
            }
        }


    }
}
