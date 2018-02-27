package cting.com.robin.toothdaily.fragments;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.toothdaily.R;
import cting.com.robin.toothdaily.databinding.ProgressListItemBinding;
import cting.com.robin.toothdaily.datagenerator.RecordDatas;
import cting.com.robin.toothdaily.model.DailyRecord;
import cting.com.robin.toothdaily.model.ProgressItem;
import cting.com.robin.toothdaily.utils.FormatHelper;

public class ProgressListFragment extends RobinListFragment<ProgressItem,ProgressListItemBinding> {
    public static final String TAG = "cting/tooth/pglist";

    @Override
    public void onItemClick(ProgressItem item) {

    }

    @Override
    public boolean onItemLongClick(ProgressItem item) {
        return false;
    }

    @Override
    protected ArrayList<ProgressItem> newData() {
        ArrayList<ProgressItem> list = new ArrayList<>();
        Map<Integer, ArrayList<DailyRecord>> map = RecordDatas.readInvisalignFile();
        if (map != null && map.size() > 0) {
            Object[] keySetArray = map.keySet().toArray();
            Arrays.sort(keySetArray);

            ProgressItem item = null;
            ArrayList<DailyRecord> records;
            int progressTimeCount = 0;
            for (Object category : keySetArray) {
                progressTimeCount = 0;
                records = map.get(category);
                item = new ProgressItem();
                item.setProgressIndex((Integer) category);
                item.setDayCount(records.size());
                for (DailyRecord record : records) {
                    progressTimeCount += record.getTotalTime();
                }
                item.setTotalTime(FormatHelper.formatDuration(progressTimeCount));
                list.add(item);
            }
        }
        Log.i(TAG, "getData: " + list);
        return list;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.progress_list_item;
    }

    @Override
    public void bindItemData(ProgressItem item, ProgressListItemBinding binding) {
        binding.setItem(item);
    }
/*
    RecyclerView mRecyclerView;
    ProgressListAdapter mAdapter;
    public ProgressListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        updateApapter();
        return view;
    }

    protected void updateApapter() {
        if (mAdapter == null) {
            mAdapter = new ProgressListAdapter(getContext(), getData());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private ArrayList<ProgressItem> getData() {

    }*/

}
