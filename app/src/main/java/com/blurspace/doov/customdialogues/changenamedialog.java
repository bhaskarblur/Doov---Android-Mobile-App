package com.blurspace.doov.customdialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.blurspace.doov.Models.Userauth;
import com.blurspace.doov.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class changenamedialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.changenamedialog_lay,container);
        Bundle bundle=getArguments();
        String currentname=bundle.getString("currentname");
        String currentmail=bundle.getString("currentmail");
        String currentavatar=bundle.getString("currentavatar");
        EditText newname=view.findViewById(R.id.newnametxt);
        Button savebtn=view.findViewById(R.id.namesave);
        newname.setText(currentname);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newname.getText().toString().equals(currentname)) {
                    dismiss();
                }
                if(newname.getText().toString().equals(null)) {

                    dismiss();
                }
                else {
                    DatabaseReference mref= FirebaseDatabase.getInstance().getReference("Users");
                    mref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Userauth(newname.getText().toString()
                    ,currentmail,currentavatar));
                    dismiss();
                }
            }
        });
        return view;
    }
}
