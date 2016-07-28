package com.jikexueyuan.baidutackout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.jikexueyuan.baidutackout.fragment.DingdanFragment;
import com.jikexueyuan.baidutackout.fragment.HomeFragment;
import com.jikexueyuan.baidutackout.fragment.MeFragment;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class GuideFragmentAdapter extends FragmentPagerAdapter {

    private Fragment[] fragment = {new HomeFragment(), new DingdanFragment(), new MeFragment()};

    public GuideFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment[position];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }
}
