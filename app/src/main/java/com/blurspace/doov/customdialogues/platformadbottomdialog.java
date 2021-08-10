package com.blurspace.doov.customdialogues;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.blurspace.doov.R;
import com.blurspace.doov.Repositories.AdminPortalRepository;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;


public class platformadbottomdialog extends BottomSheetDialogFragment {
    final int IMAGE_PICK_CODE=1000;
    final int PERMISSION_CODE=1001;
    private Uri imageuri;
    ImageView platformimg;
    Integer pos;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.platformaddbottomdialog,container,false);
        EditText name=v.findViewById(R.id.platformsetname);
        EditText field=v.findViewById(R.id.platformsetfield);
        EditText fordreams=v.findViewById(R.id.platformsetdream);
        EditText link=v.findViewById(R.id.platformlinkset);
        Button savebtn=v.findViewById(R.id.platformsavebtn);
        Button deletebtn=v.findViewById(R.id.platformdeletebtn);
        platformimg=v.findViewById(R.id.platformsetimg);

        AdminPortalRepository datarepo= new AdminPortalRepository(getContext());
        Bundle bundle=this.getArguments();


        String deleteimguri=bundle.getString("imgurl");
        if(bundle!=null) {
            pos=bundle.getInt("position");
            String imgurlrec=bundle.getString("imgurl");
             String namerec=bundle.getString("name");
            String fieldrec=bundle.getString("field");
            String fordreamrec=bundle.getString("fordreams");
            String linkrec=bundle.getString("platformlink");
            if(imgurlrec!=null && namerec!=null) {
                Picasso.get().load(imgurlrec).into(platformimg);
                name.setText(namerec);
                field.setText(fieldrec);
                fordreams.setText(fordreamrec);
                link.setText(linkrec);

            }
            else {
                Picasso.get().load(imageuri).into(platformimg);
            }
        }


        Integer finalPos = pos;
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!name.getText().toString().isEmpty() && imageuri!=null && !field.getText().toString().isEmpty()){

                datarepo.AddPlatformtoDB(imageuri,imageuri.toString(),name.getText().toString(),field.getText().toString(), finalPos,fordreams.getText().toString(),link.getText().toString());
            }
                else {
                    Toast.makeText(getActivity(), "Something is missing!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String finalImgurlrec = deleteimguri;
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               datarepo.RemovePlatformFromDB(finalImgurlrec,name.getText().toString(),field.getText().toString());
            }
        });
        platformimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);

                    }
                    else {
                        pickImageFromGallery();

                    }
                }
            }
        });


       return v;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    private void pickImageFromGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        if(data==null) {
            Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
        else {
            if(requestCode==IMAGE_PICK_CODE) {
                imageuri=data.getData();
                Picasso.get().load(imageuri).into(platformimg);

            }
        }
    }

}

