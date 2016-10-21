package com.cayden.collect.dagger2mvp.dragger.component;

import com.cayden.collect.dagger2mvp.dragger.module.MainModule;
import com.cayden.collect.dagger2mvp.view.LoginActivity;

import dagger.Component;

/**
 * Created by cuiran
 * Time  16/10/21 10:46
 * Email cuiran2001@163.com
 * Description
 */
@Component(modules = MainModule.class)
public interface MainComponent {

    public void inject(LoginActivity activity);

}
