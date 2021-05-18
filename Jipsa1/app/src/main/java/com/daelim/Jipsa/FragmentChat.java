package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentChat extends Fragment {

    private View view;
    Button cp_btn1,cp_btn2;
    MainActivity mainActivity;

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
        view = inflater.inflate(R.layout.activity_chat, container, false);

        cp_btn1 = view.findViewById(R.id.cp_btn1);
        cp_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(5);
            }
        });

        cp_btn2 = view.findViewById(R.id.cp_btn2);
        cp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(5);
            }
        });

        return view;    }
}

