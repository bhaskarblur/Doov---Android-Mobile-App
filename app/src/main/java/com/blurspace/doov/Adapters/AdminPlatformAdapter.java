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

import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.R;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.blurspace.doov.customdialogues.platformadbottomdialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminPlatformAdapter extends RecyclerView.Adapter<AdminPlatformAdapter.ViewHolder> {

    private Context mcontext;
    private List<PlatformModel> platformlist;
    private AdminPortalViewModel apviewModel;

    public AdminPlatformAdapter(Context mcontext, List<PlatformModel> platformlist) {
        this.platformlist=platformlist;
        this.mcontext=mcontext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.admin_platformlay,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminPlatformAdapter.ViewHolder holder, int position) {
                Picasso.get().load(platformlist.get(position).getPlatformimgurl()).resize(300,300).into(holder.img);
                holder.name.setText(platformlist.get(position).getPlatformname());
                holder.field.setText(platformlist.get(position).getPlatformfield());
                Picasso.get().load(R.drawable.yellow_options_icon).into(holder.optbtn);
                String fordreams=platformlist.get(position).getFordreams();
                String link=platformlist.get(position).getLink();
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgurl",platformlist.get(position).getPlatformimgurl());
                bundle.putString("name",platformlist.get(position).getPlatformname());
                bundle.putInt("position",position);
                bundle.putString("field",platformlist.get(position).getPlatformfield());
                bundle.putString("fordreams",fordreams);
                bundle.putString("platformlink",link);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                platformadbottomdialog platformadbottomdialog=new platformadbottomdialog();
                platformadbottomdialog.setArguments(bundle);
                platformadbottomdialog.show(fm,"platformadbottomdialog");
            }
        });
        holder.optbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgurl",platformlist.get(position).getPlatformimgurl());
                bundle.putString("name",platformlist.get(position).getPlatformname());
                bundle.putInt("position",position);
                bundle.putString("field",platformlist.get(position).getPlatformfield());
                bundle.putString("fordreams",fordreams);
                bundle.putString("platformlink",link);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                platformadbottomdialog platformadbottomdialog=new platformadbottomdialog();
                platformadbottomdialog.setArguments(bundle);
                platformadbottomdialog.show(fm,"platformadbottomdialog");
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
        return platformlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView  name;
        TextView field;
        View card;
        ImageView optbtn;
        ShimmerFrameLayout shimmerFrameLayout;
        ImageView shimmerimg;
        TextView  shimmername;
        TextView shimmerfield;
        ImageView shimmeroptbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.adminplatformimg);
            name=itemView.findViewById(R.id.adminplatformcardname);
            field=itemView.findViewById(R.id.adminplatformcardfield);
            optbtn=itemView.findViewById(R.id.adminplatformcardoptions);
            View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_admin_portal,null,false);
            card=itemView.findViewById(R.id.adminplatformcard);
            shimmerFrameLayout=view.findViewById(R.id.admindreamshimmer);

        }
    }
}
