package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentQnApreView extends Fragment {

    private View view;
    private MainActivity mainActivity;

    ImageButton ImgbtnQpvBack;
    Button btnQpvCheck;

    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_qnapreview, container, false);

        ImgbtnQpvBack = view.findViewById(R.id.Imgbtn_QpvBack);
        ImgbtnQpvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(12, null);
            }
        });

        btnQpvCheck = view.findViewById(R.id.btn_QpvCheck);
        btnQpvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(12, null);
            }
        });

        return view;
    }

}
