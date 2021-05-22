package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentComuWrite extends Fragment {

    private View view;
    Button BtnUpload;
    MainActivity mainActivity;
    EditText ed_title,ed_memo;
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
        view = inflater.inflate(R.layout.activity_comuwrite, container, false);
        ed_title = view.findViewById(R.id.ed_WTitle);
        ed_memo = view.findViewById(R.id.ed_WContents);

        BtnUpload = view.findViewById(R.id.btn_Upload);
        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> comm = new HashMap<>();
                comm.put("id",id);
                comm.put("title",ed_title.getText().toString());
                comm.put("memo",ed_memo.getText().toString());
                comm.put("viewnum",0);
                comm.put("date", Timestamp.now());
                db = FirebaseFirestore.getInstance();
                db.collection("commity")
                        .add(comm)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                mainActivity.setFrag(2);
            }
        });

        return view;    }
    public void set_id(String id){
        this.id = id;
    }
}

