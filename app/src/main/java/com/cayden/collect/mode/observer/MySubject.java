package com.cayden.collect.mode.observer;

/**
 * Created by cuiran on 16/7/13.
 */
public class MySubject  extends AbstractSubject {

    @Override
    public void operation() {
        System.out.println("update self");
        notifyObservers();
    }
}
