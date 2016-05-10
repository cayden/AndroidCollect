package com.cayden.collect.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by cuiran on 16/5/10.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        init();
        initView();
        initData();

    }

    protected void initData() {
    }

    /***
     * 用于在初始化View之前做一些事
     */
    protected void init() {

    }

    protected abstract void initView();

    protected abstract int setLayoutResourceID();

    protected <T extends View> T customFindViewById(int id) {
        return (T) super.findViewById(id);
    }


    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);

    }
}
