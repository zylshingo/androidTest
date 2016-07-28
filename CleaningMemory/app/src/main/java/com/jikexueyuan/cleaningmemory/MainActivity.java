package com.jikexueyuan.cleaningmemory;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityManager mSystemService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSystemService = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> mRunningApp = mSystemService.getRunningAppProcesses();
        //获取清理之前的内存
        double beforeMem = getMemory();
        //判断运行中的程序是否可以被Kill
        for (ActivityManager.RunningAppProcessInfo i : mRunningApp) {
            if (i.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                String[] pkgLists = i.pkgList;
                for (String pkgKill : pkgLists) {
                    mSystemService.killBackgroundProcesses(pkgKill);
                }
            }
        }
        //获取清理之后的内存
        double afterMem = getMemory();
        //弹出清理消息
        Toast.makeText(this, String.format(getString(R.string.qingli), afterMem - beforeMem),Toast.LENGTH_SHORT).show();
        finish();
    }

    private double getMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        mSystemService.getMemoryInfo(memoryInfo);
        return (double) memoryInfo.availMem / (1024 * 1024);
    }
}
