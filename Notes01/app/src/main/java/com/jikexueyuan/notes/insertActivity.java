package com.jikexueyuan.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class insertActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText etTime;
    private EditText etEvent;
    private boolean isexist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
        }
        setSupportActionBar(toolbar);

        findViewById(R.id.btnInsert).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        etTime = (EditText) findViewById(R.id.etInsertTime);
        etEvent = (EditText) findViewById(R.id.etInsertEvent);
        String event = etEvent.getText().toString();
        Cursor cursor = getContentResolver().query(MyProvider.URI, null, "time", null, null);

        //去除无效的空格
        if (!TextUtils.isEmpty(event) && !TextUtils.isEmpty(etTime.getText().toString())) {
            char[] str = event.toCharArray();
            for (int i = 0; i < str.length; ++i) {
                if (str[i] == ' ') {
                    event = event.substring(i);
                    break;
                }
            }
        }
        //判断是否存在相同时刻
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); ++i) {
                int timedata = cursor.getInt(cursor.getColumnIndex("time"));
                if (etTime.getText().toString().equals(String.valueOf(timedata))) {
                    Toast.makeText(getApplicationContext(), R.string.isevnet, Toast.LENGTH_SHORT).show();
                    etTime.setText("");
                    etEvent.setText("");
                    isexist = true;
                } else {
                    cursor.moveToNext();
                    isexist = false;
                }
            }
        }

        if (!isexist) {
            //判断是否输入了时间和事件
            if (TextUtils.isEmpty(etTime.getText().toString()) || TextUtils.isEmpty(event)) {
                Toast.makeText(getApplicationContext(), R.string.wanzhen, Toast.LENGTH_SHORT).show();
            }
            //判断时间输入是否合法
            else if ((Integer.parseInt(etTime.getText().toString())) < 0 || (Integer.parseInt(etTime.getText().toString())) > 23) {
                Toast.makeText(getApplicationContext(), R.string.buhefa, Toast.LENGTH_SHORT).show();
                etTime.setText("");
            } else {
                ContentValues values = new ContentValues();
                values.put("time", Integer.parseInt(etTime.getText().toString()));
                values.put("event", event);
                getContentResolver().insert(MyProvider.URI, values);
                Cursor cursor_id = getContentResolver().query(MyProvider.URI, null, null, null, null);
                if (cursor_id != null) {
                    cursor_id.moveToFirst();
                    for (int i = 0; i < cursor_id.getCount(); ++i) {
                        if (cursor_id.getInt(cursor_id.getColumnIndex("time")) == Integer.parseInt(etTime.getText().toString())) {
                            break;
                        }
                        cursor_id.moveToNext();
                    }
                }
                int _id = cursor_id.getInt(cursor_id.getColumnIndex("_id"));
                Toast.makeText(getApplicationContext(), R.string.add_ok, Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putInt("_id", _id);
                bundle.putInt("time", Integer.parseInt(etTime.getText().toString()));
                bundle.putString("event", etEvent.getText().toString());
                intent.putExtras(bundle);
                setResult(0x12, intent);
                etTime.setText("");
                etEvent.setText("");
                finish();
            }
        }
    }
}


