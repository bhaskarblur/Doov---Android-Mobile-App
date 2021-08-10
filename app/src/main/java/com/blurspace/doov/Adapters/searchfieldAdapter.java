package com.blurspace.doov.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blurspace.doov.Models.searchfieldModel;
import com.blurspace.doov.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class searchfieldAdapter extends RecyclerView.Adapter<searchfieldAdapter.viewHolder> {
    private Context mcontext;
    private List<String> searchfieldModelList;
    private oncardclicklistener listener;
    private Integer checkpos=0;
    private Boolean selected=false;
    public searchfieldAdapter(Context mcontext, List<String> searchfieldModelList) {
        this.mcontext = mcontext;
        this.searchfieldModelList = searchfieldModelList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.fieldfilter_lay,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.fieldname.setText(searchfieldModelList.get(position));
        if(checkpos==position){
            holder.fieldname.setTextColor(Color.parseColor("#FFD107"));
            holder.fieldbg.setBackgroundResource(R.drawable.fieldselbg);

        }
        else if(checkpos!=position) {
            holder.fieldbg.setBackgroundResource(R.drawable.fieldnotselbg);
            holder.fieldname.setTextColor(Color.parseColor("#DFDFDF"));
        }



    }

    @Override
    public int getItemCount() {
        return searchfieldModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView fieldname;
        View fieldbg;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            fieldname=itemView.findViewById(R.id.fieldname);
            fieldbg=itemView.findViewById(R.id.fieldbg);
            fieldbg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null) {
                        checkpos=position;
                        notifyDataSetChanged();
                        listener.oncardclick(searchfieldModelList.get(position));
                    }

                }
            });
        }
    }

    public interface oncardclicklistener{
            void oncardclick(String catname);
    }
    public void setoncardclicklistener(oncardclicklistener listener){
        this.listener=listener;
    }
}
