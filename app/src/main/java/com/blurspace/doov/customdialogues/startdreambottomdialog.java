package com.blurspace.doov.customdialogues;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blurspace.doov.Act_CurrDream;
import com.blurspace.doov.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class startdreambottomdialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dreamstart_bottomdialog,container);
        Button cont=view.findViewById(R.id.closebtn);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the currentdream Activity.
                dismiss();
                getActivity().startActivity(new Intent(getActivity(), Act_CurrDream.class));
                getActivity().overridePendingTransition(R.anim.slide_in_down,R.anim.fade);

            }
        });
        return view;
    }
}
