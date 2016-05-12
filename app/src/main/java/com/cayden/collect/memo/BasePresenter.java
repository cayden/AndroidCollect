package com.cayden.collect.memo;

/**
 * Created by cuiran on 16/5/11.
 */
public interface BasePresenter {
    /**
     * 页面初始化的时候做的事情，根据业务决定是否需要
     * 加载数据，一般在View的onResume()中执行。
     */
    void start();
}
