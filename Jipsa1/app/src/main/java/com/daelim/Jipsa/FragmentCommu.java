package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCommu extends Fragment {

    private View view;
    Button btn_write;
    MainActivity mainActivity;
    LinearLayout cbl1,cbl2,cbl3;

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
        view = inflater.inflate(R.layout.activity_commu, container, false);

        btn_write = view.findViewById(R.id.btn_Write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(6);
            }
        });

        cbl1 = view.findViewById(R.id.CBL1);
        cbl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
            }
        });

        cbl2 = view.findViewById(R.id.CBL2);
        cbl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
            }
        });

        cbl3 = view.findViewById(R.id.CBL3);
        cbl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
            }
        });


        return view;    }
}

