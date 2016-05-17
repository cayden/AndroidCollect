package com.cayden.collect.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cuiran on 16/5/17.
 */
public class Person implements Parcelable {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 序列化实体类
     */
    public static final Parcelable.Creator<Person> CREATOR = new Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            Person personPar = new Person();
            personPar.name = source.readString();
            personPar.age = source.readInt();
            return personPar;
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将实体类数据写入Parcel
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(age);
    }
}
