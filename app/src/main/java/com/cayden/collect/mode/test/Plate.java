package com.cayden.collect.mode.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuiran on 16/7/27.
 */
public class Plate {

    List<Object> eggs=new ArrayList<Object>();

    public synchronized Object getEgg(){
        if(eggs.size()==0){
            try{
                wait();
            }catch (InterruptedException e){

            }
        }
        Object egg=eggs.get(0);
        eggs.clear();
        notify();
        System.out.println("拿到鸡蛋");
        return egg;
    }

    public synchronized void putEgg(Object egg){
        if(eggs.size()>0){
            try {
                wait();
            }catch (InterruptedException e){

            }
        }
        eggs.add(egg);
        notify();
        System.out.println("放入鸡蛋");
    }

    static class AddThread extends Thread{
        private Plate plate;
        private Object egg=new Object();
        public AddThread(Plate plate){
            this.plate=plate;
        }

        public void run(){
            for(int i=0;i<5;i++){
                plate.putEgg(egg);
            }
        }
    }

    static class GetThread extends Thread{
        private Plate plate;
        public GetThread(Plate plate){
            this.plate=plate;
        }

        public void run(){
            for(int i=0;i<5;i++){
                plate.getEgg();
            }
        }
    }

    public static void main(String args[]){
        try{
            Plate plate=new Plate();
            Thread add=new Thread(new AddThread(plate));
            Thread get=new Thread(new GetThread(plate));
            add.start();
            get.start();
            add.join();
            get.join();
            System.out.println("测试结束");


        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
