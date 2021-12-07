package com.blurspace.doov.ViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blurspace.doov.Models.CurrentDreamModel;
import com.blurspace.doov.Repositories.dreamRepo;
import com.blurspace.doov.Models.DreamsModel;
import java.util.List;

public class dreamViewModel  extends ViewModel {
    public MutableLiveData<CurrentDreamModel.curdreammodel> getCurrDreamModel() {
        return crrDreamModel;
    }

    public MutableLiveData<List<DreamsModel>> getPrevDreamModel() {
        return prevDreamModel;
    }

    public MutableLiveData<List<DreamsModel>> getFavDreamModel() {
        return favDreamModel;
    }

    private MutableLiveData<CurrentDreamModel.curdreammodel> crrDreamModel;
    private MutableLiveData<List<DreamsModel>> prevDreamModel;
    private MutableLiveData<List<DreamsModel>> favDreamModel;
    private Context mcontext;
    private com.blurspace.doov.Repositories.dreamRepo dreamRepo=new dreamRepo(mcontext);

    public void initwork(Context mcontext) {
        this.mcontext=mcontext;
        if(crrDreamModel!=null) {
            return;
        }
        if(prevDreamModel!=null) {
            return;
        }
        if(favDreamModel!=null) {
            return;
        }
        crrDreamModel=dreamRepo.getInstance().returncrrDream();
        prevDreamModel=dreamRepo.getInstance().returnprevDream();
        favDreamModel=dreamRepo.getInstance().returnfavDream();

    }
}
