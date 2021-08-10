package com.blurspace.doov.customdialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.blurspace.doov.R;
import com.blurspace.doov.databinding.PassresetdialogBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class passresetdialog extends AppCompatDialogFragment {

    private FirebaseAuth mauth;
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.passresetdialog,null);
        builder.setView(view);
        mauth=FirebaseAuth.getInstance();

        EditText emailtxt= view.findViewById(R.id.resetpassmail);
        Button send=view.findViewById(R.id.sendbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(emailtxt.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Mail!", Toast.LENGTH_SHORT).show();
                }
                else if(!emailtxt.getText().toString().matches(emailPattern)) {
                    Toast.makeText(getActivity(), "Please Enter A Valid Mail!", Toast.LENGTH_SHORT).show();
                }
                else {
                    mauth.sendPasswordResetEmail(emailtxt.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Password Reset Mail Sent!", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            if(e.getMessage().toString().contains("no user record")) {
                            Toast.makeText(getActivity(), "No Account with this Email exists.", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }
            }
        });

        return builder.create();
    }
}
