package com.blurspace.doov;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blurspace.doov.Models.CurrentDreamModel;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.ViewModels.HomeViewModel;
import com.blurspace.doov.ViewModels.dreamViewModel;
import com.blurspace.doov.databinding.FragmentDreamfragmentBinding;
import com.blurspace.doov.Adapters.crrdreamAdapter;
import com.blurspace.doov.Adapters.prevdreamAdapter;
import com.blurspace.doov.Adapters.favdreamAdapter;

import java.util.ArrayList;
import java.util.List;


public class dreamfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentDreamfragmentBinding  dfbinding;
    private crrdreamAdapter crrdreamAdapter;
    private prevdreamAdapter prevdreamAdapter;
    private favdreamAdapter favdreamAdapter;
    private com.blurspace.doov.ViewModels.dreamViewModel dreamViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<CurrentDreamModel.curdreammodel> Curdreamlist=new ArrayList<>();
    public dreamfragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static dreamfragment newInstance(String param1, String param2) {
        dreamfragment fragment = new dreamfragment();
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
        dreamViewModel= new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getActivity().getApplication())).get(com.blurspace.doov.ViewModels.dreamViewModel.class);
        dreamViewModel.initwork(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dfbinding=FragmentDreamfragmentBinding.inflate(inflater,container,false);

        dreamViewModel.getCurrDreamModel().observe(getActivity(), new Observer<CurrentDreamModel.curdreammodel>() {
            @Override
            public void onChanged(CurrentDreamModel.curdreammodel dreamViewModels) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(dreamViewModels!=null) {
                            dfbinding.nocurtxt.setVisibility(View.INVISIBLE);
                            Curdreamlist.add(dreamViewModels);
                        }
                        else {
                            dfbinding.nocurtxt.setVisibility(View.VISIBLE);
                        }
                        crrdreamAdapter.notifyDataSetChanged();
                    }
                },100);
            }
        });
        dreamViewModel.getPrevDreamModel().observe(getActivity(), new Observer<List<DreamsModel>>() {
            @Override
            public void onChanged(List<DreamsModel> dreamViewModels) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(dreamViewModels.size()==0) {
                            dfbinding.noprvtxt.setVisibility(View.VISIBLE);
                        }
                        else if(dreamViewModels.size()>1) {
                            dfbinding.noprvtxt.setVisibility(View.INVISIBLE);
                        }
                        prevdreamAdapter.notifyDataSetChanged();

                    }
                },100);
            }
        });
        dreamViewModel.getFavDreamModel().observe(getActivity(), new Observer<List<DreamsModel>>() {
            @Override
            public void onChanged(List<DreamsModel> dreamViewModels) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(dreamViewModels.size()==0) {
                            dfbinding.nofavtxt.setVisibility(View.VISIBLE);
                        }
                        else if(dreamViewModels.size()>1) {
                            dfbinding.nofavtxt.setVisibility(View.INVISIBLE);
                        }
                        favdreamAdapter.notifyDataSetChanged();
                    }
                },100);
            }
        });
        viewfunc();
        loadrecsData();
        return dfbinding.getRoot();
    }

    private void loadrecsData() {

        crrdreamAdapter=new crrdreamAdapter(getContext(),Curdreamlist);
        prevdreamAdapter=new prevdreamAdapter(getContext(),dreamViewModel.getPrevDreamModel().getValue());
        favdreamAdapter=new favdreamAdapter(getContext(),dreamViewModel.getFavDreamModel().getValue());

        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        LinearLayoutManager llm1=new LinearLayoutManager(getContext());
        LinearLayoutManager llm2=new LinearLayoutManager(getContext());

        dfbinding.favdreamrec.setLayoutManager(llm2);
        dfbinding.favdreamrec.setAdapter(favdreamAdapter);

        dfbinding.currdreamrec.setLayoutManager(llm1);
        dfbinding.currdreamrec.setAdapter(crrdreamAdapter);

        dfbinding.prevdreamrec.setLayoutManager(llm);
        dfbinding.prevdreamrec.setAdapter(prevdreamAdapter);
    }

    private void viewfunc() {
    }
}