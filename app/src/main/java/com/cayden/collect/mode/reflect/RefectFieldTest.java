package com.cayden.collect.mode.reflect;

import java.lang.reflect.Field;

/**
 * Created by cuiran on 16/7/29.
 */
public class RefectFieldTest {

    public static void main(String args[]){
        Student student=new Student();
        try{
            Field property1=student.getClass().getDeclaredField("id");
            System.out.println(property1);//private
            Field property3=student.getClass().getField("nickname");
            System.out.println(property3);//public java.lang.String com.cx.test.Student.nickname

            Field property4=student.getClass().getDeclaredField("ee_1");
            System.out.println(property4);//public java.lang.String com.cx.test.Student.ee_1

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
