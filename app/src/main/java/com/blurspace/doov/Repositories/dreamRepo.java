package com.blurspace.doov.Repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blurspace.doov.Models.CurrentDreamModel;
import com.blurspace.doov.Models.DreamsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class dreamRepo {
     private DatabaseReference mdbref;
    private DatabaseReference mdbref1;
    private DatabaseReference mdbref2;
     private FirebaseAuth mauth;
    private Context mcontext;
    private dreamRepo instance;
    private MutableLiveData<CurrentDreamModel.curdreammodel> crrDreamData= new MutableLiveData<>();
    private ArrayList<DreamsModel> prevDreamlist= new ArrayList<>();
    private MutableLiveData<List<DreamsModel>> prevDreamData= new MutableLiveData<>();
    private ArrayList<DreamsModel> favDreamlist= new ArrayList<>();
    private MutableLiveData<List<DreamsModel>> favDreamData= new MutableLiveData<>();
    public dreamRepo getInstance() {
        if(instance==null) {
            instance=new dreamRepo(mcontext);
        }
        return instance;
    }
    public dreamRepo(Context mcontext) {
        this.mcontext=mcontext;
        mdbref=FirebaseDatabase.getInstance().getReference("Users");
        mdbref1=FirebaseDatabase.getInstance().getReference("Users");
        mdbref2=FirebaseDatabase.getInstance().getReference("Users");
        mauth=FirebaseAuth.getInstance();

    }
    public MutableLiveData<CurrentDreamModel.curdreammodel> returncrrDream() {
        getcrrDreamFromDb();
        return crrDreamData;
    }

    public MutableLiveData<List<DreamsModel>> returnprevDream() {
        getprevDreamFromDb();
        if(prevDreamlist==null) {
            prevDreamData.setValue(null);
        }
        prevDreamData.setValue(prevDreamlist);
        return prevDreamData;
    }


    public MutableLiveData<List<DreamsModel>> returnfavDream() {
        getfavDreamFromDb();
        if(favDreamlist==null) {
            favDreamData.setValue(null);
        }

        favDreamData.setValue(favDreamlist);
        return favDreamData;
    }

    private void getprevDreamFromDb() {
        mdbref2.child(mauth.getCurrentUser().getUid()).child("PreviousDreams").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot subsnapshot:snapshot.getChildren()) {
                    DreamsModel prevModel=subsnapshot.getValue(DreamsModel.class);
                    prevDreamlist.add(prevModel);
                }
                prevDreamData.postValue( prevDreamlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getfavDreamFromDb() {
        mdbref.child(mauth.getCurrentUser().getUid()).child("FavoriteDreams").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot subsnapshot:snapshot.getChildren()) {
                    DreamsModel favModel=subsnapshot.getValue(DreamsModel.class);
                    favDreamlist.add(favModel);
                }
                favDreamData.postValue(favDreamlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getcrrDreamFromDb() {
        mdbref1.child(mauth.getCurrentUser().getUid()).child("CurrentDream").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CurrentDreamModel.curdreammodel curdreammodel=snapshot.getValue(CurrentDreamModel.curdreammodel.class);
                crrDreamData.postValue(curdreammodel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("current",error.toString());
            }
        });
    }

}
