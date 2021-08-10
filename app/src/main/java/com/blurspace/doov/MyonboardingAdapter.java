package com.blurspace.doov;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyonboardingAdapter extends FragmentPagerAdapter {

    onboardingFragment page1,page2,page3;
    public MyonboardingAdapter( FragmentManager fm, int behavior) {
        super(fm, behavior);
        page1=new onboardingFragment();
        page1.setCurrentPos(1);
        page2=new onboardingFragment();
        page2.setCurrentPos(2);
        page3=new onboardingFragment();
        page3.setCurrentPos(3);
    }



    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return page1;
            case 1:
                return page2;
            case 2:
                return page3;
            default:
                return page1;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }
}
