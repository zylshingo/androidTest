package com.jikexueyuan.notes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private Intent intent;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private Cursor cursor;
    private Toolbar toolbar;
    public static final String ACTION = "Alarm";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item01:
                intent = new Intent(MainActivity.this, insertActivity.class);
                startActivityForResult(intent, 0x11);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 从另一个Activity返回结果并刷新界面
     * @param requestCode   请求码
     * @param resultCode    结果码
     * @param data          传回的Intent
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x12) {
            reFresh();
            if (data.getExtras() != null) {
                Intent startBroadcast = new Intent(getApplicationContext(), LauncherReceiver.class);
                startBroadcast.setAction(ACTION);
                Bundle alarmdata = data.getExtras();
                startBroadcast.putExtras(alarmdata);
                PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), data.getExtras().getInt("_id"), startBroadcast, 0);
                Calendar time = Calendar.getInstance();
                time.set(Calendar.HOUR_OF_DAY, data.getExtras().getInt("time"));
                time.set(Calendar.MINUTE, 0);
                time.set(Calendar.SECOND, 0);
                if (System.currentTimeMillis() > time.getTimeInMillis()) {
                    time.set(Calendar.DAY_OF_MONTH, time.get(Calendar.DAY_OF_MONTH) + 1);
                }
                setAlarm(time, sender);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showUI();


    }


    @Override
    protected void onResume() {
        reFresh();
        super.onResume();
    }

    /**
     * 设定ListView中项目的长按删除事件
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title)).setMessage(getString(R.string.dialog_msg))
                .setNegativeButton(getString(R.string.no), null)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor c = adapter.getCursor();
                        c.moveToPosition(position);
                        int itemId = c.getInt(c.getColumnIndex("_id"));
                        getContentResolver().delete(MyProvider.URI, "_id=?", new String[]{itemId + ""});
                        reFresh();
                        Intent cancel = new Intent(MainActivity.this, LauncherReceiver.class);
                        cancel.setAction(MainActivity.ACTION);
                        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, itemId, cancel, 0);
                        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        manager.cancel(sender);
                        dialog.dismiss();
                    }
                }).show();
        return true;
    }

    /**
     * 初始化界面UI
     */

    public void showUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
        }
        setSupportActionBar(toolbar);

        cursor = getContentResolver().query(MyProvider.URI, null, null, null, null);
        adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_items, cursor, new String[]{"time", "event"}, new int[]{R.id.etTime, R.id.etEvent}, 0);
        listView = (ListView) findViewById(R.id.lvContent);
        if (listView != null) {
            listView.setAdapter(adapter);
            listView.setOnItemLongClickListener(this);
        }
        intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
    }

    /**
     * 刷新ListView数据
     */
    public void reFresh() {
        cursor = getContentResolver().query(MyProvider.URI, null, null, null, null);
        adapter.changeCursor(cursor);
    }

    /**
     * 设定发送闹钟消息
     *
     * @param time
     * @param sender
     */
    public void setAlarm(Calendar time, PendingIntent sender) {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), sender);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 5000, sender);
    }

}
