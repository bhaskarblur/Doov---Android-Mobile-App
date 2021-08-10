package com.blurspace.doov.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blurspace.doov.Models.CourseModel;
import com.blurspace.doov.R;
import com.blurspace.doov.customdialogues.usercourse_bottomdialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class homeCourseAdapter extends RecyclerView.Adapter<homeCourseAdapter.ViewHolder> {

    private Context mcontext;
    private List<CourseModel> Courselist;
    private DatabaseReference dbref;

    public homeCourseAdapter() {

    }
    public homeCourseAdapter(Context mcontext, List<CourseModel> Courselist) {
        this.Courselist=Courselist;
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
    public void onBindViewHolder(homeCourseAdapter.ViewHolder holder, int position) {
        if(Courselist!=null) {
            Picasso.get().load(Courselist.get(position).getCourseimgurl()).into(holder.img);
            holder.name.setText(Courselist.get(position).getCoursename());
            holder.field.setText(Courselist.get(position).getCoursefield());
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.blurspace.doov.customdialogues.usercourse_bottomdialog usercourse_bottomdialog=new usercourse_bottomdialog();
                Bundle bundle=new Bundle();
                bundle.putString("courseimg",Courselist.get(position).getCourseimgurl());
                bundle.putString("coursename",Courselist.get(position).getCoursename());
                bundle.putString("coursefield",Courselist.get(position).getCoursefield());
                bundle.putString("courselink",Courselist.get(position).getLink());
                bundle.putString("coursedrm",Courselist.get(position).getFordreams());
                usercourse_bottomdialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                usercourse_bottomdialog.show(fm,"usercourse_bottomdialog");

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
        if(Courselist.size()<25){
            return Courselist.size();
        }
        else {
        return 25;
    }
    }

    public void setDreams(List<CourseModel> Courselist) {
        this.Courselist=Courselist;
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
