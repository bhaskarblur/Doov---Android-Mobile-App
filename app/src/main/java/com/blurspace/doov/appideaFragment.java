package com.blurspace.doov;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blurspace.doov.databinding.FragmentAppideaBinding;


public class appideaFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAppideaBinding aibinding;

    private String mParam1;
    private String mParam2;

    public appideaFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static appideaFragment newInstance(String param1, String param2) {
        appideaFragment fragment = new appideaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        aibinding=FragmentAppideaBinding.inflate(inflater,container,false);

        aibinding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilefragment pf = new profilefragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
                transaction.replace(R.id.MainFragmentLayout, pf);
                transaction.addToBackStack("B");
                transaction.commit();
            }
        });
        return aibinding.getRoot();
    }
}