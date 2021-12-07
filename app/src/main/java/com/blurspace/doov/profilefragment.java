package com.blurspace.doov;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.blurspace.doov.Models.Userauth;
import com.blurspace.doov.customdialogues.changeavatdialog;
import com.blurspace.doov.customdialogues.changenamedialog;
import com.blurspace.doov.customdialogues.passresetbottomdialog;
import com.blurspace.doov.databinding.FragmentProfilefragmentBinding;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class profilefragment extends Fragment implements PopupMenu.OnMenuItemClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentProfilefragmentBinding pfbdinging;
    private String mParam1;
    private String mParam2;
    private DatabaseReference mref;
    private FirebaseAuth mauth;
    private String avatarname;
    public profilefragment() {

    }
    
    public static profilefragment newInstance(String param1, String param2) {
        profilefragment fragment = new profilefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pfbdinging= FragmentProfilefragmentBinding.inflate(inflater,container,false);

        mauth=FirebaseAuth.getInstance();
        loadingauth();
        viewfunction();
        return pfbdinging.getRoot();


    }

    private void viewfunction() {
        pfbdinging.profileoptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup(v);
            }
        });
        pfbdinging.sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharemsg = new Intent(Intent.ACTION_VIEW);
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Doov App");
                    String shareMessage= "Accomplish your dreams with great information by download Doov App\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        pfbdinging.changepasslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mauth.getCurrentUser().getPhotoUrl() == null) {
                com.blurspace.doov.customdialogues.passresetbottomdialog passresetbottomdialog = new passresetbottomdialog();
                passresetbottomdialog.show(getChildFragmentManager(), "passresetbottomdialog");
            }
                else {
                    Toast.makeText(getActivity(), "Cannot change password as you are signed in with Google.", Toast.LENGTH_SHORT).show();
                }
            }

        });
        pfbdinging.dreamslslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dreamfragment df = new dreamfragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                transaction.replace(R.id.MainFragmentLayout, df);
                transaction.addToBackStack("B");
                transaction.commit();
            }
        });
        pfbdinging.feedbacklay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("\"market://details?id=" + getActivity().getPackageName());
                Intent gotoapp = new Intent(Intent.ACTION_VIEW, uri);
                gotoapp.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                gotoapp.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                try {
                    startActivity(gotoapp);
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });
        pfbdinging.contactuslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"blurspace75@gmail.com"});
                email.setData(Uri.parse("mailto:"));
                email.setType("message/rfc822");

                try {
                    //start email intent
                    startActivity(Intent.createChooser(email, "Choose Email Client..."));
                } catch (Exception e) {
                    //if any thing goes wrong for example no email client application or any exception
                    //get and show exception message
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        pfbdinging.appidealay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appideaFragment ai = new appideaFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                transaction.replace(R.id.MainFragmentLayout, ai);
                transaction.addToBackStack("A");
                transaction.commit();
            }
        });
    }

    private void showpopup(View v) {
        PopupMenu popupMenu=new PopupMenu(getContext(),v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.profoptionmenu);
        MenuItem item=popupMenu.getMenu().findItem(R.id.logout_item);
        SpannableString s = new SpannableString("Log out");
        item.setTitle(s);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#F24747")), 0, s.length(), 0);
        popupMenu.show();
    }

    private void loadingauth() {
        if(mauth==null) {
            startActivity(new Intent(getActivity(),Loginact.class));
            getActivity().finish();
        }
                mref = FirebaseDatabase.getInstance().getReference("Users");
                mref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Userauth userauth = snapshot.getValue(Userauth.class);
                        pfbdinging.username.setText(userauth.getUsername().toString());
                        pfbdinging.useremail.setText(userauth.getEmail());

                        if (pfbdinging.username.getText().length() > 12) {
                            pfbdinging.username.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                            pfbdinging.username.setSelected(true);
                            pfbdinging.username.setSingleLine(true);
                        }

                        if (pfbdinging.useremail.getText().length() > 15) {
                            pfbdinging.useremail.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                            pfbdinging.useremail.setSelected(true);
                            pfbdinging.useremail.setSingleLine(true);
                        }

                        if (userauth.getAvatar().toString().equals("yellowmale")) {
                            Picasso.get().load(R.drawable.yellowmale_dp).into(pfbdinging.useravatar);
                            avatarname="yellowmale";
                        } else if (userauth.getAvatar().toString().equals("blackmale")) {
                            Picasso.get().load(R.drawable.blackmale_dp).into(pfbdinging.useravatar);
                            avatarname="blackmale";
                        } else if (userauth.getAvatar().toString().equals("yellowfemale")) {
                            Picasso.get().load(R.drawable.yellowfemale_dp).into(pfbdinging.useravatar);
                            avatarname="yellowfemale";
                        } else if (userauth.getAvatar().toString().equals("blackfemale")) {
                            Picasso.get().load(R.drawable.blackfemale_dp).into(pfbdinging.useravatar);
                            avatarname="blackfemale";
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Error", error.getMessage());
                    }
                });
            }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.chavatar_item:
                Bundle bundle1=new Bundle();
                bundle1.putString("currentname",pfbdinging.username.getText().toString());
                bundle1.putString("currentmail",pfbdinging.useremail.getText().toString());
                bundle1.putString("currentavatar",avatarname);
                com.blurspace.doov.customdialogues.changeavatdialog changeavatdialog= new changeavatdialog();
                changeavatdialog.setArguments(bundle1);
                changeavatdialog.show(getChildFragmentManager(),"changeavatdialog");
                break;  
            case R.id.chname_item:
                Bundle bundle=new Bundle();
                bundle.putString("currentname",pfbdinging.username.getText().toString());
                bundle.putString("currentmail",pfbdinging.useremail.getText().toString());
                bundle.putString("currentavatar",avatarname);
                com.blurspace.doov.customdialogues.changenamedialog changenamedialog= new changenamedialog();
                changenamedialog.setArguments(bundle);
                changenamedialog.show(getChildFragmentManager(),"changenamedialog");
                break;
            case R.id.logout_item:
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext()).setTitle("Log out?")
                        .setMessage("Do you want to log out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mauth.signOut();
                                Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(),Loginact.class));
                                getActivity().finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

        }
        return false;
    }
}