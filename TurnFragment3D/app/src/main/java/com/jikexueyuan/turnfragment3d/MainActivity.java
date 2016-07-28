package com.jikexueyuan.turnfragment3d;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(0, R.animator.turn3d_two_enter)
                .add(R.id.fl_root, new FragmentHome())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else if (getFragmentManager().getBackStackEntryCount() < 0) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public static class FragmentHome extends Fragment {
        private View viewHome;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            viewHome = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_image, null);
            viewHome.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction()
                            .addToBackStack("Home")
                            .setCustomAnimations(R.animator.turn3d_two_enter, R.animator.turn3d_one_exit, R.animator.turn3d_one_enter, R.animator.turn3d_two_exit)
                            .replace(R.id.fl_root, new FragmentNext())
                            .commit();
                }
            });
            return viewHome;
        }


    }

    public static class FragmentNext extends Fragment {

        private View viewNext;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            viewNext = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_next_image, null);
            viewNext.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
            return viewNext;
        }
    }
}
