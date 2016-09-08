package com.cayden.collect.activity.mvp;

/**
 * Created by cuiran
 * Time  16/9/8 15:08
 * Email cuiran2001@163.com
 * Description
 */
public class DataSourceImpl implements DataSource {

    @Override
    public String getStringFromRemote() {
        return "remote Hello";
    }


    @Override
    public String getStringFromCache() {
        return "cache Hello";
    }
}
