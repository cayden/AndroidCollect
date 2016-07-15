package com.cayden.collect.mode.observer;

/**
 * Created by cuiran on 16/7/13.
 */
public interface Subject {

    public void add(Observer observer);

    public void del(Observer observer);

    public void notifyObservers();

    public void operation();
}
