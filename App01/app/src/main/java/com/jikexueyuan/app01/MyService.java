package com.jikexueyuan.app01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class MyService extends Service {
    private RemoteCallbackList<ICallback> callbackList = new RemoteCallbackList<>();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service is create");

        new Thread() {
            @Override
            public void run() {

                for (numIndex = 0; running; ++numIndex) {
                    int count = callbackList.beginBroadcast();
                    while (count-- > 0) {
                        try {
                            callbackList.getBroadcastItem(count).onTimer(numIndex);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }


                    callbackList.finishBroadcast();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("service is destory");
        running = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {
            @Override
            public void registCallback(ICallback callback) throws RemoteException {
                callbackList.register(callback);
            }

            @Override
            public void unregistCallback(ICallback callback) throws RemoteException {
                callbackList.unregister(callback);
            }

        };
    }

    private boolean running = true;
    private int numIndex = 0;

}
