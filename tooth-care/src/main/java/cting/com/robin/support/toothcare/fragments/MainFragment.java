package cting.com.robin.support.toothcare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.activities.AllInOneActivity;
import cting.com.robin.support.toothcare.datagenerator.SampleData;
import cting.com.robin.support.toothcare.models.ProgressRecord;


public class MainFragment extends Fragment {


    @BindView(R.id.new_braces)
    Button newBraces;
    @BindView(R.id.new_today)
    Button newToday;
    Unbinder unbinder;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.activity_label_all_in_one);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.new_braces)
    public void onNewBracesClicked() {
        int index = 1;
        ProgressRecord lastRecord = SampleData.getInstance().getLastProgressRecord();
        if (lastRecord != null) {
            index = lastRecord.getProgressIndex() + 1;
        }
        ProgressRecord newRecord = new ProgressRecord.Builder()
                .progressIndex(index)
                .build();
        SampleData.getInstance().addProgressRecord(newRecord);
        SampleData.getInstance().save(getContext());
        Toast.makeText(getContext(), "new braces:" + index, Toast.LENGTH_SHORT).show();
        if (getActivity() instanceof AllInOneActivity) {
            AllInOneActivity activity = (AllInOneActivity) getActivity();
            activity.setFragment(AllInOneActivity.FRAGMENT_PROGRESS_LIST);
        }

    }

    @OnClick(R.id.new_today)
    public void onNewTodayClicked() {
        if (getActivity() instanceof AllInOneActivity) {
            AllInOneActivity activity = (AllInOneActivity) getActivity();
            activity.setFragment(AllInOneActivity.FRAGMENT_TODAY);
        }
    }
}
