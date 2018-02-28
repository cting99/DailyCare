package cting.com.robin.toothdaily.activities;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.toothdaily.R;
import cting.com.robin.toothdaily.fragments.ProgressDetailFragment;

/**
 * Created by cting on 2018/2/27.
 */

public class ProgressDetailActivity extends RobinListActivity {
//    @Override
//    protected int getLayoutId() {
//        return R.layout.content_progress_detail;
//    }


    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        addFragment(new ProgressDetailFragment());
    }
}
