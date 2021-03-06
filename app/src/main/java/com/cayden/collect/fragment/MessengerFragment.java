package com.cayden.collect.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cayden.collect.R;
import com.cayden.collect.activity.AIDLActivity;
import com.cayden.collect.activity.DrawCircleActivity;
import com.cayden.collect.activity.DrawSimpleCircle;
import com.cayden.collect.activity.HandlerThreadActivity;
import com.cayden.collect.activity.LedActivity;
import com.cayden.collect.activity.SendPhoneActivity;
import com.cayden.collect.activity.TickerActivity;
import com.cayden.collect.activity.TweenActivity;
import com.cayden.collect.custom.FloatView;
import com.cayden.collect.fragment.base.BaseFragment;
import com.cayden.collect.service.MessengerService;
import com.cayden.collect.study.binder.BinderPool;
import com.cayden.collect.study.binder.ComputeImpl;
import com.cayden.collect.study.binder.ICompute;
import com.cayden.collect.study.binder.ISecurityCenter;
import com.cayden.collect.study.binder.SecurityCenterImpl;

/**
 * Created by cuiran on 16/5/13.
 */
public class MessengerFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG=MessengerFragment.class.getSimpleName();

    private static final int MSG_SUM=0x110;

    private LinearLayout mLayout;

    private Button mBtnAdd;
    private TextView mTvState;
    private Messenger mService;

    private boolean isConn;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_messenger;
    }

    @Override
    protected void initView() {
        super.initView();
        mLayout=customFindViewById(R.id.id_ll_container);
        mTvState=customFindViewById(R.id.id_tv_callback);
        mBtnAdd=customFindViewById(R.id.id_btn_add);

        mBtnAdd.setOnClickListener(this);

        customFindViewById(R.id.id_btn_aidl).setOnClickListener(this);
        customFindViewById(R.id.id_btn_float).setOnClickListener(this);
        customFindViewById(R.id.id_btn_circle).setOnClickListener(this);
        customFindViewById(R.id.id_btn_radia).setOnClickListener(this);
        customFindViewById(R.id.id_btn_tween).setOnClickListener(this);
        customFindViewById(R.id.id_btn_crash).setOnClickListener(this);
        customFindViewById(R.id.id_btn_binder).setOnClickListener(this);
        customFindViewById(R.id.id_btn_thread).setOnClickListener(this);

        customFindViewById(R.id.id_btn_ticker).setOnClickListener(this);
        customFindViewById(R.id.id_btn_send).setOnClickListener(this);
        customFindViewById(R.id.id_btn_led).setOnClickListener(this);
        bindServiceInvoked();
    }


    private Messenger mMessenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msgFromServer) {
            switch (msgFromServer.what)
            {
                case MSG_SUM:
                    TextView tv = (TextView) mLayout.findViewById(msgFromServer.arg1);
                    tv.setText(tv.getText() + "=>" + msgFromServer.arg2);
                    break;
            }
            super.handleMessage(msgFromServer);
        }
    });


    private ServiceConnection mConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            isConn = true;
            mTvState.setText("已经连接!");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService =null;
            isConn = false;
            mTvState.setText("断开！！!");
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_btn_add:
                executeAdd();

                break;
            case R.id.id_btn_aidl:
                Intent intent=new Intent(getActivity(), AIDLActivity.class);
                startActivity(intent);
                break;
            case R.id.id_btn_circle:
                Intent intent2=new Intent(getActivity(), DrawSimpleCircle.class);
                startActivity(intent2);

                break;

            case R.id.id_btn_radia:
                Intent intent3=new Intent(getActivity(), DrawCircleActivity.class);
                startActivity(intent3);

                break;
            case R.id.id_btn_float:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (Settings.canDrawOverlays(getActivity())) {
                        showFloatView();
                    } else {
                        Intent intent1 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        startActivity(intent1);
                    }
                } else {
                    showFloatView();
                }
                break;
            case R.id.id_btn_tween:
                Intent intent4=new Intent(getActivity(), TweenActivity.class);
                startActivity(intent4);
                break;
            case R.id.id_btn_crash:
                int m=5/0;
                Log.d(TAG,""+m);
                break;
            case R.id.id_btn_binder:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doWork();
                    }
                }).start();

                break;

            case R.id.id_btn_thread:
                Intent intent5=new Intent(getActivity(), HandlerThreadActivity.class);
                startActivity(intent5);
                break;
            case R.id.id_btn_ticker:
                Intent intent6=new Intent(getActivity(), TickerActivity.class);
                startActivity(intent6);
                break;

            case R.id.id_btn_send:
                Intent intent7=new Intent(getActivity(), SendPhoneActivity.class);
                startActivity(intent7);
                break;
            case R.id.id_btn_led:
                Intent intent8=new Intent(getActivity(), LedActivity.class);
                startActivity(intent8);
                break;

        }
    }

    ISecurityCenter securityCenter;
    ICompute mCompute;
    private void doWork(){
        Log.d(TAG,"doWork.......");
        BinderPool binderPool=BinderPool.getInstance(getActivity());
        IBinder securityBinder=binderPool.queryBinder(BinderPool.BINDER_SECRITY_CENTER);
        securityCenter=(ISecurityCenter) SecurityCenterImpl.asInterface(securityBinder);

        Log.d(TAG,"访问ISecurityCenter");
        String msg="HelloWorld ,I'm cayden";
        Log.d(TAG,"content="+msg);
        try {
            String password=securityCenter.encrypt(msg);
            Log.d(TAG,"password="+password);
            Log.d(TAG,"decrypt="+securityCenter.decrypt(password));
        }catch (Exception e){
            e.printStackTrace();
        }


        Log.d(TAG,"访问ICompute");
        IBinder computeBinder=binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute=(ICompute) ComputeImpl.asInterface(computeBinder);
        try {
            Log.d(TAG,"3+5="+mCompute.add(3,5));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showFloatView() {
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        FloatView floatView = new FloatView(getActivity().getApplicationContext());
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = 150;
        params.height = 150;
        params.x = 0;
        params.y = 0;
        windowManager.addView(floatView, params);
    }

    private int num;

    private void executeAdd(){
        try{
            int a=num++;
            int b=(int)(Math.random()*100);

            //创建一个tv,添加到LinearLayout中
            TextView tv = new TextView(getActivity());
            tv.setText(a + " + " + b + " = caculating ...");
            tv.setId(a);
            mLayout.addView(tv);

            Message msgFromClient = Message.obtain(null, MSG_SUM, a, b);
            msgFromClient.replyTo = mMessenger;
            if (isConn)
            {
                //往服务端发送消息
                mService.send(msgFromClient);
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    private void bindServiceInvoked()
    {
        Intent intent = new Intent(getActivity(), MessengerService.class);
        getActivity().getApplicationContext().bindService(intent, mConn, Context.BIND_AUTO_CREATE);
        Log.e(TAG, "bindService invoked !");
    }

    private void unbindServiceInvoked(){
        getActivity().getApplicationContext().unbindService(mConn);
        Log.e(TAG, "unbindService invoked !");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindServiceInvoked();
    }
}
