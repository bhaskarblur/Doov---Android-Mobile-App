package com.blurspace.doov.customdialogues;

import android.Manifest;
import android.app.Dialog;

import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorJoiner;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.blurspace.doov.R;
import com.blurspace.doov.Repositories.AdminPortalRepository;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;


public class dreamadbottomdialog extends BottomSheetDialogFragment {
    final int IMAGE_PICK_CODE=1000;
    final int PERMISSION_CODE=1001;
    private Uri imageuri;
    ImageView dreamimg;
    Integer pos;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.dreamaddbottomdialog,container,false);
         dreamimg=v.findViewById(R.id.dreamsetimg);
        EditText name=v.findViewById(R.id.dreamsetname);
        EditText field=v.findViewById(R.id.dreamsetfield);
        Button savebtn=v.findViewById(R.id.dreamsavebtn);
        Button deletebtn=v.findViewById(R.id.dreamdeletebtn);

        AdminPortalRepository datarepo= new AdminPortalRepository(getContext());
        Bundle bundle=this.getArguments();


        String deleteimguri=bundle.getString("imgurl");
        if(bundle!=null) {
            pos=bundle.getInt("position");
            String imgurlrec=bundle.getString("imgurl");
             String namerec=bundle.getString("name");
            String fieldrec=bundle.getString("field");
            if(imgurlrec!=null && namerec!=null) {
                Picasso.get().load(imgurlrec).into(dreamimg);
                name.setText(namerec);
                field.setText(fieldrec);
            }
            else {
                Picasso.get().load(imgurlrec).into(dreamimg);
            }
        }


        Integer finalPos = pos;
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!name.getText().toString().isEmpty() && imageuri!=null && !field.getText().toString().isEmpty()){

                datarepo.AddDreamtoDB(imageuri,imageuri.toString(),name.getText().toString(),field.getText().toString(), finalPos);
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

                datarepo.RemoveDreamFromDB(finalImgurlrec,name.getText().toString(),field.getText().toString());
            }
        });
        dreamimg.setOnClickListener(new View.OnClickListener() {
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
                Picasso.get().load(imageuri).into(dreamimg);

            }
        }
    }

}

