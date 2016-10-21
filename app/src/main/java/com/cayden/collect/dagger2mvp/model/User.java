package com.cayden.collect.dagger2mvp.model;

/**
 * Created by cuiran
 * Time  16/10/21 10:47
 * Email cuiran2001@163.com
 * Description
 */
public class User {

    private String name ;
    private String password ;

    public User(String name,String password){
        this.name = name ;
        this.password = password ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
