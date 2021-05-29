package com.daelim.Jipsa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentMyPage extends Fragment {

    private View view;
    MainActivity mainActivity;
    TextView txt_username,txt_useremail;
    LinearLayout LayoutNotice, LayoutQnA, LayoutUser, LayoutLogout;
    String id;
    FirebaseFirestore db;
    private static final String TAG = "LoginActivity";
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
        view = inflater.inflate(R.layout.activity_mypage, container, false);
        //db
        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("members").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData()+document.get("pwd"));
                        txt_useremail = view.findViewById(R.id.tv_inforEm);
                        txt_username = view.findViewById(R.id.tv_InforUN);
                        txt_username.setText(document.get("name").toString());
                        txt_useremail.setText(document.get("email").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
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

