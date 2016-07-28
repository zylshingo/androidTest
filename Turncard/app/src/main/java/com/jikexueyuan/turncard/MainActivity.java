package com.jikexueyuan.turncard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageA;
    private ImageView imageB;
    private ScaleAnimation sa_to_0;
    private ScaleAnimation sa_to_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        addListener();
    }

    private void initUI() {
        imageA = (ImageView) findViewById(R.id.iv_imageA);
        imageB = (ImageView) findViewById(R.id.iv_imageB);
        showImageA();
        sa_to_0 = new ScaleAnimation(1, 0, 1, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        sa_to_1 = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        sa_to_0.setDuration(1000);
        sa_to_1.setDuration(1000);

    }

    private void addListener() {
        findViewById(R.id.fl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageA.getVisibility() == View.VISIBLE) {
                    imageA.startAnimation(sa_to_0);
                } else {
                    imageB.startAnimation(sa_to_0);
                }
            }
        });

        sa_to_0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (imageA.getVisibility() == View.VISIBLE) {
                    showImageB();
                    imageB.startAnimation(sa_to_1);
                } else {
                    showImageA();
                    imageA.startAnimation(sa_to_1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void showImageA() {
        imageA.setVisibility(View.VISIBLE);
        imageB.setVisibility(View.INVISIBLE);
    }

    private void showImageB() {
        imageA.setVisibility(View.INVISIBLE);
        imageB.setVisibility(View.VISIBLE);
    }


}
