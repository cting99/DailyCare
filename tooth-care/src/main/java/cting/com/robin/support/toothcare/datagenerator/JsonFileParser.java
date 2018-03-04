package cting.com.robin.support.toothcare.datagenerator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.toothcare.models.DailyRecord;
import cting.com.robin.support.toothcare.models.ProgressRecord;
import cting.com.robin.support.toothcare.utils.GsonHelper;

public class JsonFileParser extends DataFactory {
    public static final String TAG = "cting/util/" + JsonFileParser.class.getCanonicalName();
    public static final String FILE_INVISALIGN_JSON = FileHelper.EXTERNAL_DIR + "tooth_daily.json";

    public JsonFileParser(Context context) {
        super(context);
    }

    @Override
    public void load() {
        progressRecords = GsonHelper.readFromJson(context);
        for (ProgressRecord record : progressRecords) {
            dailyRecords.addAll(record.getDailyRecords());
        }
        sortDes();
    }

    @Override
    public void addDailyItem(DailyRecord dailyRecord) {

    }
}
