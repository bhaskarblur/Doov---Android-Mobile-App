package com.blurspace.doov;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blurspace.doov.Models.Userauth;
import com.blurspace.doov.databinding.ActivitySigninactBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signinact extends AppCompatActivity {
   private ActivitySigninactBinding signbinding;
   private FirebaseAuth mauth;
   private DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signbinding=ActivitySigninactBinding.inflate(getLayoutInflater());
        setContentView(signbinding.getRoot());

        this.getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor));
        }

        mauth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference("Users");
        signbinding.errortextlogin.setVisibility(View.INVISIBLE);
        signbinding.progresscircle.setVisibility(View.INVISIBLE);
        viewfunctions();
    }

    private void viewfunctions() {
        signbinding.logingobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signinact.this, Loginact.class));
                finish();
            }
        });

        signbinding.signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signbinding.errortextlogin.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        signbinding.progresscircle.setVisibility(View.VISIBLE);
                        signbinding.signinbtn.setVisibility(View.INVISIBLE);

                        signincheck();
                    }
                },150);

            }

        });

    }

    private void signincheck() {

        final boolean[] connected = {false};
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


          if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED &&
          !signbinding.loginusername.getText().toString().isEmpty() && !signbinding.loginemail.getText().toString().isEmpty() &&
                        !signbinding.loginpass.getText().toString().isEmpty() && !signbinding.loginconfpass.getText().toString().isEmpty() &&
                        signbinding.loginpass.getText().toString().equals(signbinding.loginconfpass.getText().toString()))
         {
          connected[0] = true;

          // Main work here!

             mauth.createUserWithEmailAndPassword(signbinding.loginemail.getText().toString().replaceAll("\\s",""),signbinding.loginpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                 @Override
                 public void onSuccess(AuthResult authResult) {

                     mref.child(mauth.getUid().toString()).setValue(new Userauth(signbinding.loginusername.getText().toString(),signbinding.loginemail.getText().toString(),"yellowmale"));
                     signbinding.progresscircle.setVisibility(View.INVISIBLE);
                     signbinding.errortextlogin.setVisibility(View.INVISIBLE);
                     signbinding.signinbtn.setVisibility(View.VISIBLE);
                     Toast.makeText(Signinact.this, "Created a new account!", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(Signinact.this,Loginact.class));
                     finish();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure( Exception e) {
                     signbinding.progresscircle.setVisibility(View.INVISIBLE);
                     signbinding.signinbtn.setVisibility(View.VISIBLE);
                     signbinding.errortextlogin.setVisibility(View.VISIBLE);
                     signbinding.errortextlogin.setText(e.getMessage().toString());

                 };
             });
        }
        else {
            connected[0]=false;
        }

       if (connected[0] == false &&
               !signbinding.loginusername.getText().toString().isEmpty() && !signbinding.loginemail.getText().toString().isEmpty() &&
               !signbinding.loginpass.getText().toString().isEmpty() && !signbinding.loginconfpass.getText().toString().isEmpty() &&
               signbinding.loginpass.getText().equals(signbinding.loginconfpass.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Signinact.this);
            builder.setMessage("You are not connected to Internet. Please Try Again!");
            builder.setTitle("No Internet!");
            builder
                    .setPositiveButton(
                            "OK",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    AlertDialog alertDialog=builder.create();
                                    alertDialog.hide();
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

           signbinding.progresscircle.setVisibility(View.INVISIBLE);
           signbinding.signinbtn.setVisibility(View.VISIBLE);


        }

       else if (signbinding.loginusername.getText().toString().isEmpty()) {
            signbinding.progresscircle.setVisibility(View.INVISIBLE);
            signbinding.errortextlogin.setVisibility(View.VISIBLE);
            signbinding.signinbtn.setVisibility(View.VISIBLE);
            signbinding.errortextlogin.setText("Please Enter Username!");
        }

       else if (!signbinding.loginemail.getText().toString().matches(emailPattern) && !signbinding.loginemail.getText().toString().isEmpty()) {
            signbinding.progresscircle.setVisibility(View.INVISIBLE);
            signbinding.errortextlogin.setVisibility(View.VISIBLE);
            signbinding.signinbtn.setVisibility(View.VISIBLE);
            signbinding.errortextlogin.setText("Invalid Email Address!");
        }

       else if (signbinding.loginemail.getText().toString().isEmpty()) {
            signbinding.progresscircle.setVisibility(View.INVISIBLE);
            signbinding.errortextlogin.setVisibility(View.VISIBLE);
            signbinding.signinbtn.setVisibility(View.VISIBLE);
            signbinding.errortextlogin.setText("Please Enter Email!");
        }

       else if (signbinding.loginusername.getText().toString().isEmpty()) {
            signbinding.progresscircle.setVisibility(View.INVISIBLE);
            signbinding.errortextlogin.setVisibility(View.VISIBLE);
            signbinding.signinbtn.setVisibility(View.VISIBLE);
            signbinding.errortextlogin.setText("Please Enter Username!");
        }

       else if (signbinding.loginpass.getText().length() < 6 && !signbinding.loginpass.getText().toString().isEmpty()) {
            signbinding.progresscircle.setVisibility(View.INVISIBLE);
            signbinding.errortextlogin.setVisibility(View.VISIBLE);
            signbinding.signinbtn.setVisibility(View.VISIBLE);
            signbinding.errortextlogin.setText("Password Is Less Than 6 Characters!");

        }
       else if (signbinding.loginpass.getText().toString().isEmpty()) {
            signbinding.progresscircle.setVisibility(View.INVISIBLE);
            signbinding.errortextlogin.setVisibility(View.VISIBLE);
            signbinding.signinbtn.setVisibility(View.VISIBLE);
            signbinding.errortextlogin.setText("Please Enter Password!");
        }
       else if (signbinding.loginconfpass.getText().toString().isEmpty()) {
           signbinding.progresscircle.setVisibility(View.INVISIBLE);
           signbinding.errortextlogin.setVisibility(View.VISIBLE);
           signbinding.signinbtn.setVisibility(View.VISIBLE);
           signbinding.errortextlogin.setText("Please Confirm Password!");
       }
       else if (!signbinding.loginpass.getText().toString().matches(signbinding.loginconfpass.getText().toString())) {
               signbinding.progresscircle.setVisibility(View.INVISIBLE);
               signbinding.errortextlogin.setVisibility(View.VISIBLE);
               signbinding.signinbtn.setVisibility(View.VISIBLE);
               signbinding.errortextlogin.setText("Password Confirmation Incorrect!");
               Toast.makeText(this, signbinding.loginpass.getText().toString() + " , " +
                       signbinding.loginconfpass.getText().toString(), Toast.LENGTH_SHORT).show();
       }
        else if(signbinding.loginpass.getText().toString().equals(signbinding.loginconfpass.getText())) {
           signbinding.progresscircle.setVisibility(View.INVISIBLE);
           signbinding.errortextlogin.setVisibility(View.INVISIBLE);
           signbinding.signinbtn.setVisibility(View.VISIBLE);
       }

    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
}