package com.blurspace.doov.Repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blurspace.doov.Models.CourseModel;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.Models.searchfieldModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeRepo {

    private HomeRepo instance;
    private ArrayList<DreamsModel> dreamlist= new ArrayList<>();
    private ArrayList<PlatformModel> platformlist= new ArrayList<>();
    private ArrayList<CourseModel> courselist= new ArrayList<>();
    private MutableLiveData<List<DreamsModel>> dreamdata= new MutableLiveData<>();
    private MutableLiveData<List<PlatformModel>> platformdata= new MutableLiveData<>();
    private MutableLiveData<List<CourseModel>> coursedata= new MutableLiveData<>();
    private DatabaseReference mdbref;
    private DatabaseReference mdb;
    private StorageReference mst;
    private ArrayList<DreamsModel> searchreslist= new ArrayList<>();
    private MutableLiveData<List<DreamsModel>> searchresdata= new MutableLiveData<>();
    private Context mcontext;

    private MutableLiveData<List<String>> searchfieldModel=new MutableLiveData<>();
    private List<String> searchfieldList=new ArrayList<>();


    public HomeRepo(Context mcontext) {
        this.mcontext=mcontext;
        mdb= FirebaseDatabase.getInstance().getReference("AppData");
        mdbref=FirebaseDatabase.getInstance().getReference("AppData");
        mst= FirebaseStorage.getInstance().getReference("AppData");
    }
    public HomeRepo getInstance() {
        if(instance==null){
            instance=new HomeRepo(mcontext);

        }
        return instance;
    }

    public MutableLiveData<List<DreamsModel>> returnDreamData() {
        getDreamDatafromSource();
        if(dreamlist==null) {
            dreamdata.setValue(null);
        }

        dreamdata.setValue(dreamlist);

        return dreamdata;
    }

    public MutableLiveData<List<DreamsModel>> returnsearchresData() {
        getsearchresfromSource();
        if(searchreslist==null) {
            searchresdata.setValue(null);
        }

        searchresdata.setValue(searchreslist);

        return searchresdata;
    }



    public MutableLiveData<List<PlatformModel>> returnPlatformData() {
        getPlatformDatafromSource();
        if(platformlist==null) {
            platformdata.setValue(null);
        }
        platformdata.setValue(platformlist);
        return platformdata;
    }
    public MutableLiveData<List<CourseModel>> returnCourseData() {
        getCourseDatafromSource();
        if(coursedata==null) {
            coursedata.setValue(null);
        }
        coursedata.setValue(courselist);
        return coursedata;
    }

    public MutableLiveData<List<String>> returnsearchfieldModel() {
        getfieldfromSource();
        if(searchfieldList==null) {
            searchfieldModel.setValue(null);
        }
        searchfieldList.add("All");
        searchfieldModel.setValue(searchfieldList);
        return searchfieldModel;
    }

    private void getfieldfromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("DreamList");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    searchfieldList.add(snapshot1.getKey().toString());

                }
                searchfieldModel.postValue(searchfieldList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDreamDatafromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("DreamList") ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    for (DataSnapshot subsnapshot:snapshot1.getChildren()) {
                        dreamlist.add(subsnapshot.getValue(DreamsModel.class));
                    }
                    dreamdata.postValue(dreamlist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getsearchresfromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("DreamList");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    for (DataSnapshot subsnapshot:snapshot1.getChildren()) {
                            searchreslist.add(subsnapshot.getValue(DreamsModel.class));


                    }
                    searchresdata.postValue(searchreslist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPlatformDatafromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("PlatformList") ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    for (DataSnapshot subsnapshot:snapshot1.getChildren()) {
                        platformlist.add(subsnapshot.getValue(PlatformModel.class));
                    }
                    platformdata.postValue(platformlist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCourseDatafromSource() {
        mdbref= FirebaseDatabase.getInstance().getReference("AppData");
        Query query=mdbref.child("CourseList") ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    for (DataSnapshot subsnapshot:snapshot1.getChildren()) {
                        courselist.add(subsnapshot.getValue(CourseModel.class));
                    }
                    coursedata.postValue(courselist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
