package com.jikexueyuan.activitywish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/6/4 0004.
 */
public class WishActivity extends AppCompatActivity {
    private EditText txtWish;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_activity);

        txtWish = (EditText) findViewById(R.id.txtWish);
        intent = getIntent();

        //分别对确定和取消按钮设置点击事件
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0x12, intent);
                finish();
            }
        });
        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("wish",txtWish.getText().toString());
                intent.putExtras(bundle);
                setResult(0x13,intent);
                txtWish.setText("");
                finish();
            }
        });
    }
}
