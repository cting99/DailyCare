package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.utils.GsonHelper;

public class MySource {
    public static final String TAG = "cting/MySource";

    private MySource() {
    }

    public static DataGenerator getDataGenerator(Context context) {
        DataGenerator dg;
        // try external json
        dg = new SourceLocalJson();
        if (!dg.checkEmpty(context)) {
            return dg;
        }

        // try raw data
        dg = new SourceRawFile();
        if (!dg.checkEmpty(context)) {
//            MyRepositoryService.startActionBackup(context);   //save raw data to external json file as initialization
            return dg;
        }

        // sample data for test
        /*dg = new SourceSample();
        if (!dg.checkEmpty(context)) {
            return dg;
        }*/

        return dg;
    }

}
