package com.blurspace.doov;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.blurspace.doov.Models.Userauth;
import com.blurspace.doov.customdialogues.passresetdialog;
import com.blurspace.doov.databinding.ActivityLoginactBinding;
import com.blurspace.doov.databinding.PassresetdialogBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Loginact extends AppCompatActivity {
    private ActivityLoginactBinding logbinding;
    private FirebaseAuth mauth;
    private GoogleSignInClient mSignInClient;
    private int RC_SIGN_Gooogle = 1;
    private DatabaseReference mref;
    private DatabaseReference mrefcheck;
    private String AdminId= "blurspace75@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logbinding=ActivityLoginactBinding.inflate(getLayoutInflater());
        setContentView(logbinding.getRoot());

        this.getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bgcolor));
        }

        mref= FirebaseDatabase.getInstance().getReference("Users");
        mrefcheck= FirebaseDatabase.getInstance().getReference("UsersList");
        mauth=FirebaseAuth.getInstance();
        logbinding.errortextlogin.setVisibility(View.INVISIBLE);
        logbinding.progresscircle.setVisibility(View.INVISIBLE);

        if(mauth.getCurrentUser()!=null && !mauth.getCurrentUser().getEmail().toString().equals(AdminId)) {
            startActivity(new Intent(Loginact.this,MainArea.class));
            finish();
        }
         if(mauth.getCurrentUser()!=null && mauth.getCurrentUser().getEmail().toString().equals(AdminId)) {
             startActivity(new Intent(Loginact.this,AdminPortal.class));
             finish();
         }
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(Loginact.this,gso);
        viewfunctions();
    }

    private void viewfunctions() {
        logbinding.signingobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loginact.this,Signinact.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

        logbinding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logbinding.errortextlogin.setVisibility(View.INVISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logbinding.progresscircle.setVisibility(View.VISIBLE);
                        logbinding.loginbtn.setVisibility(View.INVISIBLE);

                        checklogin();
                    }
                },150);

            }
        });
        logbinding.googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginwithgoogle();
            }
        });
        logbinding.forgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open custom dialoguebox for getting email for reset.

                com.blurspace.doov.customdialogues.passresetdialog passresetdialog= new passresetdialog();
                passresetdialog.show(getSupportFragmentManager(),"passresetdialog");


            }
        });
    }

    private void loginwithgoogle() {
        Intent signIn= mSignInClient.getSignInIntent();
        startActivityForResult(signIn,RC_SIGN_Gooogle);
    }

    private void checklogin() {
        final boolean[] connected = {false};
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
        && !logbinding.loginemail.getText().toString().isEmpty() && !logbinding.loginpass.getText().toString().isEmpty())
        {
            connected[0] = true;

            // Main work here!
            mauth.signInWithEmailAndPassword(logbinding.loginemail.getText().toString().replaceAll("\\s",""),logbinding.loginpass.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            FirebaseUser firebaseUser=authResult.getUser();
                            if(authResult.getUser().getEmail().toString().equals("blurspace75@gmail.com")) {
                                startActivity(new Intent(Loginact.this,AdminPortal.class));
                                finish();
                            }
                             else {
                                startActivity(new Intent(Loginact.this, MainArea.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    logbinding.progresscircle.setVisibility(View.INVISIBLE);
                    logbinding.errortextlogin.setVisibility(View.VISIBLE);
                    logbinding.loginbtn.setVisibility(View.VISIBLE);

                    if(e.getMessage().toString().contains("no user record")) {
                        logbinding.errortextlogin.setText("No Account with this Email exists.");
                    }
                    if(e.getMessage().toString().contains("password is invalid")) {
                        logbinding.errortextlogin.setText("Invalid Password!");
                    }
                    if(e.getMessage().toString().contains("badly formatted")) {
                        logbinding.errortextlogin.setText("Invalid Email!");
                    }
                }
            });

        }
        else {
            connected[0]=false;
        }
        if (connected[0] == false && !logbinding.loginemail.getText().toString().isEmpty() && !logbinding.loginpass.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Loginact.this);
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

            logbinding.progresscircle.setVisibility(View.INVISIBLE);
            logbinding.loginbtn.setVisibility(View.VISIBLE);


        }

        else if (!logbinding.loginemail.getText().toString().matches(emailPattern) && !logbinding.loginemail.getText().toString().isEmpty()) {
            logbinding.progresscircle.setVisibility(View.INVISIBLE);
            logbinding.errortextlogin.setVisibility(View.VISIBLE);
            logbinding.loginbtn.setVisibility(View.VISIBLE);
            logbinding.errortextlogin.setText("Invalid Email Address!");
        }

        else if (logbinding.loginemail.getText().toString().isEmpty()) {
            logbinding.progresscircle.setVisibility(View.INVISIBLE);
            logbinding.errortextlogin.setVisibility(View.VISIBLE);
            logbinding.loginbtn.setVisibility(View.VISIBLE);
            logbinding.errortextlogin.setText("Please Enter Email!");
        }
        else if (logbinding.loginpass.getText().toString().isEmpty()) {
            logbinding.progresscircle.setVisibility(View.INVISIBLE);
            logbinding.errortextlogin.setVisibility(View.VISIBLE);
            logbinding.loginbtn.setVisibility(View.VISIBLE);
            logbinding.errortextlogin.setText("Please Enter Password!");
        }

    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_Gooogle){


            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acc=task.getResult(ApiException.class);
                assert acc != null;
                logbinding.loginbtn.setVisibility(View.INVISIBLE);
                logbinding.progresscircle.setVisibility(View.VISIBLE);
                FirebaseGoogleAuth(acc);


            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error:",e.getMessage().toString());
            }
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mauth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = mauth.getCurrentUser();
                    mrefcheck.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot snapshot) {
                            if(!snapshot.child(mauth.getCurrentUser().getUid()).exists()) {
                                mrefcheck.child(mauth.getCurrentUser().getUid().toString()).setValue("exists");
                                mref.child(mauth.getUid().toString()).setValue(new Userauth(mauth.getCurrentUser().getDisplayName(),mauth.getCurrentUser().getEmail(),"yellowmale"));
                            }
                        }

                        @Override
                        public void onCancelled( DatabaseError error) {
                            Log.d("ErrorDb:",error.getMessage().toString());
                        }
                    });


                    startActivity(new Intent(Loginact.this,MainArea.class));

                    finish();
                }
                else{
                    Toast.makeText(Loginact.this, "An Error Occured! Please Try Again!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}