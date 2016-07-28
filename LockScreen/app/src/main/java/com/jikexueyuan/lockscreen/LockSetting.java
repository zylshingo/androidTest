package com.jikexueyuan.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LockSetting extends AppCompatActivity implements View.OnClickListener {

    private DevicePolicyManager manager;
    private Button btn_reg;
    private Button btn_unreg;
    private Button btn_lock;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setting);

        initUI();
        addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regAdmin:
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                startActivity(intent);
                break;
            case R.id.btn_unregAdmin:
                manager.removeActiveAdmin(componentName);
                showBtnReg();
                break;
            case R.id.btn_lockScreen:
                manager.lockNow();
        }
    }

    private void initUI() {
        btn_reg = (Button) findViewById(R.id.btn_regAdmin);
        btn_unreg = (Button) findViewById(R.id.btn_unregAdmin);
        btn_lock = (Button) findViewById(R.id.btn_lockScreen);
        manager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, DeviceAdminBC.class);
    }

    private void addListener() {
        btn_reg.setOnClickListener(this);
        btn_unreg.setOnClickListener(this);
        btn_lock.setOnClickListener(this);
    }

    private void showBtnReg() {
        btn_unreg.setVisibility(View.GONE);
        btn_reg.setVisibility(View.VISIBLE);
        btn_lock.setVisibility(View.GONE);
    }

    private void showBtnUnreg() {
        btn_unreg.setVisibility(View.VISIBLE);
        btn_lock.setVisibility(View.VISIBLE);
        btn_reg.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean admin = manager.isAdminActive(componentName);
        if (admin) {
            showBtnUnreg();
        } else {
            showBtnReg();
        }
    }
}
