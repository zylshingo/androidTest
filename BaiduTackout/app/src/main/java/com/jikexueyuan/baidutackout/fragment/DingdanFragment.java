package com.jikexueyuan.baidutackout.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jikexueyuan.baidutackout.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DingdanFragment extends Fragment {


    public DingdanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.dingdan_fragment, null);
    }

}
