package cting.com.robin.support.teethcare.daily.detail;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.teethcare.R;
import cting.com.robin.support.teethcare.database.SourceDatabase;
import cting.com.robin.support.teethcare.databinding.DailyDetailFragmentBinding;
import cting.com.robin.support.teethcare.databinding.TimeSliceEditLayoutBinding;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;
import cting.com.robin.support.teethcare.utils.TimeSliceHelper;
import cting.com.robin.support.teethcare.views.StateButton;

public class DailyDetailFragment<B extends ViewDataBinding> extends RobinListFragment<TimeSlice, B> implements StateButton.Callback {

    public static final String ACTION_EDIT = "action_edit";
    public static final String DAILY_RECORD = "daily_record";
    public static final String DAILY_RECORD_DAY_INDEX = "day_index";

    private DailyDetailRecord mDetailRecord;
    private boolean mIsEditMode = false;
    private DailyDetailFragmentBinding mBinding;
    private SourceDatabase mDBSource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null || !bundle.containsKey(DAILY_RECORD_DAY_INDEX)) {
            Toast.makeText(getContext(), "Not exist!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        int dayIndex = bundle.getInt(DAILY_RECORD_DAY_INDEX);
        mDBSource = new SourceDatabase(getContext());
        mDetailRecord = mDBSource.queryDayDetailRecord(dayIndex);
        if (mDetailRecord == null) {
            Log.e(TAG, "onCreate: no this record,dayIndex=" + dayIndex);
            Toast.makeText(getContext(), "onCreate: no this record,dayIndex=" + dayIndex, Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        mIsEditMode = bundle.getBoolean(ACTION_EDIT);
        Log.i(TAG, "onCreate,detail record: " + mDetailRecord + ", mIsEditMode=" + mIsEditMode);
        getActivity().setTitle(String.valueOf(mDetailRecord.getIndex()));
        getActivity().setTitle(getString(R.string.daily_detail_fragment_title, mDetailRecord.getIndex()));
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDBSource != null) {
            mDBSource.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        EventBus.getDefault().unregister(this);
    }
/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveFinished(MessageEvent msg) {
        Log.i(TAG, "onSaveFinished: " + msg.getMessage() + " " + msg.isFinish());
        if (msg.isFinish() && MyRepositoryService.MESSAGE_SAVE.equals(msg.getMessage())) {
            Toast.makeText(getContext(), "save finished from " + msg.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mIsEditMode) {
            addSaveMenu(menu);
        } else {
            addEditMenu(menu);
        }
    }

    @Override
    protected void selectMenuEdit() {
        changeEditMode(true);
    }

    @Override
    protected void selectMenuSave() {
        Log.i(TAG, "selectMenuSave,before: " + mDetailRecord);
        String line = TimeSliceHelper.toLine(mDataList);
        mDetailRecord.setTimeLine(line);
        mDetailRecord.setTotalTime(TimeSliceHelper.calculateTotalTime(line));
        int count = mDBSource.updateDaily(mDetailRecord);
        Log.i(TAG, "selectMenuSave, after:" + mDetailRecord);
        Log.i(TAG, "selectMenuSave, save success:" + (count > 0));
        mBinding.setItem(mDetailRecord);//update total time

        changeEditMode(false);
//        Toast.makeText(getContext(), "save " + mDetailRecord.getDate(), Toast.LENGTH_SHORT).show();
    }

    private void changeEditMode(boolean editMode) {
        mIsEditMode = editMode;
        getActivity().finish();
        DailyDetailActivity.launch(getContext(), mDetailRecord.getIndex(), mIsEditMode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.daily_detail_fragment,
                container, false);
//        View view = inflater.inflate(R.layout.daily_detail_fragment, container, false);
        mBinding.setItem(mDetailRecord);
        mBinding.setEditMode(mIsEditMode);
        setupRecyclerView(mBinding.recyclerView);
        setDefaultAdapter(this);
        setDataList(newData());
        return mBinding.getRoot();
    }

    @Override
    protected void setupRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (!mIsEditMode) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    protected ArrayList<TimeSlice> newData() {
        if (mDetailRecord != null) {
            ArrayList<TimeSlice> list = TimeSliceHelper.generateListFromLine(mDetailRecord.getTimeLine());
            Log.i(TAG, "newData: list=" + list);
            return list;
        }
        return null;
    }

    @Override
    public int getItemLayoutId() {
        if (mIsEditMode) {
            return R.layout.time_slice_edit_layout;
        } else {
            return R.layout.time_slice_layout;
        }
    }

    @Override
    public void bindItemData(TimeSlice item, B binding, int position) {
        super.bindItemData(item, binding, position);
        if (mIsEditMode && binding instanceof TimeSliceEditLayoutBinding) {
            TimeSliceEditLayoutBinding editBinding = (TimeSliceEditLayoutBinding) binding;
            editBinding.addNewTimeSliceBtn.setCallback(this);
            editBinding.addNewTimeSliceBtn.setEntryState(item);
            editBinding.addNewTimeSliceBtn.setPosition(position, mDataList.size());
            editBinding.addNewTimeSliceBtn.refresh();
        }
    }

    @Override
    public void onAdd() {
        TimeSlice slice = new TimeSlice();
        slice.setFrom(TimeFormatHelper.formatNow());
        slice.setTo("");
        mDataList.add(slice);//always add at last position
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete(int position) {
        mDataList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDone(int position) {
        mDataList.get(position).close();
        mAdapter.notifyDataSetChanged();

    }
}
