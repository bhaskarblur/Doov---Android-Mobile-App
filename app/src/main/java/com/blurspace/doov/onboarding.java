package com.blurspace.doov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blurspace.doov.databinding.ActivityOnboardingBinding;


public class onboarding extends AppCompatActivity  {

    ActivityOnboardingBinding onbinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onbinding=ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(onbinding.getRoot());

        this.getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor));
        }


        onbinding.viewpager.setAdapter(new MyonboardingAdapter(getSupportFragmentManager(),1));
        onboardingFragment onboardingFragment=new onboardingFragment();

        onbinding.onbprog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onbinding.viewpager.setCurrentItem(0,true);
                onboardingFragment.setCurrentPos(1);
            }
        });
        onbinding.onbprog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onbinding.viewpager.setCurrentItem(1,true);
                onboardingFragment.setCurrentPos(2);
            }
        });
        onbinding.onbprog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onbinding.viewpager.setCurrentItem(2,true);
                onboardingFragment.setCurrentPos(3);
            }
        });
        onbinding.nexttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onboardingFragment.getCurrentPos()==1 && !onbinding.nexttxt.getText().equals("Continue")) {
                    onbinding.viewpager.setCurrentItem(1,true);
                    onboardingFragment.setCurrentPos(2);
                }
                else if(onboardingFragment.getCurrentPos()==2 && !onbinding.nexttxt.getText().equals("Continue")) {
                    onbinding.viewpager.setCurrentItem(2,true);
                    onboardingFragment.setCurrentPos(3);
                }
                else if(onbinding.nexttxt.getText().equals("Continue")) {
                    SharedPreferences sharedPreferences= getSharedPreferences("onbdone",0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("onbdone","yes");
                    editor.commit();
                   startActivity(new Intent(onboarding.this,Loginact.class));
                   finish();


                }
            }

        });

        onbinding.skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= getSharedPreferences("onbdone",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("onbdone","yes");
                editor.commit();
                startActivity(new Intent(onboarding.this,Loginact.class));
                finish();
            }
        });



        onbinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0) {
                    onboardingFragment.setCurrentPos(1);
                    onbinding.nexttxt.setText("Next");
                    onbinding.skipbtn.setVisibility(View.VISIBLE);
                    onbinding.onbprog1.getBackground().setTint(Color.parseColor("#ffd107"));
                    onbinding.onbprog2.getBackground().setTint(Color.parseColor("#3D3D3D"));
                    onbinding.onbprog2.getBackground().setTint(Color.parseColor("#3D3D3D"));

                }
                else if(position==1) {
                    onboardingFragment.setCurrentPos(2);
                    onbinding.nexttxt.setText("Next");
                    onbinding.skipbtn.setVisibility(View.VISIBLE);
                    onbinding.onbprog1.getBackground().setTint(Color.parseColor("#3D3D3D"));
                    onbinding.onbprog2.getBackground().setTint(Color.parseColor("#ffd107"));
                    onbinding.onbprog3.getBackground().setTint(Color.parseColor("#3D3D3D"));
                }
                else if(position==2) {
                    onboardingFragment.setCurrentPos(3);
                    onbinding.nexttxt.setText("Continue");
                    onbinding.skipbtn.setVisibility(View.INVISIBLE);
                    onbinding.onbprog1.getBackground().setTint(Color.parseColor("#3D3D3D"));
                    onbinding.onbprog2.getBackground().setTint(Color.parseColor("#3D3D3D"));
                    onbinding.onbprog3.getBackground().setTint(Color.parseColor("#ffd107"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }
}
