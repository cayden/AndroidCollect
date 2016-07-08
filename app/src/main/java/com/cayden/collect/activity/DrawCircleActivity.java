package com.cayden.collect.activity;

import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;
import com.cayden.collect.custom.RadialProgressWidget;

/**
 * Created by cuiran on 16/7/8.
 */
public class DrawCircleActivity extends BaseActivity {

    private RadialProgressWidget mView;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initView() {
        mView=customFindViewById(R.id.radial_view);

    }
}
