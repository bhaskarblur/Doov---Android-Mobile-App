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

import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class userplatform_bottomdialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.userplatform_bottomdialog,container,false);
        ImageView platformimg= v.findViewById(R.id.platformdialogimg);
        TextView platformname=v.findViewById(R.id.platformdialogname);
        TextView platformfield=v.findViewById(R.id.platformdialogfield);
        Button visit=v.findViewById(R.id.platformgobtn);
        ImageView sharebtn=v.findViewById(R.id.platformsharebtn);
        Bundle bundle=getArguments();
        String recimg=bundle.getString("platformimg");
        String recname=bundle.getString("platformname");
        String recfield=bundle.getString("platformfield");
        String reclink=bundle.getString("platformlink");
        String recdr=bundle.getString("platformdrm");


        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharemsg = new Intent(Intent.ACTION_VIEW);
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Doov App");
                    String shareMessage= "Check this platform "+recname+" listed at Doov.\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Picasso.get().load(recimg).into(platformimg);
        platformname.setText(recname);
        platformfield.setText(recfield);
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
