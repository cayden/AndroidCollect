package com.cayden.collect.study.binder;

import android.os.RemoteException;

/**
 * Created by cuiran
 * Time  16/9/18 14:28
 * Email cuiran2001@163.com
 * Description
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
