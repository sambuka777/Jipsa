package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    private View view;

    ImageView ViewPager;
    TextView TvComuTitle, TvComuList1, TvComuList2, TvComuList3, TvComuList4, TvLostAnimal;
    Button BtnComuMore, BtnLostAniMore;
    ImageButton IbLost1, IbLost2, IbLost3;

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
        view = inflater.inflate(R.layout.activity_home, container, false);

        BtnComuMore = view.findViewById(R.id.btn_ComuMore);
        BtnComuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.setFrag(2);
            }
        });

        BtnLostAniMore = view.findViewById(R.id.btn_LostAniMore);
        BtnLostAniMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.setFrag(7);
            }
        });

        return view;
    }



}


