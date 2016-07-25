package com.cayden.collect.activity;

import android.view.View;

import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;
import com.cayden.collect.custom.RadialProgressWidget;

/**
 * Created by cuiran on 16/7/8.
 */
public class DrawCircleActivity extends BaseActivity implements View.OnClickListener {

    private RadialProgressWidget mView;
    private int progress = 0;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initView() {
        mView=customFindViewById(R.id.radial_view);
        customFindViewById(R.id.startBtn).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBtn:
                mView.scanStart();
                start();
                break;
        }
    }

    private void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress <= 100) {
                    progress += 1;

                    System.out.println(progress);

                    mView.setProgress(progress);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                mView.scanFinish();
                progress=0;
            }
        }).start();

    }
}
