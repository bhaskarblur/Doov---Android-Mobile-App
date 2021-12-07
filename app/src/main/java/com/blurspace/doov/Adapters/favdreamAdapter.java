package com.blurspace.doov.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.blurspace.doov.customdialogues.crrdream_bottomdialog;
import com.blurspace.doov.customdialogues.userdream_bottomdialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class favdreamAdapter extends RecyclerView.Adapter<favdreamAdapter.viewHolder> {
    public favdreamAdapter(Context mcontext, List<DreamsModel> dreamsList) {
        this.mcontext = mcontext;
        this.dreamsList = dreamsList;
    }

    private Context mcontext;
    private List<DreamsModel> dreamsList;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.favdreams_lay,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(dreamsList!=null) {
            Picasso.get().load(dreamsList.get(position).getDreamimgurl()).resize(300,300).into(holder.img);
            holder.name.setText(dreamsList.get(position).getDreamname());
            holder.field.setText(dreamsList.get(position).getDreamfield());
        }

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
                bundle.putString("dreamimg",dreamsList.get(position).getDreamimgurl());
                bundle.putString("dreamname",dreamsList.get(position).getDreamname());
                bundle.putString("dreamfield",dreamsList.get(position).getDreamfield());
                userdream_bottomdialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                userdream_bottomdialog.show(fm,"userdream_bottomdialog");

            }
        });

        holder.resbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.blurspace.doov.customdialogues.userdream_bottomdialog userdream_bottomdialog=new userdream_bottomdialog();
                Bundle bundle=new Bundle();
                bundle.putString("dreamimg",dreamsList.get(position).getDreamimgurl());
                bundle.putString("dreamname",dreamsList.get(position).getDreamname());
                bundle.putString("dreamfield",dreamsList.get(position).getDreamfield());
                userdream_bottomdialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                userdream_bottomdialog.show(fm,"userdream_bottomdialog");
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
        View resbtn;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.favdreamimg);
            name=itemView.findViewById(R.id.favdreamname);
            field=itemView.findViewById(R.id.favdreamfield);
            card=itemView.findViewById(R.id.favdreamcard);
            resbtn=itemView.findViewById(R.id.favdreamstart);
        }
    }
}
