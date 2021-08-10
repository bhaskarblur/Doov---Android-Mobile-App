package com.blurspace.doov.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blurspace.doov.Models.CourseModel;
import com.blurspace.doov.Models.DreamsModel;
import com.blurspace.doov.Models.PlatformModel;
import com.blurspace.doov.Repositories.HomeRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<DreamsModel>> randomdreamModel;
    private MutableLiveData<List<PlatformModel>> randomplatformModel;
    private MutableLiveData<List<CourseModel>> randomcourseModel;
    private MutableLiveData<List<String>> searchfieldModel;
    private MutableLiveData<List<DreamsModel>> searchresModel;
    private Context mContext;
    private com.blurspace.doov.Repositories.HomeRepo mRepo=new HomeRepo(mContext);

    public void initwork( Context mContext) {
        this.mContext=mContext;
        if(randomdreamModel!=null){
            return;
        }
        if(randomplatformModel!=null){
            return;
        }
        if(randomcourseModel!=null){
            return;
        }
        randomdreamModel=mRepo.getInstance().returnDreamData();
        randomplatformModel=mRepo.returnPlatformData();
        randomcourseModel=mRepo.returnCourseData();
        searchfieldModel=mRepo.getInstance().returnsearchfieldModel();
        searchresModel=mRepo.getInstance().returnsearchresData();

    }

    public LiveData<List<DreamsModel>> getdreamModel() {

        return randomdreamModel;

    }
    public LiveData<List<DreamsModel>> getsearchresModel() {
        return searchresModel;

    }

    public LiveData<List<String>> getsearchfieldModel() {
        return searchfieldModel;
    }
    public LiveData<List<PlatformModel>> getplatformModel() {
        return randomplatformModel;
    }

    public LiveData<List<CourseModel>> getcourseModel() {
        return randomcourseModel;
    }
}
