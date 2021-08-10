package com.blurspace.doov;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blurspace.doov.databinding.FragmentOnboardingBinding;

import java.util.zip.Inflater;


public class onboardingFragment extends Fragment {
    FragmentOnboardingBinding onbfrag;

    public Integer getCurrentPos() {
        return currentPos;
    }

    private Integer currentPos=1;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       onbfrag=FragmentOnboardingBinding.inflate(inflater,container,false);

       switch (currentPos){
           case 1:
               onbfrag.onbimg.setImageResource(R.drawable.onbimg1);
               onbfrag.onbheadtxt.setText("Pick Your Dream");
               onbfrag.onbsubtxt.setText("Pick Your 'Dream' From Various Dreams And Get Ready To Accomplish It.");


               break;
           case 2:
               onbfrag.onbimg.setImageResource(R.drawable.onbimg2);
               onbfrag.onbheadtxt.setText("Set Your Journey");
               onbfrag.onbsubtxt.setText("Set Your To-Do List, Explore Latest News , Best Courses, Platforms And More.");


               break;
           case 3:
               onbfrag.onbimg.setImageResource(R.drawable.onbimg3);
               onbfrag.onbheadtxt.setText("Accomplished");
               onbfrag.onbsubtxt.setText("Complete Your Tasks , Get Up To Date With Latest News , Courses And Test Yourself To Accomplish Your Dream.");
               break;
       }
        return onbfrag.getRoot();
    }

    public void setCurrentPos(Integer currentPos) {
        this.currentPos = currentPos;
    }
}
