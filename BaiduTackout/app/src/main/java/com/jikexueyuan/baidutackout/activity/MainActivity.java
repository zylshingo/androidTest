package com.jikexueyuan.baidutackout.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import com.jikexueyuan.baidutackout.R;
import com.jikexueyuan.baidutackout.adapter.GuideFragmentAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int color_red = 0xffff4081;
    private static final int color_black = 0xff000000;
    private static final int TAB_home = 0;
    private static final int TAB_dingdan = 1;
    private static final int TAB_me = 2;
    private RadioButton rb_home, rb_dingdan, rb_me;
    private Drawable da_home_on, da_dingdan_on, da_me_on;
    private Drawable da_home_off, da_dingdan_off, da_me_off;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showUI();
        addListener();

    }

    private void showUI() {
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_dingdan = (RadioButton) findViewById(R.id.rb_dingdan);
        rb_me = (RadioButton) findViewById(R.id.rb_me);

        //获得所有需要的drawable对象 并设置好属性 方便点击时候调用
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int dpi = metrics.densityDpi;
        //根据DPi设置导航栏图标大小
        if (dpi > 240) {
            da_home_on = ContextCompat.getDrawable(this, R.drawable.tab_icon_home_selected);
            if (da_home_on != null) {
                da_home_on.setBounds(0, 0, 90, 90);
                rb_home.setCompoundDrawables(null, da_home_on, null, null);
                rb_home.setTextColor(color_red);
            }
            da_home_off = ContextCompat.getDrawable(this, R.drawable.tab_icon_home);
            da_home_off.setBounds(0, 0, 90, 90);

            da_dingdan_off = ContextCompat.getDrawable(this, R.drawable.tab_icon_dingdan);
            if (da_dingdan_off != null) {
                da_dingdan_off.setBounds(0, 0, 90, 90);
                rb_dingdan.setCompoundDrawables(null, da_dingdan_off, null, null);
            }
            da_dingdan_on = ContextCompat.getDrawable(this, R.drawable.tab_icon_dingdan_selected);
            da_dingdan_on.setBounds(0, 0, 90, 90);

            da_me_off = ContextCompat.getDrawable(this, R.drawable.tab_icon_me);
            if (da_me_off != null) {
                da_me_off.setBounds(0, 0, 90, 90);
                rb_me.setCompoundDrawables(null, da_me_off, null, null);
            }
            da_me_on = ContextCompat.getDrawable(this, R.drawable.tab_icon_me_selected);
            da_me_on.setBounds(0, 0, 90, 90);

        } else {
            da_home_on = ContextCompat.getDrawable(this, R.drawable.tab_icon_home_selected);
            if (da_home_on != null) {
                da_home_on.setBounds(0, 0, 50, 50);
                rb_home.setCompoundDrawables(null, da_home_on, null, null);
                rb_home.setTextColor(color_red);
            }
            da_home_off = ContextCompat.getDrawable(this, R.drawable.tab_icon_home);
            da_home_off.setBounds(0, 0, 50, 50);

            da_dingdan_off = ContextCompat.getDrawable(this, R.drawable.tab_icon_dingdan);
            if (da_dingdan_off != null) {
                da_dingdan_off.setBounds(0, 0, 50, 50);
                rb_dingdan.setCompoundDrawables(null, da_dingdan_off, null, null);
            }
            da_dingdan_on = ContextCompat.getDrawable(this, R.drawable.tab_icon_dingdan_selected);
            da_dingdan_on.setBounds(0, 0, 50, 50);

            da_me_off = ContextCompat.getDrawable(this, R.drawable.tab_icon_me);
            if (da_me_off != null) {
                da_me_off.setBounds(0, 0, 50, 50);
                rb_me.setCompoundDrawables(null, da_me_off, null, null);
            }
            da_me_on = ContextCompat.getDrawable(this, R.drawable.tab_icon_me_selected);
            da_me_on.setBounds(0, 0, 50, 50);
        }

        //为下方导航栏设置点击事件
        rb_home.setOnClickListener(this);
        rb_dingdan.setOnClickListener(this);
        rb_me.setOnClickListener(this);

        //为viewPager添加适配器
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        GuideFragmentAdapter adapter = new GuideFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void addListener() {
        //为viewPager添加页面改变事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TAB_home:
                        re_home_on();
                        re_dingdan_off();
                        re_me_off();
                        break;
                    case TAB_dingdan:
                        re_home_off();
                        re_dingdan_on();
                        re_me_off();
                        break;
                    case TAB_me:
                        re_home_off();
                        re_dingdan_off();
                        re_me_on();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void re_home_on() {
        rb_home.setCompoundDrawables(null, da_home_on, null, null);
        rb_home.setTextColor(color_red);
    }

    private void re_home_off() {
        rb_home.setCompoundDrawables(null, da_home_off, null, null);
        rb_home.setTextColor(color_black);
    }

    private void re_dingdan_on() {
        rb_dingdan.setCompoundDrawables(null, da_dingdan_on, null, null);
        rb_dingdan.setTextColor(color_red);
    }

    private void re_dingdan_off() {
        rb_dingdan.setCompoundDrawables(null, da_dingdan_off, null, null);
        rb_dingdan.setTextColor(color_black);
    }

    private void re_me_on() {
        rb_me.setCompoundDrawables(null, da_me_on, null, null);
        rb_me.setTextColor(color_red);
    }

    private void re_me_off() {
        rb_me.setCompoundDrawables(null, da_me_off, null, null);
        rb_me.setTextColor(color_black);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home:
                viewPager.setCurrentItem(TAB_home);
                break;
            case R.id.rb_dingdan:
                viewPager.setCurrentItem(TAB_dingdan);
                break;
            case R.id.rb_me:
                viewPager.setCurrentItem(TAB_me);
                break;
            default:
                break;
        }
    }
}
