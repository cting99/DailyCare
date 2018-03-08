package cting.com.robin.support.teethcare.daily;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.R;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;
import cting.com.robin.support.teethcare.databinding.DailyRecordListItemBinding;
import cting.com.robin.support.teethcare.models.MyListFragment;
import cting.com.robin.support.teethcare.repository.MySource;

public class DailyListFragment extends MyListFragment<DailyRecord, DailyRecordListItemBinding>
/*implements DialogInterface.OnClickListener*/{
//    private int mSelectedDay;

    @Override
    protected ArrayList<DailyRecord> newData() {
        return null;
    }

    @Override
    protected ArrayList<DailyRecord> loadDataFromService() {
        return MySource.getInstance().getGenerator().getDailyRecords(getContext());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.daily_record_list_item;
    }

    @Override
    public void onItemClick(DailyRecord item, int position) {
        DailyDetailActivity.launch(getContext(), item, position == 0);
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
            MyRepositoryService.startActionSave(getContext());
        }
    }*/
}
