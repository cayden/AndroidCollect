package com.cayden.collect.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;

/**
 * Created by cuiran on 16/5/25.
 */
public class DeepLinksActivity extends BaseActivity {
    private static final String TAG=DeepLinksActivity.class.getSimpleName();
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_deep;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();
            if(null!=uri){
                String host = uri.getHost();
                if ("buydress".equals(host)) {
                    // 跳转到卖裙子界面
                    Log.i(TAG, "buydressbuydress");
                }
            }

        }
    }
}
