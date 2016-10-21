package com.cayden.collect.dagger2mvp.presenter;

import com.cayden.collect.dagger2mvp.model.User;
import com.cayden.collect.dagger2mvp.view.ILoginView;

import javax.inject.Inject;

/**
 * Created by cuiran
 * Time  16/10/21 10:50
 * Email cuiran2001@163.com
 * Description
 */
public class LoginPresenterCompl implements ILoginPresenter {

    private ILoginView loginView;

    private User user;

    @Inject
    public LoginPresenterCompl(ILoginView view){
        loginView=view;
        user=new User("cayden","123456");
    }

    @Override
    public void clear() {
        loginView.onClearText();
    }

    @Override
    public void doLogin(String name, String password) {
        boolean result = false ;
        int code = 0 ;
        if(name.equals(user.getName()) && password.equals(user.getPassword())){
            result = true ;
            code = 1 ;
        }else{
            result = false ;
            code = 0 ;
        }

        loginView.onLoginResult(result,code);
    }
}
