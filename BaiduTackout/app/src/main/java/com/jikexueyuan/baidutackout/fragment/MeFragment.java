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
public class MeFragment extends Fragment {

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.me_fragment, null);
        return view;
    }

}
