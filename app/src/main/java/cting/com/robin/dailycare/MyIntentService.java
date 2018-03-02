package cting.com.robin.dailycare;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cting.com.robin.support.commom.utils.FileHelper;

public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "cting.com.robin.dailycare.action.FOO";

    private static final String EXTRA_PARAM1 = "cting.com.robin.dailycare.extra.PARAM1";
    public static final String TAG = "cting/";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionFoo(Context context, String param1) {
        Log.i(TAG, "startActionFoo: ");
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionFoo(param1);
            }
        }
    }

    private void handleActionFoo(String param1) {
        Log.i(TAG, "handleActionFoo: ");
        MessageEvent event = new MessageEvent();

        event.setMsg("Loading...");
        EventBus.getDefault().post(event);

        String data = FileHelper.readFile(param1);
        event.setMsg(data);

        EventBus.getDefault().post(event);
    }
}
