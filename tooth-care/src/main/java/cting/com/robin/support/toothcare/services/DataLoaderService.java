package cting.com.robin.support.toothcare.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cting.com.robin.support.toothcare.datagenerator.DataFactory;
import cting.com.robin.support.toothcare.datagenerator.JsonFileParser;
import cting.com.robin.support.toothcare.datagenerator.RawFileParser;
import cting.com.robin.support.toothcare.datagenerator.SampleData;
import cting.com.robin.support.toothcare.models.MessageEvent;

public class DataLoaderService extends IntentService {

    public static final String TAG = "cting/DataLoaderService";

    public static final String ACTION_LOAD = "cting.com.robin.support.toothcare.action.LOAD";

    public static final String EXTRA_DATA_FROM = "DATA_FROM";
    public static final String DATA_SOURCE_RAW_FILE = "raw_file";
    public static final String DATA_SOURCE_EXTERNAL_JSON = "external_json";

    private MessageEvent mMsgEvent = new MessageEvent();

    public DataLoaderService() {
        super("Invisalign data service");
    }

    public static void startActionLoad(Context context, String dataFrom) {
        Log.i(TAG, "startActionLoad: ");
        Intent intent = new Intent(context, DataLoaderService.class);
        intent.setAction(ACTION_LOAD);
        intent.putExtra(EXTRA_DATA_FROM, dataFrom);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent: " + intent);
        if (intent != null) {
            final String action = intent.getAction();
            Log.i(TAG, "onHandleIntent: action=" + action);
            if (ACTION_LOAD.equals(action)) {
                final String from = intent.getStringExtra(EXTRA_DATA_FROM);
                handleActionLoad(from);
            }
        }
    }

    private void handleActionLoad(String dataFrom) {
        Log.i(TAG, "handleActionLoad: from" + dataFrom);
        DataFactory factory = null;
        if (DATA_SOURCE_RAW_FILE.equals(dataFrom)) {
            factory = new RawFileParser(this);
        } else if (DATA_SOURCE_EXTERNAL_JSON.equals(dataFrom)) {
            factory = new JsonFileParser(this);
        }
        SampleData.getInstance().load(factory);

        Log.i(TAG, "handleActionLoad: load end");
        mMsgEvent.setLoadFinish(true);
        EventBus.getDefault().post(mMsgEvent);
    }
}
