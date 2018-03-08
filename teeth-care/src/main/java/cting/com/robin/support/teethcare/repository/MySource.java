package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.utils.GsonHelper;

public class MySource {
    private static final MySource ourInstance = new MySource();
    public static final String TAG = "cting/MySource";
    private static DataGenerator generator;

    private MySource() {
    }

    public static MySource getInstance() {
        return ourInstance;
    }


    public DataGenerator getGenerator() {
        return generator;
    }

    public void initAndOnlyOnce(Context context) {
        generator = getDataGenerator(context);
        Log.i(TAG, "getDataGenerator: from " + generator.getClass().getSimpleName());
    }

    public void save(Context context) {
        if (generator != null) {
            Log.i(TAG, "save: start");
            GsonHelper.saveBraces(context, generator.getBracesRecords(context));
            GsonHelper.saveDaily(context, generator.getDailyRecords(context));
            Log.i(TAG, "save: end");
        }
    }

    private DataGenerator getDataGenerator(Context context) {
        DataGenerator dg;
        // try external json
        dg = new SourceLocalJson();
        if (!dg.checkEmpty(context)) {
            return dg;
        }

        // try raw data
        dg = new SourceRawFile();
        if (!dg.checkEmpty(context)) {
            MyRepositoryService.startActionSave(context);   //save raw data to external json file as initialization
            return dg;
        }

        // sample data
        dg = new SourceSample();
        if (!dg.checkEmpty(context)) {
            return dg;
        }

        return dg;
    }

}
