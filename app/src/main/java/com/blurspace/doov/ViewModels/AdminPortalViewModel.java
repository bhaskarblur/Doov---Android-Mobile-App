package com.blurspace.doov.ViewModels;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blurspace.doov.Adapters.AdminDreamAdapter;
import com.blurspace.doov.AdminPortal;
import com.blurspace.doov.Models.CourseModel;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.Repositories.AdminPortalRepository;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminPortalViewModel extends ViewModel {

    private DatabaseReference mdb;
    private StorageReference mst;
    private Context mcontext;
    private MutableLiveData<List<DreamsModel>> dreamModel;
    private MutableLiveData<List<PlatformModel>> platformModel;
    private MutableLiveData<List<CourseModel>> courseModel;
    private AdminPortalRepository mrepo= new AdminPortalRepository(mcontext);

    public void initwork(Context mcontext) {
        this.mcontext=mcontext;
        if(dreamModel!=null) {
            return;
        }

        mdb=FirebaseDatabase.getInstance().getReference("AppData");
        mst= FirebaseStorage.getInstance().getReference("AppData");
        dreamModel=mrepo.getInstance().returnDreamData();
        platformModel=mrepo.getInstance().returnPlatformData();
        courseModel=mrepo.getInstance().returnCourseData();


    }
     public LiveData<List<DreamsModel>> getdreamModel() {
        return dreamModel;

     }

     public LiveData<List<PlatformModel>> getplatformModel() {
        return platformModel;
     }

    public LiveData<List<CourseModel>> getcourseModel() {
        return courseModel;
    }




}


