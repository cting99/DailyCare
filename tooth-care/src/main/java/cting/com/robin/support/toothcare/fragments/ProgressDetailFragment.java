package cting.com.robin.support.toothcare.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.activities.DailyRecordDetailActivity;
import cting.com.robin.support.toothcare.databinding.DailyRecordListItemBinding;
import cting.com.robin.support.toothcare.databinding.ProgressDetailBinding;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;

public class ProgressDetailFragment extends RobinListFragment<DailyRecord, DailyRecordListItemBinding> {
    public static final String EXPORT_FILE_NAME = FileHelper.DIR + "tooth_daily.json";
    private ProgressRecord progressRecord;

    public ProgressDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ProgressRecord.PROGRESS_INDEX)) {
            progressRecord = new ProgressRecord(bundle);
        }
        Log.i(TAG, "onCreate: progressRecord=" + progressRecord);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProgressDetailBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.progress_detail, container, false);
        binding.setItem(progressRecord);
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setDefaultAdapter(this);
        setDataList(newData());
        return binding.getRoot();
    }

    @Override
    public void onItemClick(DailyRecord item) {
        DailyRecordDetailActivity.launchWithBundle(getContext(),item.toBundle());
    }

    @Override
    protected ArrayList<DailyRecord> newData() {
        return progressRecord.getDailyRecords();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.daily_record_list_item;
    }


    @Override
    protected void exportData(ArrayList<DailyRecord> dataList) {
//        Gson gson = new Gson();
//        gson.toJson(new Wrapper(mDataList));
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        gson.toJson(new Wrapper(mDataList));
        boolean ret = FileHelper.writeToExternal(EXPORT_FILE_NAME, gson.toString());
        Toast.makeText(getContext(), "write to " + EXPORT_FILE_NAME + ": " + (ret ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "exportData: " + gson.toString());
    }

    static class Wrapper{
        private ArrayList<DailyRecord> list;

        public Wrapper(ArrayList<DailyRecord> list) {
            this.list = list;
        }

        public ArrayList<DailyRecord> getList() {
            return list;
        }
    }
}
