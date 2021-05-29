package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentMyPage extends Fragment {

    private View view;
    MainActivity mainActivity;
    TextView txt_username,txt_useremail;
    LinearLayout LayoutNotice, LayoutQnA, LayoutUser, LayoutLogout;

    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }


    String id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage, container, false);

        LayoutNotice = view.findViewById(R.id.layout_notice);
        LayoutQnA = view.findViewById(R.id.layout_qna);
        LayoutUser = view.findViewById(R.id.layout_user);

        LayoutNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(9, null);
            }
        });

        LayoutQnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(12, null);
            }
        });

        LayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(11, null);
            }
        });

        return view;    }
    public void set_id(String id){
        this.id = id;
    }
}

