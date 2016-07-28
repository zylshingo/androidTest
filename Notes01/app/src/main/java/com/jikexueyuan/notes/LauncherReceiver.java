package com.jikexueyuan.notes;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class LauncherReceiver extends BroadcastReceiver {
    public LauncherReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MainActivity.ACTION)) {
            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
            if (intent.getExtras().getInt("_id") % 3 == 0) {
                notification.setSmallIcon(R.drawable.ic_launcher);
            } else if (intent.getExtras().getInt("_id") % 3 == 1) {
                notification.setSmallIcon(R.drawable.jpg01);
            } else if (intent.getExtras().getInt("_id") % 3 == 2) {
                notification.setSmallIcon(R.drawable.jpg02);
            }
            notification.setContentTitle(context.getString(R.string.remind));
            notification.setContentText(intent.getExtras().getInt("time") + context.getString(R.string.point_to)
                    + intent.getExtras().getString("event"));
            notification.setAutoCancel(true);
            notification.setDefaults(Notification.DEFAULT_ALL);
            manager.notify(intent.getExtras().getInt("_id"), notification.build());

        } else {
            Intent service = new Intent(context, MyService.class);
            context.startService(service);
        }
    }

}
