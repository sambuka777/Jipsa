package com.daelim.Jipsa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentChat extends Fragment {

    private View view;
    Button cp_btn1,cp_btn2;
    MainActivity mainActivity;
    LinearLayout chat1,chat2;
    String id;
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
        System.out.println(id);
        chat1 = view.findViewById(R.id.chat_1);
        chat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mainActivity.setFrag(5);
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("members").document(id);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                System.out.println(document.get("name").toString());
                                Intent intent = new Intent(getActivity(), Chatroom2.class);
                                intent.putExtra("id",document.get("name").toString());

                                startActivity(intent);
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });

            }
        });

//        chat2 = view.findViewById(R.id.chat_2);
//        chat2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.setFrag(5);
//            }
//        });


        return view;    }
    public void set_id(String id){
        this.id = id;
    }

}

