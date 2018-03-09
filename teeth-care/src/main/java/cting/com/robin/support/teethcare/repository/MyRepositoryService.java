package cting.com.robin.support.teethcare.repository;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.database.SourceDatabase;
import cting.com.robin.support.teethcare.models.IRecord;
import cting.com.robin.support.teethcare.utils.GsonHelper;

public class MyRepositoryService extends IntentService {

    public static final String TAG = "cting/RepositoryService";

    public static final String ACTION_LOAD = "cting.com.robin.support.teethcare.action.MESSAGE_LOAD";
    private static final String ACTION_SAVE = "cting.com.robin.support.teethcare.action.SAVE";
    private static final String ACTION_CHECK_DATABASE = "cting.com.robin.support.teethcare.action.CHECK_DATABASE";

    public static final String MESSAGE_SAVE = "SAVE";

    public static final String EXTRA_DATA_TAG = "DATA_TAG";
    public static final String DATA_TAG_DAILY_LIST = "DAILY_LIST";
    public static final String DATA_TAG_BRACES_LIST = "BRACES_LIST";

    private SourceDatabase mDBSource;


    public MyRepositoryService() {
        super("Invisalign data service");
    }


    public static void startActionCheckDatabase(Context context) {
        Log.i(TAG, "startActionCheckDatabase: ");
        Intent intent = new Intent(context, MyRepositoryService.class);
        intent.setAction(ACTION_CHECK_DATABASE);
        context.startService(intent);
    }

    public static void startActionLoad(Context context, String dataTag) {
        Log.i(TAG, "startActionLoad: ");
        Intent intent = new Intent(context, MyRepositoryService.class);
        intent.setAction(ACTION_LOAD);
        intent.putExtra(EXTRA_DATA_TAG, dataTag);
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
            mDBSource = new SourceDatabase(this);
            mDBSource.open();
            if (ACTION_LOAD.equals(action)) {
                String tag = intent.getStringExtra(EXTRA_DATA_TAG);
                if (DATA_TAG_DAILY_LIST.equals(tag)) {
                    sendLoadFinishMsg(tag, true, mDBSource.queryDaily(""));
                } else if (DATA_TAG_BRACES_LIST.equals(tag)) {
                    sendLoadFinishMsg(tag, true, mDBSource.queryBraces(-1));
                } else {
                    sendLoadFinishMsg(tag, false, null);
                }
            } else if (ACTION_SAVE.equals(action)) {
                handleActionSave();
            } else if (ACTION_CHECK_DATABASE.equals(action)) {
                handleActionCheckDatabase();
            }
            mDBSource.close();
        }
    }

    private void handleActionCheckDatabase() {
        if (mDBSource.getDailyCount() == 0 && mDBSource.getRecordCount() == 0) {
            DataGenerator generator = MySource.getDataGenerator(this);
            mDBSource.insertDailyList(generator.getDailyRecords(this));
            mDBSource.insertBracesList(generator.getBracesRecords(this));
            Log.d(TAG, "handleActionCheckDatabase finish: Initialize data to database from " + generator.getName());
        } else {
            Log.i(TAG, "handleActionCheckDatabase: db already initialized!");
        }

    }

    private <T extends IRecord> void sendLoadFinishMsg(String dataTag, boolean finished, ArrayList<T> list) {
        Log.i(TAG, "sendLoadFinishMsg," + dataTag + ":" + finished);
        MessageEvent<T> msg = new MessageEvent();
        msg.setFinish(finished);
        msg.setMessage(dataTag);
        msg.setList(list);
        EventBus.getDefault().post(msg);
    }

    private void handleActionSave() {
        Log.i(TAG, "handleActionSave,start");
//        MySource.getInstance().save(this);
        GsonHelper.saveDaily(this, mDBSource.queryDaily(null));
        GsonHelper.saveBraces(this, mDBSource.queryBraces(-1));

        MessageEvent msg = new MessageEvent();
        msg.setFinish(true);
        msg.setMessage(MESSAGE_SAVE);
        EventBus.getDefault().post(msg);
        Log.i(TAG, "handleActionSave,end");
    }
}
