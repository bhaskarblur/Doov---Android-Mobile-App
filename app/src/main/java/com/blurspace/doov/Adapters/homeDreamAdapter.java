package com.blurspace.doov.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.R;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.blurspace.doov.ViewModels.HomeViewModel;
import com.blurspace.doov.customdialogues.dreamadbottomdialog;
import com.blurspace.doov.customdialogues.userdream_bottomdialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class homeDreamAdapter extends RecyclerView.Adapter<homeDreamAdapter.ViewHolder> {

    private Context mcontext;
    private List<DreamsModel> dreamlist;
    private DatabaseReference dbref;
    public String lovedstat;


    public homeDreamAdapter() {

    }
    public homeDreamAdapter(Context mcontext, List<DreamsModel> dreamlist) {
        this.dreamlist=dreamlist;
        this.mcontext=mcontext;
        dbref= FirebaseDatabase.getInstance().getReference("Users");
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.home_dreamlay,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(homeDreamAdapter.ViewHolder holder, int position) {
        dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("FavoriteDreams").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()) {
                    if(ds.getValue().toString().contains("dreamname="+dreamlist.get(position).getDreamname())
                    &&  ds.getValue().toString().contains("dreamfield="+dreamlist.get(position).getDreamfield())) {
                        holder.lovedbtn.setVisibility(View.VISIBLE);
                        holder.notlovedbtn.setVisibility(View.INVISIBLE);
                    }
                    else if(!snapshot.getValue().toString().contains("dreamname="+dreamlist.get(position).getDreamname())
                            &&  !snapshot.getValue().toString().contains("dreamfield="+dreamlist.get(position).getDreamfield())) {
                        holder.lovedbtn.setVisibility(View.INVISIBLE);
                        holder.notlovedbtn.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        if(dreamlist!=null) {
            Picasso.get().load(dreamlist.get(position).getDreamimgurl()).into(holder.img);
            holder.name.setText(dreamlist.get(position).getDreamname());
            holder.field.setText(dreamlist.get(position).getDreamfield());
            Picasso.get().load(R.drawable.love_nobgicon).into(holder.lovedbtn);
            Picasso.get().load(R.drawable.notlove_nobgicon).into(holder.notlovedbtn);
        }

//        holder.notlovedbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("FavoriteDreams").child(dbref.push().getKey()).setValue(
//                        new DreamsModel(dreamlist.get(position).getDreamimgurl(),dreamlist.get(position).getDreamname(),dreamlist.get(position).getDreamfield())
//                );
//                Toast.makeText(mcontext, "Removed From Favourite Dreams", Toast.LENGTH_SHORT).show();
//                holder.lovedbtn.setVisibility(View.VISIBLE);
//                holder.notlovedbtn.setVisibility(View.INVISIBLE);
//            }
//        });
//        holder.lovedbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("FavoriteDreams").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot ds:snapshot.getChildren()) {
//                            if(ds.getValue().toString().contains("dreamname="+dreamlist.get(position).getDreamname())
//                                    &&  ds.getValue().toString().contains("dreamfield="+dreamlist.get(position).getDreamfield())) {
//
//                                dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                        .child("FavoriteDreams").child(ds.getKey()).removeValue();
//                            }
//                            Toast.makeText(mcontext, "Added to Favourite Dreams", Toast.LENGTH_SHORT).show();
//                            holder.lovedbtn.setVisibility(View.INVISIBLE);
//                            holder.notlovedbtn.setVisibility(View.VISIBLE);
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        if(holder.name.getText().length()>10) {
            holder.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.name.setSelected(true);
            holder.name.setSingleLine(true);
        }
        if(holder.field.getText().length()>10) {
            holder.field.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.field.setSelected(true);
            holder.field.setSingleLine(true);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.blurspace.doov.customdialogues.userdream_bottomdialog userdream_bottomdialog=new userdream_bottomdialog();
                Bundle bundle=new Bundle();
                bundle.putString("dreamimg",dreamlist.get(position).getDreamimgurl());
                bundle.putString("dreamname",dreamlist.get(position).getDreamname());
                bundle.putString("dreamfield",dreamlist.get(position).getDreamfield());
                userdream_bottomdialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                userdream_bottomdialog.show(fm,"userdream_bottomdialog");

            }
        });
    }

    @Override
    public int getItemCount() {
        if(dreamlist.size()<25){
            return dreamlist.size();
        }
        else {
        return 25;
    }
    }

    public void setDreams(List<DreamsModel> dreamlist) {
        this.dreamlist=dreamlist;
        this.notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView  name;
        TextView field;
        View card;
        ImageView lovedbtn;
        ImageView notlovedbtn;
        ShimmerFrameLayout shimmerFrameLayout;
        ImageView shimmerimg;
        TextView  shimmername;
        TextView shimmerfield;
        ImageView shimmeroptbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.homedreamcardimg);
            name=itemView.findViewById(R.id.homedreamcardname);
            field=itemView.findViewById(R.id.homedreamcardfield);
            lovedbtn=itemView.findViewById(R.id.homedreamcardloved);
            notlovedbtn=itemView.findViewById(R.id.homedreamcardnotloved);
            View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_admin_portal,null,false);
            card=itemView.findViewById(R.id.homedreamcard);
            shimmerFrameLayout=view.findViewById(R.id.homedreamshimmer);

        }
    }
}
