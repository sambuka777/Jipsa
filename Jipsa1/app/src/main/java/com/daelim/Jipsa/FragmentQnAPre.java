package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentQnAPre extends Fragment {

    private View view;
    private MainActivity mainActivity;

    ImageButton ImgBtnQpBack, ImgBtnQpWrite;

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
        view = inflater.inflate(R.layout.activity_qnapre, container, false);

        ImgBtnQpBack = view.findViewById(R.id.Imgbtn_QpBack);
        ImgBtnQpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(4, null);
            }
        });

        ImgBtnQpWrite = view.findViewById(R.id.Imgbtn_QpWrite);
        ImgBtnQpWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(10, null);
            }
        });


        return view;
    }

}
