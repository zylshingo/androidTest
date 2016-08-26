package com.jikexueyuan.locationshare;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class locationService extends Service {

    private static boolean running = true;
    private DataListener dataListener = null;
    private Socket client = null;
    private BufferedWriter bw = null;
    private BufferedReader br = null;

    void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }


    public locationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        running = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    client = new Socket("192.168.0.103", 12580);
                    bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    System.out.println("client is run");

                    String line = "";
                    while ((line = br.readLine()) != null) {
                        if (dataListener != null) {
                            dataListener.dataChanged(line);
                        }
                        System.out.println("receive is run");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    class MyBinder extends Binder {

        locationService getService() {
            return locationService.this;
        }
    }

    public boolean sendMsg(String data) {
        if (bw != null) {
            try {
                bw.write(data + "\n");
                bw.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
