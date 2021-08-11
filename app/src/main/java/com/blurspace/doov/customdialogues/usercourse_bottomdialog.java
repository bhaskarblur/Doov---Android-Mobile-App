package com.blurspace.doov.customdialogues;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blurspace.doov.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.BuildConfig;
import com.squareup.picasso.Picasso;

public class usercourse_bottomdialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.usercourse_bottomdialog,container,false);
        ImageView courseimg= v.findViewById(R.id.coursedialogimg);
        TextView coursename=v.findViewById(R.id.coursedialogname);
        TextView coursefield=v.findViewById(R.id.coursedialogfield);
        Button visit=v.findViewById(R.id.coursegobtn);
        ImageView sharebtn=v.findViewById(R.id.coursesharebtn);
        Bundle bundle=getArguments();
        String recimg=bundle.getString("courseimg");
        String recname=bundle.getString("coursename");
        String recfield=bundle.getString("coursefield");
        String reclink=bundle.getString("courselink");
        String recdr=bundle.getString("coursedrm");


        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharemsg = new Intent(Intent.ACTION_VIEW);
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Doov App");
                    String shareMessage= "Check this course "+recname+" listed at Doov.\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Picasso.get().load(recimg).into(courseimg);
        if(reclink.toString().contains("Udacity") || reclink.toString().contains("udacity")) {
            visit.setText("Available On Udacity");
        }
        else if(reclink.toString().contains("Udemy") || reclink.toString().contains("udemy")) {
            visit.setText("Available On Udemy");
        }
        else if(reclink.toString().contains("Coursera") || reclink.toString().contains("coursera")) {
            visit.setText("Available On Coursera");
        }
        else {
                visit.setText("Check Here");
        }
        coursename.setText(recname);
        coursefield.setText(recfield);
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = reclink.toString();
                try {
                    Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Chrome is probably not installed
                }
            }
        });

        return v;
    }
}
