package com.cayden.collect.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by cuiran on 16/5/17.
 */
public class AIDLServerService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private IAIDLServerService.Stub mBinder= new IAIDLServerService.Stub() {
        @Override
        public Person getPerson() throws RemoteException {
            Person person=new Person();
            person.setName("cayden");
            person.setAge(5);
            return person;
        }
    };

}
