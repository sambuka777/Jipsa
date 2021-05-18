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

public class FragmentCommu extends Fragment {

    private View view;
    Button btn_write;
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
        view = inflater.inflate(R.layout.activity_commu, container, false);

        btn_write = view.findViewById(R.id.btn_Write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(6);
            }
        });

        return view;    }
}

