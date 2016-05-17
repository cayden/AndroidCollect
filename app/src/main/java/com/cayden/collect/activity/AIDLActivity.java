package com.cayden.collect.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;
import com.cayden.collect.service.IAIDLServerService;
import com.cayden.collect.service.Person;

public class AIDLActivity extends BaseActivity implements View.OnClickListener {

    private Button aidlBtn;
    private TextView aidlTv;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_aidl;
    }

    @Override
    protected void initView() {
        Toolbar toolbar =  customFindViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = customFindViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        aidlBtn=customFindViewById(R.id.aidlBtn);
        aidlBtn.setOnClickListener(this);
        aidlTv=customFindViewById(R.id.aidlTv);

    }

    private IAIDLServerService mAidlServerService;

    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAidlServerService=IAIDLServerService.Stub.asInterface(iBinder);
            try{
                Person person= mAidlServerService.getPerson();
                String str = "姓名：" + person.getName() + "\n" + "年龄："
                        + person.getAge();
                aidlTv.setText(str);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mAidlServerService=null;
        }
    };
    @Override
    public void onClick(View view) {
        Intent service=new Intent("com.cayden.collect.service.AIDLServerService").setPackage("com.cayden.collect");
        bindService(service,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
