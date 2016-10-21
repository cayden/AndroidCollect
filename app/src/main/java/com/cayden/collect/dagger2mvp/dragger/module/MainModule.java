package com.cayden.collect.dagger2mvp.dragger.module;

import com.cayden.collect.dagger2mvp.view.ILoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuiran
 * Time  16/10/21 10:37
 * Email cuiran2001@163.com
 * Description
 */
@Module
public class MainModule {

    private final ILoginView view;
    public MainModule(ILoginView view){
        this.view=view;
    }

    @Provides
    ILoginView provideILogView(){
        return view;
    }
}
