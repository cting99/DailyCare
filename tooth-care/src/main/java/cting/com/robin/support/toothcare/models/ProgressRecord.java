package cting.com.robin.support.toothcare.models;

import android.os.Bundle;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.model.IRobinListItem;
import cting.com.robin.support.toothcare.utils.TimeFormatHelper;

/**
 * Created by cting on 2018/2/28.
 */

public class ProgressRecord implements IRobinListItem {
    public static final String PROGRESS_INDEX = "PROGRESS_INDEX";
    public static final String DAILYRECORDS = "DAILYRECORDS";

    @Expose
    private int progressIndex;

    @Expose
    private ArrayList<DailyRecord> dailyRecords = new ArrayList<>();

    public ProgressRecord() {
    }

    public ProgressRecord(Bundle bundle) {
        if (bundle != null) {
            this.progressIndex = bundle.getInt(PROGRESS_INDEX);
            this.dailyRecords = bundle.getParcelableArrayList(DAILYRECORDS);
        }
    }


    public int getProgressIndex() {
        return progressIndex;
    }

    public void setProgressIndex(int progressIndex) {
        this.progressIndex = progressIndex;
    }

    public String getStartDate() {
        return dailyRecords.get(0).getDate();
    }

    public String getEndDate() {
        return dailyRecords.get(dailyRecords.size() - 1).getDate();
    }

    public int getDayCount() {
        return dailyRecords.size();
    }

    public String getTotalTime() {
        long totalTimeMillions = 0;
        for (DailyRecord record : dailyRecords) {
            totalTimeMillions += record.getTotalTimeMillions();
        }
        return TimeFormatHelper.formatDuration(totalTimeMillions);
    }

    public ArrayList<DailyRecord> getDailyRecords() {
        return dailyRecords;
    }

    @Override
    public String toString() {
        return "ProgressRecord{" +
                "progressIndex=" + progressIndex +
                ", dailyRecords='" + dailyRecords +
                '}';
    }


    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(PROGRESS_INDEX, progressIndex);
        bundle.putParcelableArrayList(DAILYRECORDS, dailyRecords);
        return bundle;
    }

    public static ProgressRecord newRecord(int progressIndex) {
        ProgressRecord record = new ProgressRecord();
        record.setProgressIndex(progressIndex);
        return record;

    }

    public void addRecord(DailyRecord dailyRecord) {
        dailyRecords.add(dailyRecord);
    }

    public static ProgressRecord newFirstProgress() {
        return new ProgressRecord.Builder()
                .progressIndex(1)
                .build();
    }


    public static class Builder {
        private ProgressRecord progressRecord = new ProgressRecord();

        public ProgressRecord build() {
            return progressRecord;
        }

        public Builder progressIndex(int progressIndex) {
            progressRecord.progressIndex = progressIndex;
            return this;
        }

        public Builder newRecordList(DailyRecord dailyRecord) {
            if (dailyRecord != null) {
                progressRecord.addRecord(dailyRecord);
            }
            return this;
        }
    }
}
