package com.cayden.collect.memo;

/**
 * Created by cuiran on 16/5/11.
 */
public interface BaseView<T> {
    /**
     * 使用fragment作为view时，将activity中的presenter传递  给fragment
     * @param presenter
     */
    void setPresenter(T presenter);
}
