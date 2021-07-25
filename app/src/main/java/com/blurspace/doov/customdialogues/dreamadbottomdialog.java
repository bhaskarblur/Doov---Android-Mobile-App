package com.blurspace.doov.customdialogues;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blurspace.doov.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class dreamadbottomdialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.dreamaddbottomdialog,container,false);
       return v;
    }

    public interface dreamadbottomListener {
        void onSaveClick();
        void onDeleteClick();
    }
}
