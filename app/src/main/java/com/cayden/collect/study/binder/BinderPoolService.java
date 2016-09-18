package com.cayden.collect.study.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by cuiran
 * Time  16/9/18 14:33
 * Email cuiran2001@163.com
 * Description
 */
public class BinderPoolService extends Service {

    private static final String TAG="BinderPoolService";

    private Binder mBinderPool=new BinderPool.BinderPoolImpl();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
