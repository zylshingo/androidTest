package com.jikexueyuan.radiobutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup sex;
    private RadioButton r;
    private CheckBox checkArctic;
    private CheckBox checkPacific;
    private CheckBox checkXyy;
    private CheckBox checkIndianOcean;
    private CheckBox checkSun;
    private CheckBox checkAtlantic;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUnit();

    }

    private void setUnit() {
        sex = (RadioGroup) findViewById(R.id.radioSex);
        txtResult = (TextView) findViewById(R.id.txtResult);
        checkArctic = (CheckBox) findViewById(R.id.checkArctic);
        checkAtlantic = (CheckBox) findViewById(R.id.checkAtlantic);
        checkIndianOcean = (CheckBox) findViewById(R.id.checkIndianOcean);
        checkPacific = (CheckBox) findViewById(R.id.checkPacific);
        checkSun = (CheckBox) findViewById(R.id.checkSun);
        checkXyy = (CheckBox) findViewById(R.id.checkXyy);
        findViewById(R.id.btnResult).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnResult:
                String radioRt = "";
                for (int i = 0; i < sex.getChildCount(); ++i) {
                    r = (RadioButton) sex.getChildAt(i);
                    if (r.isChecked()) {
                        radioRt = r.getText().toString();
                    }
                }
                String checkResult = "";
                if (checkXyy.isChecked() && checkSun.isChecked() && !checkPacific.isChecked() && !checkIndianOcean.isChecked()
                        && !checkArctic.isChecked() && !checkAtlantic.isChecked()) {
                    checkResult = "你的回答是正确的！";
                } else if (!checkXyy.isChecked() && !checkSun.isChecked() && !checkPacific.isChecked() && !checkIndianOcean.isChecked()
                        && !checkArctic.isChecked() && !checkAtlantic.isChecked()) {
                    checkResult = "你没有回答题目，请重新选择！";
                } else {
                    checkResult = "你的回答是错误的！";
                }

                if (radioRt.equals("")) {
                    txtResult.setText(String.format("你没有选择性别！%s", checkResult));
                } else {
                    txtResult.setText(String.format("你是一位%s性，%s", radioRt, checkResult));
                }
                break;
        }
    }
}
