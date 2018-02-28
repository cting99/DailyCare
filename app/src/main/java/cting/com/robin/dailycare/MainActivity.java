package cting.com.robin.dailycare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.commom.activities.TextActivity;
import cting.com.robin.support.commom.utils.FileHelper;

public class MainActivity extends TextActivity {


    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();

        String data = FileHelper.readFile(FileHelper.DIR + "invisalign.txt");
        FileHelper.writeFile(data, FileHelper.DIR + "invisalign_raw.txt");

        setMessage(data);

        TextActivity.startShowMessage(this,data);
    }
}
