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

import com.blurspace.doov.MainArea;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.R;
import com.blurspace.doov.customdialogues.userdream_bottomdialog;
import com.blurspace.doov.customdialogues.userplatform_bottomdialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class homePlatformAdapter extends RecyclerView.Adapter<homePlatformAdapter.ViewHolder> {

    private Context mcontext;
    private List<PlatformModel> platformlist;
    private DatabaseReference dbref;
    public homePlatformAdapter() {

    }
    public homePlatformAdapter(Context mcontext, List<PlatformModel> platformlist) {
        this.platformlist=platformlist;
        this.mcontext=mcontext;
        dbref= FirebaseDatabase.getInstance().getReference("Users");
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.home_platformlay,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(homePlatformAdapter.ViewHolder holder, int position) {
        if(platformlist!=null) {
            Picasso.get().load(platformlist.get(position).getPlatformimgurl()).resize(300,300).into(holder.img);
            holder.name.setText(platformlist.get(position).getPlatformname());
            holder.field.setText(platformlist.get(position).getPlatformfield());
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.blurspace.doov.customdialogues.userplatform_bottomdialog userplatform_bottomdialog=new userplatform_bottomdialog();
                Bundle bundle=new Bundle();
                bundle.putString("platformimg",platformlist.get(position).getPlatformimgurl());
                bundle.putString("platformname",platformlist.get(position).getPlatformname());
                bundle.putString("platformfield",platformlist.get(position).getPlatformfield());
                bundle.putString("platformlink",platformlist.get(position).getLink());
                bundle.putString("platformdrm",platformlist.get(position).getFordreams());
               userplatform_bottomdialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                userplatform_bottomdialog.show(fm,"userplatform_bottomdialog");
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


    }

    @Override
    public int getItemCount() {
        if(platformlist.size()<25){
            return platformlist.size();
        }
        else {
        return 25;
    }
    }

    public void setDreams(List<PlatformModel> platformlist) {
        this.platformlist=platformlist;
        this.notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView  name;
        TextView field;
        View card;
        ShimmerFrameLayout shimmerFrameLayout;
        ImageView shimmerimg;
        TextView  shimmername;
        TextView shimmerfield;
        ImageView shimmeroptbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.homeplatformcardimg);
            name=itemView.findViewById(R.id.homeplatformcardname);
            field=itemView.findViewById(R.id.homeplatformcardfield);
            View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_admin_portal,null,false);
            card=itemView.findViewById(R.id.homeplatformcard);
            shimmerFrameLayout=view.findViewById(R.id.homedreamshimmer);

        }
    }
}
