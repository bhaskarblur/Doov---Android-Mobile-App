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
import com.blurspace.doov.Models.CourseModel;
import com.blurspace.doov.R;
import com.blurspace.doov.ViewModels.AdminPortalViewModel;
import com.blurspace.doov.customdialogues.courseadbottomdialog;
import com.blurspace.doov.customdialogues.platformadbottomdialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminCourseAdapter extends RecyclerView.Adapter<AdminCourseAdapter.ViewHolder> {

    private Context mcontext;
    private List<CourseModel> courselist;
    private AdminPortalViewModel apviewModel;

    public AdminCourseAdapter(Context mcontext, List<CourseModel> courselist) {
        this.courselist=courselist;
        this.mcontext=mcontext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.admin_courselay,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminCourseAdapter.ViewHolder holder, int position) {
                Picasso.get().load(courselist.get(position).getCourseimgurl()).resize(300,300).into(holder.img);
                holder.name.setText(courselist.get(position).getCoursename());
                holder.field.setText(courselist.get(position).getCoursefield());
                Picasso.get().load(R.drawable.yellow_options_icon).into(holder.optbtn);
                String fordreams=courselist.get(position).getFordreams();
                String link=courselist.get(position).getLink();
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgurl",courselist.get(position).getCourseimgurl());
                bundle.putString("name",courselist.get(position).getCoursename());
                bundle.putInt("position",position);
                bundle.putString("field",courselist.get(position).getCoursefield());
                bundle.putString("fordreams",fordreams);
                bundle.putString("courselink",link);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                com.blurspace.doov.customdialogues.courseadbottomdialog courseadbottomdialog=new courseadbottomdialog();
                courseadbottomdialog.setArguments(bundle);
               courseadbottomdialog.show(fm,"courseadbottomdialog");
            }
        });
        holder.optbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgurl",courselist.get(position).getCourseimgurl());
                bundle.putString("name",courselist.get(position).getCoursename());
                bundle.putInt("position",position);
                bundle.putString("field",courselist.get(position).getCoursefield());
                bundle.putString("fordreams",fordreams);
                bundle.putString("courselink",link);
                FragmentActivity activity = (FragmentActivity)(mcontext);
                FragmentManager fm = activity.getSupportFragmentManager();
                com.blurspace.doov.customdialogues.courseadbottomdialog courseadbottomdialog=new courseadbottomdialog();
                courseadbottomdialog.setArguments(bundle);
                courseadbottomdialog.show(fm,"courseadbottomdialog");

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
        return courselist.size();
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
            img=itemView.findViewById(R.id.admincourseimg);
            name=itemView.findViewById(R.id.admincoursecardname);
            field=itemView.findViewById(R.id.admincoursecardfield);
            optbtn=itemView.findViewById(R.id.admincoursecardoptions);
            View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_admin_portal,null,false);
            card=itemView.findViewById(R.id.admincoursecard);
            shimmerFrameLayout=view.findViewById(R.id.admindreamshimmer);

        }
    }
}
