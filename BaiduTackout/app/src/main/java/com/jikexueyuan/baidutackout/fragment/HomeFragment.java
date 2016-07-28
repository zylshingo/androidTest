package com.jikexueyuan.baidutackout.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.jikexueyuan.baidutackout.R;
import com.jikexueyuan.baidutackout.adapter.HomeLvAdapter;
import com.jikexueyuan.baidutackout.view.MyGirdView;
import com.jikexueyuan.baidutackout.view.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private int[] mImageId = {R.drawable.eat, R.drawable.buy, R.drawable.fruit, R.drawable.tea,
            R.drawable.tuhao, R.drawable.deliver, R.drawable.newdian, R.drawable.medicine};
    private String[] mTitle = {"美食", "超市", "水果", "下午茶", "土豪", "专送", "新店", "医药"};


    public HomeFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.home_fragment, null);
        MyListView listView = (MyListView) view.findViewById(R.id.listView);
        MyGirdView gridView = (MyGirdView) view.findViewById(R.id.gridView);
        List<Map<String, Object>> lists = new ArrayList<>();
        for (int i = 0; i < mImageId.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", mImageId[i]);
            map.put("mTitle", mTitle[i]);
            lists.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), lists, R.layout.gridview_cell, new String[]{"image", "mTitle"}, new int[]{R.id.iv_gv_cell, R.id.tv_gv_cell});
        gridView.setAdapter(adapter);
        TextView lvheader = new TextView(getActivity());
        lvheader.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        lvheader.setHeight(60);
        lvheader.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        lvheader.setText("全部商家");
        lvheader.setBackgroundColor(0xffc7c4c4);
        HomeLvAdapter homeLvAdapter = new HomeLvAdapter(getContext());
        listView.addHeaderView(lvheader);
        listView.setAdapter(homeLvAdapter);
        return view;
    }

}
