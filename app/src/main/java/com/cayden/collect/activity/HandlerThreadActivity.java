package com.cayden.collect.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.cayden.collect.R;

public class HandlerThreadActivity extends AppCompatActivity {

    private TextView mTvInfo;

    private HandlerThread mCheckMsgThread;

    private Handler mCheckMsgHandler;

    private boolean isUpdateInfo;

    private static final int MSG_UPDATE_INFO = 0x110;

    private Handler mHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        initBackThread();
        mTvInfo=(TextView)findViewById(R.id.id_textview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isUpdateInfo=true;
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isUpdateInfo=false;

        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
    }

    private void initBackThread(){
        mCheckMsgThread=new HandlerThread("check-message-coming");
        mCheckMsgThread.start();

        mCheckMsgHandler=new Handler(mCheckMsgThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                checkForUpadte();
                if(isUpdateInfo){
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO,1000);
                }
            }
        };
    }


    private void checkForUpadte(){
        try{
            Thread.sleep(1000);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
                    result = String.format(result, (int) (Math.random() * 3000 + 1000));
                    mTvInfo.setText(Html.fromHtml(result));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCheckMsgThread.quit();
    }
}
