package com.jikexueyuan.app2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.jikexueyuan.app01.ICallback;
import com.jikexueyuan.app01.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent intent;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        intent = new Intent();
        intent.setComponent(new ComponentName("com.jikexueyuan.app01", "com.jikexueyuan.app01.MyService"));
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                bindService(intent, this, BIND_AUTO_CREATE);
                break;
            case R.id.button2:
                unbindService(this);
                biuder = null;
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Service is bind");
        biuder = IMyAidlInterface.Stub.asInterface(service);
        try {
            biuder.registCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        try {
            biuder.unregistCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            biuder.unregistCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IMyAidlInterface biuder = null;
    private ICallback.Stub callback = new ICallback.Stub() {
        @Override
        public void onTimer(int numIndex) throws RemoteException {
            Message msg = new Message();
            msg.obj = MainActivity.this;
            msg.arg1 = numIndex;
            hander.sendMessage(msg);
        }
    };
    private final MyHander hander = new MyHander();

    private class MyHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity _this = (MainActivity) msg.obj;
            int i = msg.arg1;
            _this.textView.setText("这是回调过来的数据" + i);
        }
    }

}
