package com.jikexueyuan.notes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;

import java.util.Calendar;

public class MyService extends Service {
    private Cursor cursor;
    private Calendar calendar;
    private int time, _id;
    private String event;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public MyService() {
    }

    //关机后重启时重新设定AlarmManager
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service on create");
        cursor = getContentResolver().query(MyProvider.URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); ++i) {
                _id = cursor.getInt(cursor.getColumnIndex("_id"));
                time = cursor.getInt(cursor.getColumnIndex("time"));
                event = cursor.getString(cursor.getColumnIndex("event"));
                Intent startBroadcast = new Intent(getApplicationContext(), LauncherReceiver.class);
                startBroadcast.setAction(MainActivity.ACTION);
                Bundle bundle = new Bundle();
                bundle.putInt("_id", _id);
                bundle.putInt("time", time);
                bundle.putString("event", event);
                startBroadcast.putExtras(bundle);
                PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), _id, startBroadcast, 0);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, time);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                }
                setAlarm(calendar, sender);
            }

        }
        onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 设定发送闹钟消息
     *
     * @param time
     * @param sender
     */
    public void setAlarm(Calendar time, PendingIntent sender) {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), sender);
    }

}
