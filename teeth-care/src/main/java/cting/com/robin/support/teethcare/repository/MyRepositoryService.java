package cting.com.robin.support.teethcare.repository;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class MyRepositoryService extends IntentService {

    public static final String TAG = "cting/RepositoryService";

    public static final String ACTION_LOAD = "cting.com.robin.support.teethcare.action.MESSAGE_LOAD";
    private static final String ACTION_SAVE = "cting.com.robin.support.teethcare.action.SAVE";

    public static final String EXTRA_DATA_FROM = "DATA_FROM";
    public static final String DATA_SOURCE_RAW_FILE = "raw_file";
    public static final String DATA_SOURCE_EXTERNAL_JSON = "external_json";
    public static final String MESSAGE_LOAD = "MESSAGE_LOAD";
    public static final String MESSAGE_SAVE = "SAVE";


    public MyRepositoryService() {
        super("Invisalign data service");
    }

    public static void startActionLoad(Context context) {
        Log.i(TAG, "startActionLoad: ");
        Intent intent = new Intent(context, MyRepositoryService.class);
        intent.setAction(ACTION_LOAD);
        context.startService(intent);
    }

    public static void startActionSave(Context context) {
        Log.i(TAG, "startActionSave: ");
        Intent intent = new Intent(context, MyRepositoryService.class);
        intent.setAction(ACTION_SAVE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.i(TAG, "onHandleIntent: " + intent);
        if (intent != null) {
            final String action = intent.getAction();
            Log.i(TAG, "onHandleIntent: action=" + action);
            if (ACTION_LOAD.equals(action)) {
                handleActionLoad();
                testHttp();
            } else if (ACTION_SAVE.equals(action)) {
                handleActionSave();
            }
        }
    }

    private void testHttp() {
    }

    private void handleActionLoad() {
        Log.i(TAG, "handleActionLoad:  start");
        MySource.getInstance().initAndOnlyOnce(this);

        MessageEvent msg = new MessageEvent();
        msg.setFinish(true);
        msg.setMessage(MESSAGE_LOAD);
        EventBus.getDefault().postSticky(msg);
        Log.i(TAG, "handleActionLoad:  end");
    }

    private void handleActionSave() {
        Log.i(TAG, "handleActionSave:  start");
        MySource.getInstance().save(this);

        MessageEvent msg = new MessageEvent();
        msg.setFinish(true);
        msg.setMessage(MESSAGE_SAVE);
        EventBus.getDefault().postSticky(msg);
        Log.i(TAG, "handleActionSave:  end");
    }
}
