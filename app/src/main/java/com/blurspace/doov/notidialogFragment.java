package com.blurspace.doov;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blurspace.doov.databinding.FragmentNotidialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notidialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notidialogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentNotidialogBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notidialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notidialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static notidialogFragment newInstance(String param1, String param2) {
        notidialogFragment fragment = new notidialogFragment();
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
        binding=FragmentNotidialogBinding.inflate(inflater,container,false);
        viewfunc();
        return binding.getRoot();
    }

    private void viewfunc() {
        binding.noticlose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               homefragment df=new homefragment();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
                transaction.replace(R.id.MainFragmentLayout,df);
                transaction.commit();
            }
        });
    }
}