package com.blurspace.doov.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.R;
import com.blurspace.doov.customdialogues.userdream_bottomdialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class searchresAdapter extends RecyclerView.Adapter<searchresAdapter.viewHolder> {

    public searchresAdapter(Context mcontext, List<DreamsModel> searchreslist) {
        this.mcontext = mcontext;
        this.searchreslist = searchreslist;
    }

    private Context mcontext;
    private List<DreamsModel> searchreslist;


    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.searchres_lay,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(searchreslist.get(position).getDreamname());
        Picasso.get().load(searchreslist.get(position).getDreamimgurl()).into(holder.img);
        holder.field.setText(searchreslist.get(position).getDreamfield());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.blurspace.doov.customdialogues.userdream_bottomdialog userdream_bottomdialog=new userdream_bottomdialog();
                Bundle bundle=new Bundle();
                bundle.putString("dreamimg",searchreslist.get(position).getDreamimgurl());
                bundle.putString("dreamname",searchreslist.get(position).getDreamname());
                bundle.putString("dreamfield",searchreslist.get(position).getDreamfield());
                userdream_bottomdialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                userdream_bottomdialog.show(fm,"userdream_bottomdialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchreslist.size();
    }

    public void searchList(List<DreamsModel> searchedList) {
        searchreslist=searchedList;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView field;
        View card;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.searchrescardimg);
            name=itemView.findViewById(R.id.searchrescardname);
            field=itemView.findViewById(R.id.searchrescardfield);
            card=itemView.findViewById(R.id.searchrescard);
        }
    }
}
