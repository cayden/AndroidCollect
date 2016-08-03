package com.cayden.collect.mode.test;

/**
 * Created by cuiran on 16/7/26.
 */
public class OldMan extends People {

    @Override
    public void like(String str) {
        super.like(str);
        System.out.println("老人喜欢"+str);
    }
}
