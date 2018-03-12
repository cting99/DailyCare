package cting.com.robin.support.teethcare.daily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.R;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;
import cting.com.robin.support.teethcare.database.SourceDatabase;
import cting.com.robin.support.teethcare.databinding.DailyListItemBinding;
import cting.com.robin.support.teethcare.MyListFragment;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

import static cting.com.robin.support.teethcare.repository.MyRepositoryService.DATA_TAG_DAILY_LIST;

public class DailyListFragment extends MyListFragment<DailyRecord, DailyListItemBinding>
/*implements DialogInterface.OnClickListener*/{
    //    private int mSelectedDay;

    public static final String BRACES_RECORD = "BRACES_RECORD";
    public static final String BRACES_RECORD_INDEX = "braces_index";
    private SourceDatabase mDBSource;
    private int mBracesIndex = -100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(BRACES_RECORD_INDEX)) {
            mBracesIndex = bundle.getInt(BRACES_RECORD_INDEX);
            mDBSource = new SourceDatabase(getContext());
            mDataList = mDBSource.queryDays(mBracesIndex);
            if (mDataList != null && mBracesIndex > 0) {
                getActivity().setTitle(getString(R.string.braces_record_index, String.valueOf(mBracesIndex)));
                Log.i(TAG, "onCreate: read from bundle:bracesIndex=" + mBracesIndex + ",item size=" + mDataList.size());
            }
        }
    }

    @Override
    protected ArrayList<DailyRecord> newData() {
        if (mBracesIndex > 0) {
            return mDataList;//from bundle
        }else {
            return null;
        }
    }

    @Override
    protected String getDataTag() {
        if (mBracesIndex > 0) {
            return null;
        } else {
            return DATA_TAG_DAILY_LIST;
        }
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.daily_list_item;
    }

    @Override
    public void onItemClick(DailyRecord item, int position) {
        DailyDetailActivity.launch(getContext(), item.getIndex(), TimeFormatHelper.isToday(item.getDate()));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
/*
    @Override
    public boolean onItemLongClick(DailyRecord item, int position) {
        mSelectedDay = item.getIndex();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Do you want to delete this day?")
                .setPositiveButton(android.R.string.ok, this);
        builder.create()
                .show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            MySource.getInstance().getGenerator().deleteDay(mSelectedDay);
            MyRepositoryService.startActionBackup(getContext());
        }
    }*/
}
