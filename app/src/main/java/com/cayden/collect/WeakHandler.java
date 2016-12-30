package com.cayden.collect;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by cuiran
 * Time  16/12/30 15:01
 * Email cuiran2001@163.com
 * Description
 */

public abstract class WeakHandler<T> extends Handler {
    private WeakReference<T> mOwner;

    public WeakHandler(T owner) {
        mOwner = new WeakReference<T>(owner);
    }


    public T getOwner() {
        return mOwner.get();
    }
}