package com.cayden.collect.mode.observer;

/**
 * Created by cuiran on 16/7/13.
 */
public class Observer1 implements Observer {

    @Override
    public void update() {
        System.out.println("observer1 has received");
    }
}
