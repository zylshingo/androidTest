package com.jikexueyuan.app01;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        intent = new Intent(this, MyService.class);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent, MainActivity.this, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binder != null) {
                    unbindService(MainActivity.this);
                    binder = null;
                }
            }
        });
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = IMyAidlInterface.Stub.asInterface(service);
        try {
            binder.registCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        try {
            binder.unregistCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            binder.unregistCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IMyAidlInterface binder = null;

    private ICallback.Stub callback = new ICallback.Stub() {
        @Override
        public void onTimer(int numIndex) throws RemoteException {
            Message msg = new Message();
            msg.obj = MainActivity.this;
            msg.arg1 = numIndex;
            mangler.sendMessage(msg);
        }
    };

    private Mangler mangler = new Mangler();

    private static class Mangler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity _this = (MainActivity) msg.obj;
            int i = msg.arg1;
            _this.textView.setText(String.format("服务传回的数据：%d", i));
        }
    }

}
