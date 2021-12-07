package com.blurspace.doov.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.blurspace.doov.Act_CurrDream;
import com.blurspace.doov.Models.CurrentDreamModel;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.R;
import com.blurspace.doov.customdialogues.crrdream_bottomdialog;
import com.blurspace.doov.customdialogues.crrdream_bottomdialog_2;
import com.blurspace.doov.customdialogues.userdream_bottomdialog;
import com.blurspace.doov.dreamfragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class crrdreamAdapter extends RecyclerView.Adapter<crrdreamAdapter.viewHolder> {
    public crrdreamAdapter(Context mcontext, List<CurrentDreamModel.curdreammodel> dreamsList) {
        this.mcontext = mcontext;
        this.dreamsList = dreamsList;
    }

    private Context mcontext;
    private List<CurrentDreamModel.curdreammodel> dreamsList;
    private DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth mauth = FirebaseAuth.getInstance();

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.currdream_lay, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if (dreamsList != null) {
            Picasso.get().load(dreamsList.get(0).getDreaminfo().getDreamimgurl()).resize(300,300).into(holder.img);
            holder.name.setText(dreamsList.get(0).getDreaminfo().getDreamname());
            holder.field.setText(dreamsList.get(0).getDreaminfo().getDreamfield());
        }
        if (holder.name.getText().length() > 10) {
            holder.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.name.setSelected(true);
            holder.name.setSingleLine(true);
        }
        if (holder.field.getText().length() > 10) {
            holder.field.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.field.setSelected(true);
            holder.field.setSingleLine(true);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.blurspace.doov.customdialogues.crrdream_bottomdialog_2 userdream_bottomdialog =
                        new crrdream_bottomdialog_2();
                Bundle bundle = new Bundle();
                bundle.putString("dreamimg", dreamsList.get(0).getDreaminfo().getDreamimgurl());
                bundle.putString("dreamname", dreamsList.get(0).getDreaminfo().getDreamname());
                bundle.putString("dreamfield", dreamsList.get(0).getDreaminfo().getDreamfield());
                userdream_bottomdialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity) (mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                userdream_bottomdialog.show(fm, "userdream_bottomdialog");

            }
        });

        holder.leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext).setTitle("Leave?")
                        .setMessage("Do you want to leave your dream?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DreamsModel dreamsModel=new DreamsModel(dreamsList.get(0).getDreaminfo().getDreamimgurl()
                                ,dreamsList.get(0).getDreaminfo().getDreamname(),dreamsList.get(0).getDreaminfo().getDreamfield());

                                dbref.child(mauth.getCurrentUser().getUid()).child("PreviousDreams").child(dbref.push().getKey())
                                        .setValue(dreamsModel);
                                ((AppCompatActivity) mcontext).getViewModelStore().clear();
                                dreamfragment df = new dreamfragment();
                                FragmentTransaction transaction = ((AppCompatActivity) mcontext).getSupportFragmentManager().beginTransaction();
                                transaction.setCustomAnimations(R.anim.fade_fast_2, R.anim.fade);
                                transaction.replace(R.id.MainFragmentLayout, df);
                                transaction.commit();
                                dbref.child(mauth.getCurrentUser().getUid()).child("CurrentDream")
                                        .removeValue();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });

        holder.contbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, Act_CurrDream.class));
                ActivityOptions options  = ActivityOptions.makeSceneTransitionAnimation((Activity) mcontext);
                ((Activity) mcontext).overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dreamsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView field;
        View card;
        View contbtn;
        View leavebtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.currdreamimg);
            name = itemView.findViewById(R.id.currdreamname);
            field = itemView.findViewById(R.id.currdreamfield);
            card = itemView.findViewById(R.id.currdreamcard);
            contbtn = itemView.findViewById(R.id.currdreamcont);
            leavebtn = itemView.findViewById(R.id.currdreamleave);
        }
    }
}
