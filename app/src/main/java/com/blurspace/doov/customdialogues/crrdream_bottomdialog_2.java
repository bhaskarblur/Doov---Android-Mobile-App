package com.blurspace.doov.customdialogues;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blurspace.doov.Act_CurrDream;
import com.blurspace.doov.Models.DreamsModel;
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

public class crrdream_bottomdialog_2 extends BottomSheetDialogFragment {
    DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("Users");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.currdream_bottomdialog,container,false);
        ImageView dreamimg= v.findViewById(R.id.crrdreamdialogimg);
        TextView dreaname=v.findViewById(R.id.crrdreamdialogname);
        TextView dreamfield=v.findViewById(R.id.crrdreamdialogfield);
        Button startdream=v.findViewById(R.id.crrdreamcont);
        ImageView lovedbtn=v.findViewById(R.id.crrdream_lovebtn);
        ImageView notlovedbtn=v.findViewById(R.id.crrdream_notlovebtn);
        ImageView sharebtn=v.findViewById(R.id.crrdream_sharebtn);
        Bundle bundle=getArguments();
        String recimg=bundle.getString("dreamimg");
        String recname=bundle.getString("dreamname");
        String recfield=bundle.getString("dreamfield");

        
        dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("FavoriteDreams").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()) {
                    if(ds.getValue().toString().contains("dreamname="+recname)
                            &&  ds.getValue().toString().contains("dreamfield="+recfield)) {
                        lovedbtn.setVisibility(View.VISIBLE);
                        notlovedbtn.setVisibility(View.INVISIBLE);
                    }
                    else if(!snapshot.getValue().toString().contains("dreamname="+recname)
                            &&  !snapshot.getValue().toString().contains("dreamfield="+recfield)) {
                        lovedbtn.setVisibility(View.INVISIBLE);
                        notlovedbtn.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        notlovedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("FavoriteDreams").child(dbref.push().getKey()).setValue(
                        new DreamsModel(recimg,recname,recfield)
                );
                Toast.makeText(getContext(), "Added to Favourite Dreams", Toast.LENGTH_SHORT).show();
                lovedbtn.setVisibility(View.VISIBLE);
               notlovedbtn.setVisibility(View.INVISIBLE);
            }
        });

        startdream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open Current dream act
                getActivity().startActivity(new Intent(getActivity(), Act_CurrDream.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                dismiss();
            }
        });
        lovedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("FavoriteDreams").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            if(ds.getValue().toString().contains(recname)
                                    &&  ds.getValue().toString().contains(recname)) {
                                dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("FavoriteDreams").child(ds.getKey()).removeValue();
                                Toast.makeText(getContext(), "Removed from Favourite Dreams", Toast.LENGTH_SHORT).show();
                            }
                            lovedbtn.setVisibility(View.INVISIBLE);
                            notlovedbtn.setVisibility(View.VISIBLE);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharemsg = new Intent(Intent.ACTION_VIEW);
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Doov App");
                    String shareMessage= "Pursue your dream of "+recname+" with great Knowledge and Courses At Doov.\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Picasso.get().load(recimg).into(dreamimg);
        dreaname.setText(recname);
        dreamfield.setText(recfield);


        return v;
    }
}
