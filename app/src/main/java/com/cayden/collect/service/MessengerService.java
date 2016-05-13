package com.cayden.collect.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;


/**
 * MessengerService
 * Created by cuiran on 16/5/12.
 */
public class MessengerService extends Service {
    private static final String TAG=MessengerService.class.getSimpleName();

    private static final int MSG_SUM=0x110;

    private Messenger mMessenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msgfromClient) {

            Message msgToClient = Message.obtain(msgfromClient);//返回给客户端的消息
            switch (msgfromClient.what){
                case MSG_SUM:
                    msgToClient.what=MSG_SUM;
                    try{
//                        Thread.sleep(2000);
                        msgToClient.arg2=msgfromClient.arg1+msgfromClient.arg2;
                        msgfromClient.replyTo.send(msgToClient);
                    }
//                    catch (InterruptedException e){
//
//                    }
                    catch (RemoteException e){

                    }
                    break;
            }
            super.handleMessage(msgfromClient);
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
