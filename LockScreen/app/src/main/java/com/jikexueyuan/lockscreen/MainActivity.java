package com.jikexueyuan.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        boolean admin = devicePolicyManager.isAdminActive(new ComponentName(this,DeviceAdminBC.class));
        if (admin) {
            devicePolicyManager.lockNow();
            finish();
        } else {
            Intent intent = new Intent(this,LockSetting.class);
            startActivity(intent);
            finish();
        }
    }
}
