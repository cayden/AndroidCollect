package com.cayden.collect;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by cuiran on 16/5/10.
 */
public class CollectApplication extends Application {
    /**
     * SDK初始化也可以放到Application中
     * 46c730e7e33eabeb3ec790b3fb0a02d7
     * 3124f50157a5df138aba77a85e1d8909
     */
    public static String APPID ="77f2199061fe7d7d935a2f9c3159af01";
    @Override
    public void onCreate() {
        super.onCreate();
        //第二：默认初始化
        Bmob.initialize(this,APPID);
    }
}
