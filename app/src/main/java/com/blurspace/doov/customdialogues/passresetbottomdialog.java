package com.blurspace.doov.customdialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blurspace.doov.Loginact;
import com.blurspace.doov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;


public class passresetbottomdialog extends BottomSheetDialogFragment {

    private FirebaseAuth mauth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {

        View view= LayoutInflater.from(getContext()).inflate(R.layout.passresetbottomdialog,container);
        EditText emailtxt=view.findViewById(R.id.emailpasstxt);
        Button savepass=view.findViewById(R.id.savenewpassbtn);

        mauth=FirebaseAuth.getInstance();
        savepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(emailtxt.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your email!", Toast.LENGTH_SHORT).show();
                }
                else if(!emailtxt.getText().toString().matches(emailPattern)) {
                    Toast.makeText(getActivity(), "Please Enter A Valid Mail!", Toast.LENGTH_SHORT).show();
                }
                 else {
                    if (mauth == null) {
                        Toast.makeText(getActivity(), "Your are not signed in!", Toast.LENGTH_SHORT).show();
                        mauth.signOut();
                        startActivity(new Intent(getActivity(), Loginact.class));
                        getActivity().finish();
                    } else {
                        mauth.sendPasswordResetEmail(emailtxt.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Password Change Mail Sent!", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "There was an error while sending link!", Toast.LENGTH_SHORT).show();
                                Log.d("passerror",e.getMessage());
                            }
                        });
                    }
                }
            }
        });
        return view;
    }
}
