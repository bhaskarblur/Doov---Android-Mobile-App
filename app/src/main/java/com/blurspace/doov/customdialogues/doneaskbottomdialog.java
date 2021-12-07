package com.blurspace.doov.customdialogues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blurspace.doov.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class doneaskbottomdialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.doneask_bottomdialog,container);
        return view;
    }
}
