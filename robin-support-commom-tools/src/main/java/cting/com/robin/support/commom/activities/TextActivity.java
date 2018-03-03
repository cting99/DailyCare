package cting.com.robin.support.commom.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import cting.com.robin.support.commom.R;

public class TextActivity extends BasePermissionCheckActivity {

    public static final String KEY_MESSAGE = "message";

    protected TextView mTextContent;

    public static void startShowMessage(Context context, String message) {
        Intent intent = new Intent(context, TextActivity.class);
        intent.putExtra(KEY_MESSAGE, message);
        context.startActivity(intent);
    }

    @Override
    protected void onPermissionReady() {
        setContentView(R.layout.activity_scroll_text);
        mTextContent = findViewById(R.id.text_content);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(KEY_MESSAGE)) {
            String msg = bundle.getString(KEY_MESSAGE);
            setMessage(msg);
        }
    }

    protected void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            mTextContent.setText(message);
        }
    }

    protected void appendMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            mTextContent.append(message);
        }
    }
}
