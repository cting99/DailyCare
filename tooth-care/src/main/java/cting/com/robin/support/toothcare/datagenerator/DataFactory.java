package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.utils.GsonHelper;

public abstract class DataFactory {

    public static final String TAG = "cting/tooth/data";

    protected ArrayList<ProgressRecord> progressRecords = new ArrayList<>();
    protected ArrayList<DailyRecord> dailyRecords = new ArrayList<>();
    protected Context context;

    public DataFactory(Context context) {
        this.context = context;
    }

    public void sortDes() {
        Collections.sort(progressRecords, new Comparator<ProgressRecord>() {
            @Override
            public int compare(ProgressRecord o1, ProgressRecord o2) {
                return o2.getProgressIndex() - o1.getProgressIndex();
            }
        });
        Collections.sort(dailyRecords, new Comparator<DailyRecord>() {
            @Override
            public int compare(DailyRecord o1, DailyRecord o2) {
                return o2.getDayIndex() - o1.getDayIndex();
            }
        });
    }


    // progress record begin
    public ArrayList<ProgressRecord> getProgressRecords() {
        return progressRecords;
    }

    public ProgressRecord getLastProgressRecord() {
        if (progressRecords.size()== 0) {
            progressRecords.add(ProgressRecord.newFirstProgress());
        }
        return progressRecords.get(0);
    }

    public int getLastProgressIndex() {
        if (progressRecords.size() == 0) {
            return 0;
        }
        return getLastProgressRecord().getProgressIndex();
    }

    public void addProgressItem(ProgressRecord progressRecord) {
        int size = progressRecords.size();
        int index = size == 0 ? 0 : size - 1;
        progressRecords.add(index, progressRecord);
    }
    // progress record end


    // daily record begin
    public ArrayList<DailyRecord> getDailyRecords() {
        return dailyRecords;
    }

    public DailyRecord getLastDailyRecord() {
        if (dailyRecords.size()== 0) {
            dailyRecords.add(DailyRecord.newFirstDay());
        }
        return dailyRecords.get(0);
    }

    public void addDailyItem(DailyRecord dailyRecord) {
        dailyRecords.add(dailyRecord);
        ProgressRecord progressRecord = getLastProgressRecord();
        if (progressRecord == null) {
            progressRecord = new ProgressRecord.Builder()
                    .progressIndex(1)
                    .build();
        }
        getLastProgressRecord().addRecord(dailyRecord);
        dailyRecords.add(dailyRecord);
    }
    // daily record end


    public boolean save(Context context) {
        return GsonHelper.writeToGson(context, progressRecords);
    }

    public abstract void load();

}
