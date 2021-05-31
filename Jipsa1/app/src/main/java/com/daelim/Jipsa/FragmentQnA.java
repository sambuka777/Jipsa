package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FragmentQnA extends Fragment {

    private View view;
    private MainActivity mainActivity;

    Button BtnQnAUpload;
    ImageButton ImgBtnQnABack;
    EditText EdQnATitle, EdQnAContents;
    FirebaseFirestore db;
    String id,memo;
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
        view = inflater.inflate(R.layout.activity_qna, container, false);
        EdQnATitle = view.findViewById(R.id.ed_QnATitle);
        EdQnAContents = view.findViewById(R.id.ed_QnAContents);
        ImgBtnQnABack = view.findViewById(R.id.Imgbtn_QnABack);
        ImgBtnQnABack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(12, null);
            }
        });

        BtnQnAUpload = view.findViewById(R.id.btn_QnAUpload);
        BtnQnAUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> comm = new HashMap<>();

                db = FirebaseFirestore.getInstance();
                comm.put("title", EdQnATitle.getText().toString());
                memo = EdQnAContents.getText().toString();
                memo = memo.replaceAll("(\r\n|\r|\n|\n\r)", "InE");
                comm.put("memoOfQ", memo);
                comm.put("date", Timestamp.now());
                comm.put("memoOfA",null);
                comm.put("isAnser",false);
                db.collection("QnAof").document(id).collection("Qoftitle").add(comm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
//                DocumentReference docRef = db.collection("members").document(id).collection("Qoftitle");
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//
//                                db.collection("commity")
//                                        .add(comm)
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                            @Override
//                                            public void onSuccess(DocumentReference documentReference) {
//                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.w(TAG, "Error adding document", e);
//                                            }
//                                        });
//                            } else {
//                                Log.d(TAG, "No such document");
//                            }
//                        } else {
//                            Log.d(TAG, "get failed with ", task.getException());
//                        }
//                    }
//                });
                mainActivity.setFrag(12, null);
            }
        });

        return view;
    }
    public void set_id(String id){
        this.id = id;
    }

}
