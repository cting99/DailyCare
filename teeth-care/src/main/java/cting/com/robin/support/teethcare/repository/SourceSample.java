package cting.com.robin.support.teethcare.repository;

import android.content.Context;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;

class SourceSample extends DataGenerator {

    @Override
    public void forceLoad(Context context) {
        super.forceLoad(context);
        mDailyList = DailyRecord.Builder.sampleData();
        mBracesList = BracesRecord.Builder.sampleData();
    }

}
