package com.jikexueyuan.movebutton;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AnimationSet set;
    private TranslateAnimation moveX;
    private TranslateAnimation moveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_va_xml).setOnClickListener(this);
        findViewById(R.id.btn_va_java).setOnClickListener(this);
        findViewById(R.id.btn_oa_xml).setOnClickListener(this);
        findViewById(R.id.btn_oa_java).setOnClickListener(this);
        findViewById(R.id.iv_tree).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_va_xml:
                v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
                break;
            case R.id.btn_va_java:
                set = new AnimationSet(true);
                set.setDuration(1000);
                moveX = new TranslateAnimation(0, 300,
                        0, 0);
                moveX.setDuration(1000);
                moveY = new TranslateAnimation(0, 0, 0, 300);
                moveY.setStartOffset(1000);
                moveY.setDuration(1000);
                set.addAnimation(moveX);
                set.addAnimation(moveY);
                v.startAnimation(set);
                break;
            case R.id.btn_oa_xml:
                AnimatorSet asXml = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.translate);
                asXml.setTarget(v);
                asXml.start();
                break;
            case R.id.btn_oa_java:
                AnimatorSet asJava = new AnimatorSet();
                asJava.playSequentially(ObjectAnimator.ofFloat(v, "translationX", 0, 300).setDuration(1000),
                        ObjectAnimator.ofFloat(v, "translationY", 0, 300).setDuration(1000),
                        ObjectAnimator.ofFloat(v, "translationY", 300, 0).setDuration(1000),
                        ObjectAnimator.ofFloat(v, "translationX", 300, 0).setDuration(1000));
                asJava.start();
                break;
            case R.id.iv_tree:
                AnimatorSet asImage = new AnimatorSet();
                asImage.playSequentially(ObjectAnimator.ofFloat(v, "rotationY", 0, 180, 180).setDuration(1000),
                        ObjectAnimator.ofFloat(v, "rotationY", 180, 180, 0).setDuration(1000));
                asImage.start();
                break;
        }
    }
}
