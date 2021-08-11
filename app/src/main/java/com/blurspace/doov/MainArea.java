package com.blurspace.doov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blurspace.doov.customdialogues.nocondialog;
import com.blurspace.doov.databinding.ActivityAdminPortalBinding;
import com.blurspace.doov.databinding.ActivityMainAreaBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class MainArea extends AppCompatActivity {
    private ActivityMainAreaBinding ambinding;
    private Integer backclicks=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ambinding= ActivityMainAreaBinding.inflate(getLayoutInflater());
        setContentView(ambinding.getRoot());

        this.getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor));
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
            },0);


        }

        bottomnavworker();

    }
    private MainArea instance;

    public MainArea getinstance() {
        if(instance==null) {
            instance=new MainArea();
        }
        return instance;
    }

    private void bottomnavworker() {
        ambinding.dreamnotselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ambinding.dreamselectedlay.setVisibility(View.VISIBLE);
                ambinding.homenotselected2.setVisibility(View.VISIBLE);
                ambinding.profilenotselected2.setVisibility(View.VISIBLE);

                ambinding.dreamnotselected.setVisibility(View.INVISIBLE);
                ambinding.homeselectedlay.setVisibility(View.INVISIBLE);
                ambinding.homenotselected.setVisibility(View.INVISIBLE);
                ambinding.profileselectedlay.setVisibility(View.INVISIBLE);
                ambinding.profilenotselected.setVisibility(View.INVISIBLE);

                dreamfragment df=new dreamfragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_fast_2,R.anim.fade);
                transaction.replace(R.id.MainFragmentLayout,df);
                transaction.commit();

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
                    },0);


                }
            }
        });

        ambinding.profilenotselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ambinding.profileselectedlay.setVisibility(View.VISIBLE);
                ambinding.homenotselected.setVisibility(View.VISIBLE);
                ambinding.dreamnotselected2.setVisibility(View.VISIBLE);

                ambinding.profilenotselected.setVisibility(View.INVISIBLE);
                ambinding.dreamselectedlay.setVisibility(View.INVISIBLE);
                ambinding.homeselectedlay.setVisibility(View.INVISIBLE);
                ambinding.dreamselectedlay.setVisibility(View.INVISIBLE);
                ambinding.dreamnotselected.setVisibility(View.INVISIBLE);

                profilefragment pf=new profilefragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_fast_2,R.anim.fade);
                transaction.replace(R.id.MainFragmentLayout,pf);
                transaction.commit();

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
                    },0);


                }
            }
        });
        ambinding.profilenotselected2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ambinding.profileselectedlay.setVisibility(View.VISIBLE);
                ambinding.homenotselected.setVisibility(View.VISIBLE);
                ambinding.dreamnotselected2.setVisibility(View.VISIBLE);


                ambinding.profilenotselected2.setVisibility(View.INVISIBLE);
                ambinding.homeselectedlay.setVisibility(View.INVISIBLE);
                ambinding.homenotselected.setVisibility(View.INVISIBLE);
                ambinding.dreamselectedlay.setVisibility(View.INVISIBLE);
                ambinding.dreamnotselected.setVisibility(View.INVISIBLE);

                profilefragment pf=new profilefragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_fast_2,R.anim.fade);
                transaction.replace(R.id.MainFragmentLayout,pf);
                transaction.commit();

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
                    },0);


                }
            }
        });
        ambinding.homenotselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ambinding.homeselectedlay.setVisibility(View.VISIBLE);
                ambinding.dreamnotselected.setVisibility(View.VISIBLE);
                ambinding.profilenotselected.setVisibility(View.VISIBLE);

                ambinding.profileselectedlay.setVisibility(View.INVISIBLE);
                ambinding.homenotselected.setVisibility(View.INVISIBLE);
                ambinding.dreamnotselected2.setVisibility(View.INVISIBLE);
                ambinding.profilenotselected2.setVisibility(View.INVISIBLE);
                ambinding.dreamselectedlay.setVisibility(View.INVISIBLE);

                homefragment hm=new homefragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_fast_2,R.anim.fade);
                transaction.replace(R.id.MainFragmentLayout,hm);
                transaction.commit();

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
                    },0);


                }

            }
        });


        ambinding.dreamnotselected2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ambinding.dreamselectedlay.setVisibility(View.VISIBLE);
                ambinding.homenotselected2.setVisibility(View.VISIBLE);
                ambinding.profilenotselected2.setVisibility(View.VISIBLE);

                ambinding.dreamnotselected2.setVisibility(View.INVISIBLE);
                ambinding.profileselectedlay.setVisibility(View.INVISIBLE);
                ambinding.homenotselected.setVisibility(View.INVISIBLE);
                ambinding.dreamnotselected2.setVisibility(View.INVISIBLE);

                dreamfragment df=new dreamfragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_fast_2,R.anim.fade);
                transaction.replace(R.id.MainFragmentLayout,df);
                transaction.commit();

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
                    },0);


                }
            }
        });

        ambinding.homenotselected2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ambinding.homeselectedlay.setVisibility(View.VISIBLE);
                ambinding.profilenotselected.setVisibility(View.VISIBLE);
                ambinding.dreamnotselected.setVisibility(View.VISIBLE);


                ambinding.profileselectedlay.setVisibility(View.INVISIBLE);
                ambinding.dreamnotselected2.setVisibility(View.INVISIBLE);
                ambinding.homenotselected2.setVisibility(View.INVISIBLE);
                ambinding.profilenotselected2.setVisibility(View.INVISIBLE);
                ambinding.dreamselectedlay.setVisibility(View.INVISIBLE);

                homefragment hm=new homefragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_fast_2,R.anim.fade);
                transaction.replace(R.id.MainFragmentLayout,hm);
                transaction.commit();
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
                    },0);


                }
            }
        });
    }


    @Override
    public void onBackPressed() {
       backclicks++;
       if(backclicks==1) {
           Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   backclicks=0;
               }
           },2500);
       }
       if(backclicks>=2) {
           finish();
       }
    }
}