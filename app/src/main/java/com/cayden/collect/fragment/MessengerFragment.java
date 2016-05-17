package com.cayden.collect.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cayden.collect.R;
import com.cayden.collect.activity.AIDLActivity;
import com.cayden.collect.fragment.base.BaseFragment;
import com.cayden.collect.service.MessengerService;

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

        }
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
