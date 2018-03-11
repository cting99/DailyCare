package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.database.DBItem;

public abstract class DataGenerator {

    protected String TAG = "cting/";
    protected ArrayList<DBItem> list = new ArrayList<>();
    private String mName;

    public DataGenerator(String name) {
        TAG += getClass().getSimpleName();
        mName = name;
    }

    public String getName() {
        return mName;
    }

    protected void initIfNeeded(Context context) {
        if (list.size() == 0) {
            long millions = System.currentTimeMillis();
            forceLoad(context);
            millions = System.currentTimeMillis() - millions;
            Log.i(TAG, "forceLoad cost: " + millions + "ms");
        }
    }

    protected abstract void forceLoad(Context context);

    public boolean checkEmpty(Context context) {
        int oldSize = list.size();
        initIfNeeded(context);
        Log.i(TAG, "checkEmpty,before:size=" +oldSize+" ,after:size="+ list.size());
        return list.size() == 0;
    }

    public ArrayList<DBItem> getList(Context context) {
        initIfNeeded(context);
        return list;
    }

}
