package com.jikexueyuan.activitywish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txtContent;
    private TextView txtWish;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtContent = (TextView) findViewById(R.id.txtcontent);
        txtWish = (TextView) findViewById(R.id.txtWish);
        intent = new Intent(MainActivity.this,WishActivity.class);

        //为按钮设置点击事件
        findViewById(R.id.btnChangeAct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,0x11);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x11 && resultCode == 0x12) {
            Toast.makeText(this,"你取消了许愿！",Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 0x11 && resultCode == 0x13) {
            Bundle bundle = data.getExtras();
            String str = bundle.getString("wish");
            System.out.println(str);
            txtContent.setText("你许下的愿望是：");
            txtWish.setText(str);
        }
    }
}
