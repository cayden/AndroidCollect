package com.cayden.collect.mode.observer;

/**
 * 当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！对象之间是一种一对多的关系。
 * Created by cuiran on 16/7/13.
 */
public class ObserverTest {

    public static void main(String [] args){
        Subject sub=new MySubject();

        sub.add(new Observer1());
        sub.add(new Observer2());

        sub.operation();
    }
}
