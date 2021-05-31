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

public class FragmentNoticeView extends Fragment {

    private View view;
    private MainActivity mainActivity;

    ImageButton ImgbtnNvBack;
    Button btnNvCheck;
    String db_id;
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
        view = inflater.inflate(R.layout.activity_noticeview, container, false);

        ImgbtnNvBack = view.findViewById(R.id.Imgbtn_NvBack);
        ImgbtnNvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(9, null);
            }
        });

        btnNvCheck = view.findViewById(R.id.btn_NvCheck);
        btnNvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(9, null);
            }
        });

        return view;
    }
    public void set_commid(String id){
        db_id = id;
    }

}
