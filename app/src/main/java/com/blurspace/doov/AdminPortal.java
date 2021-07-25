package com.blurspace.doov;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blurspace.doov.Adapters.AdminDreamAdapter;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.blurspace.doov.customdialogues.dreamadbottomdialog;
import com.blurspace.doov.customdialogues.nocondialog;
import com.blurspace.doov.customdialogues.passresetdialog;
import com.blurspace.doov.databinding.ActivityAdminPortalBinding;

import java.util.List;

public class AdminPortal extends AppCompatActivity {
    ActivityAdminPortalBinding apbinding;
    private AdminPortalViewModel adviewmodel;
    public AdminDreamAdapter adadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apbinding=ActivityAdminPortalBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(apbinding.getRoot());

        this.getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    com.blurspace.doov.customdialogues.nocondialog nocondialog= new nocondialog();
                    nocondialog.show(getSupportFragmentManager(),"nocondialog");
                    nocondialog.setCancelable(false);
                }
            },20);
        }

        adviewmodel= new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(AdminPortalViewModel.class);
        adviewmodel.initwork(this);
        adviewmodel.getdreamModel().observe(this, new Observer<List<DreamsModel>>() {
            @Override
            public void onChanged(List<DreamsModel> dreamsModels) {
                if(adviewmodel.getdreamModel().getValue().size()>0) {
                    apbinding.admindreamshimmer.setVisibility(View.VISIBLE);
                }

                adadapter.notifyDataSetChanged();
            }
        });

        if(adviewmodel.getdreamModel().getValue().size()<2 && adviewmodel.getdreamModel().getValue().size()>0) {
            apbinding.shimmerdreamsublay2.getRoot().setVisibility(View.INVISIBLE);
        }
        loadDreamList();

        viewfunctions();


    }

    private void viewfunctions() {
        apbinding.adminPreviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPortal.this,MainArea.class));
            }
        });

        apbinding.admindreamadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgurl","none");
                bundle.putString("name","none");
                bundle.putInt("position",-1);
                bundle.putString("field","none");
                com.blurspace.doov.customdialogues.dreamadbottomdialog dreamadbottomdialog=new dreamadbottomdialog();
                dreamadbottomdialog.setArguments(bundle);
                dreamadbottomdialog.show(getSupportFragmentManager(),"dreamadbottomdialog");
            }
        });



    }

    private void loadDreamList() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                apbinding.admindreamshimmer.stopShimmer();
                apbinding.admindreamshimmer.hideShimmer();
                apbinding.admindreamshimmer.setVisibility(View.INVISIBLE);
                apbinding.adminDreamlist.setVisibility(View.VISIBLE);
            }
        },3000);



        adadapter= new AdminDreamAdapter(AdminPortal.this,adviewmodel.getdreamModel().getValue());
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.HORIZONTAL);
        apbinding.adminDreamlist.setLayoutManager(llm);
        apbinding.adminDreamlist.setAdapter(adadapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finish() {
        super.finish();
        this.getViewModelStore().clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.getViewModelStore().clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.getViewModelStore().clear();
    }
}