package com.blurspace.doov.customdialogues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blurspace.doov.Models.Userauth;
import com.blurspace.doov.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class changeavatdialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.changeavtdialog_lay,container);
        ImageView currimg= view.findViewById(R.id.curravatarimg);
        View yellowmale=view.findViewById(R.id.yellowmalesel);
        View yellowfemale=view.findViewById(R.id.yellowfemalesel);
        View blackmale=view.findViewById(R.id.blackmalesel);
        View blackfemale=view.findViewById(R.id.blackfemalesel);
        Button savebtn=view.findViewById(R.id.saveavabtn);
        Bundle bundle=getArguments();
        String currentname=bundle.getString("currentname");
        String currentmail=bundle.getString("currentmail");
        String currentavatar=bundle.getString("currentavatar");
        final String[] newavatar = new String[1];

        if(currentavatar.equals("yellowmale")) {
            newavatar[0] ="yellowmale";
            Picasso.get().load(R.drawable.yellowmale_dp).into(currimg);
        }
        else if(currentavatar.equals("blackmale")) {
            newavatar[0] ="blackmale";
            Picasso.get().load(R.drawable.blackmale_dp).into(currimg);
        }
        else if(currentavatar.equals("yellowfemale")) {
            newavatar[0] ="yellowfemale";
            Picasso.get().load(R.drawable.yellowfemale_dp).into(currimg);
        }
        else if(currentavatar.equals("blackfemale")) {
            newavatar[0] ="blackfemale";
            Picasso.get().load(R.drawable.blackfemale_dp).into(currimg);
        }

        yellowmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newavatar[0] ="yellowmale";
                Picasso.get().load(R.drawable.yellowmale_dp).into(currimg);
            }
        });
        blackmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newavatar[0] ="blackmale";
                Picasso.get().load(R.drawable.blackmale_dp).into(currimg);
            }
        });
        yellowfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newavatar[0] ="yellowfemale";
                Picasso.get().load(R.drawable.yellowfemale_dp).into(currimg);
            }
        });
        blackfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newavatar[0] ="blackfemale";
                Picasso.get().load(R.drawable.blackfemale_dp).into(currimg);
            }
        });


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mref= FirebaseDatabase.getInstance().getReference("Users");
                mref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Userauth(currentname
                        ,currentmail,newavatar[0]));
                dismiss();
            }
        });
        return view;
    }
}
