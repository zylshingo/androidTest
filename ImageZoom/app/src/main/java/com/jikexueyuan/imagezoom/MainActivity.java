package com.jikexueyuan.imagezoom;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv_Title;
    //初始化两点之间的距离为负
    private float lastDistance = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIv_Title = (ImageView) findViewById(R.id.iv_title);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = MotionEventCompat.getActionMasked(event);
        float currentDistance;
        switch (eventAction) {
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() > 1) {
                    float offsetX = event.getX(0) - event.getX(1);
                    float offsetY = event.getY(0) - event.getY(1);
                    //用求平方根函数得到当前两点之间的距离
                    currentDistance = (float) Math.sqrt(offsetX * offsetX + offsetY * offsetY);

                    if (lastDistance < 0) {
                        lastDistance = currentDistance;
                    } else {
                        if (lastDistance - currentDistance < 6) {
                            zoomInOut(1.1f);
                            lastDistance = currentDistance;
                        } else if (lastDistance - currentDistance > 6) {
                            zoomInOut(0.9f);
                            lastDistance = currentDistance;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                lastDistance = -1;
                break;
            default:
                break;
        }
        return true;
    }

    private void zoomInOut(float f) {
        FrameLayout.LayoutParams iv = (FrameLayout.LayoutParams) mIv_Title.getLayoutParams();
        iv.width = (int) (f * mIv_Title.getWidth());
        iv.height = (int) (f * mIv_Title.getHeight());
        mIv_Title.setLayoutParams(iv);
    }
}
