package com.blurspace.doov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class SplashScreen extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        this.getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor));
        }


        sharedPreferences= getSharedPreferences("onbdone",0);
        String onbchecker= sharedPreferences.getString("onbdone","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(onbchecker==null) {
                    startActivity(new Intent(SplashScreen.this,onboarding.class));
                    finish();
                }
                else if(onbchecker.equals("yes")){
                    startActivity(new Intent(SplashScreen.this,Loginact.class));
                    finish();
                }
                else if(onbchecker!="yes" && !onbchecker.equals(null)){
                    startActivity(new Intent(SplashScreen.this,onboarding.class));
                    finish();
                }



            }

        },2500);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }
}