package cting.com.robin.support.teethcare.daily.detail;

import android.content.Intent;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.teethcare.R;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.databinding.DailyDetailFragmentBinding;
import cting.com.robin.support.teethcare.databinding.TimeSliceEditLayoutBinding;
import cting.com.robin.support.teethcare.repository.DataGenerator;
import cting.com.robin.support.teethcare.repository.MessageEvent;
import cting.com.robin.support.teethcare.repository.MyRepositoryService;
import cting.com.robin.support.teethcare.repository.MySource;
import cting.com.robin.support.teethcare.views.StateButton;

public class DailyDetailFragment<B extends ViewDataBinding> extends RobinListFragment<TimeSlice, B> implements StateButton.Callback {

    public static final String ACTION_EDIT = "action_edit";
    public static final String DAILY_RECORD = "daily_record";
    public static final String DAILY_RECORD_DATE = "date";
    private DailyRecord mDailyRecord;
    private boolean mIsEditMode = false;
    private DataGenerator mGenerator;
    private DailyDetailFragmentBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(DAILY_RECORD_DATE)) {
            mGenerator = MySource.getInstance().getGenerator();
            mDailyRecord = mGenerator.getDayByDate(bundle.getString(DAILY_RECORD_DATE));
            mIsEditMode = bundle.getBoolean(ACTION_EDIT);
            Log.i(TAG, "onCreate: date=" + mDailyRecord.getDate() + ", mIsEditMode=" + mIsEditMode);
            getActivity().setTitle(String.valueOf(mDailyRecord.getIndex()));
            getActivity().setTitle(getString(R.string.daily_detail_fragment_title, mDailyRecord.getIndex()));
            setHasOptionsMenu(true);
        } else {
            Toast.makeText(getContext(), "Not exist!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveFinished(MessageEvent msg) {
        Log.i(TAG, "onSaveFinished: " + msg.getMessage() + " " + msg.isFinish());
        if (msg.isFinish() && MyRepositoryService.MESSAGE_SAVE.equals(msg.getMessage())) {
            Toast.makeText(getContext(), "save finished from " + msg.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

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
        mIsEditMode = true;
        getActivity().finish();
        DailyDetailActivity.launch(getContext(), mDailyRecord, mIsEditMode);
    }

    @Override
    protected void selectMenuSave() {
        MyRepositoryService.startActionSave(getContext());
        mBinding.setItem(mDailyRecord);//update total time
//        Toast.makeText(getContext(), "save " + mDailyRecord.getDate(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.daily_detail_fragment, container, false);
//        View view = inflater.inflate(R.layout.daily_detail_fragment, container, false);
        mBinding.setItem(mDailyRecord);
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
        if (mDailyRecord != null) {
            return (ArrayList<TimeSlice>) mDailyRecord.getSliceList();
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
        TimeSlice slice=new TimeSlice.Builder().newNow().build();
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
