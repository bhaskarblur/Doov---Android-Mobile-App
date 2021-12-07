package com.blurspace.doov.Adapters;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.R;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.blurspace.doov.customdialogues.dreamadbottomdialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminDreamAdapter extends RecyclerView.Adapter<AdminDreamAdapter.ViewHolder> {

    private Context mcontext;
    private List<DreamsModel> dreamlist;
    private AdminPortalViewModel apviewModel;

    public AdminDreamAdapter() {

    }
    public AdminDreamAdapter(Context mcontext,List<DreamsModel> dreamlist) {
        this.dreamlist=dreamlist;
        this.mcontext=mcontext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.admin_dreamlay,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AdminDreamAdapter.ViewHolder holder, int position) {
                Picasso.get().load(dreamlist.get(position).getDreamimgurl()).resize(300,300).into(holder.img);
                holder.name.setText(dreamlist.get(position).getDreamname());
                holder.field.setText(dreamlist.get(position).getDreamfield());
                Picasso.get().load(R.drawable.yellow_options_icon).into(holder.optbtn);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgurl",dreamlist.get(position).getDreamimgurl());
                bundle.putString("name",dreamlist.get(position).getDreamname());
                bundle.putInt("position",position);
                bundle.putString("field",dreamlist.get(position).getDreamfield());
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                com.blurspace.doov.customdialogues.dreamadbottomdialog dreamadbottomdialog=new dreamadbottomdialog();
                dreamadbottomdialog.setArguments(bundle);
                dreamadbottomdialog.show(fm,"dreamadbottomdialog");
            }
        });
        holder.optbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgurl",dreamlist.get(position).getDreamimgurl());
                bundle.putString("name",dreamlist.get(position).getDreamname());
                bundle.putInt("position",position);
                bundle.putString("field",dreamlist.get(position).getDreamfield());
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                com.blurspace.doov.customdialogues.dreamadbottomdialog dreamadbottomdialog=new dreamadbottomdialog();
               dreamadbottomdialog.setArguments(bundle);
                dreamadbottomdialog.show(fm,"dreamadbottomdialog");

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
        return dreamlist.size();
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
        ImageView optbtn;
        ShimmerFrameLayout shimmerFrameLayout;
        ImageView shimmerimg;
        TextView  shimmername;
        TextView shimmerfield;
        ImageView shimmeroptbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.admindreamcardimg);
            name=itemView.findViewById(R.id.admindreamcardname);
            field=itemView.findViewById(R.id.admindreamcardfield);
            optbtn=itemView.findViewById(R.id.admindreamcardoption);
            View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_admin_portal,null,false);
            card=itemView.findViewById(R.id.admindreamcard);
            shimmerFrameLayout=view.findViewById(R.id.admindreamshimmer);

        }
    }
}
