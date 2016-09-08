package com.cayden.collect.activity.mvp;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cuiran
 * Time  16/9/8 15:10
 * Email cuiran2001@163.com
 * Description
 */
public class MainPresenter {
    MainView mainView;
    DataSource dataSource;

    public MainPresenter(){
        this.dataSource=new DataSourceImpl();
    }

    public MainPresenter test(){
        this.dataSource=new DataSourceImpl();
        return this;
    }

    public MainPresenter addTaskListener(MainView viewListener){
        this.mainView=viewListener;
        return this;
    }

    public void getData(){
//        String str=dataSource.getStringFromRemote()+dataSource.getStringFromCache();
//        mainView.onShowString(str);

        /**
         * dataAction是我们的数据业务逻辑，
         * viewAction是界面的显示逻辑，通过RxJava的传递和变换，
         * dataAction会在由RxJava管理的IO线程—Schedulers.io() 中执行，
         * 而viewAction则会在UI线程—AndroidSchedulers.mainThread()中执行。
         */
        final Func1<String,String> dataAction=new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s+dataSource.getStringFromRemote()+dataSource.getStringFromCache();
            }
        };


        Action1<String> viewAction=new Action1<String>() {
            @Override
            public void call(String s) {
                mainView.onShowString(s);
            }
        };


        Observable.just("RxJava")
                .observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);
    }
}
