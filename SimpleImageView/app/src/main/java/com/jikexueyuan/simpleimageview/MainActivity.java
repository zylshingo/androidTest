package com.jikexueyuan.simpleimageview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //点击按钮后  获得保存文件的Uri 并选择查看图片的应用
        findViewById(R.id.btnOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setDataAndType(getUri(), "image/*");
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
    }


    public Uri getUri() {
        String fileRoot = getString(R.string.fileRoot);
        String fileName = getString(R.string.fileName);
        File file = new File(fileRoot + fileName);
        //判断是否存在文件
        if (!file.exists()) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                //获得资源文件的输入流
                is = MainActivity.this.getResources().openRawResource(+R.mipmap.jpg01);
                //定义保存文件的输出流
                fos = new FileOutputStream(file);
                byte[] buffer = new byte[8192];
                int count = 0;
                //开始将内容保存到文件
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return Uri.fromFile(file);
        }
        return Uri.fromFile(file);
    }
}
