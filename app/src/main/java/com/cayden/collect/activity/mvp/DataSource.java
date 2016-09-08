package com.cayden.collect.activity.mvp;

/**
 * Created by cuiran
 * Time  16/9/8 15:07
 * Email cuiran2001@163.com
 * Description
 */
public interface DataSource {

    String getStringFromRemote();

    String getStringFromCache();
}
