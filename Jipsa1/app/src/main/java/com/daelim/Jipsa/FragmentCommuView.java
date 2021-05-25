package com.daelim.Jipsa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentCommuView extends Fragment {

    private View view;
    String id,commid;
    TextView txt_title,txt_memo,txt_id;
    int viewAnInt;
    private static final String TAG = "FragmentCommu";
    FirebaseFirestore db;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_commuview, container, false);
        viewAnInt=0;
        txt_id = view.findViewById(R.id.txt_id);
        txt_memo = view.findViewById(R.id.txt_memo);
        txt_title = view.findViewById(R.id.txt_title);
        System.out.println(commid);
        //db
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("commity").document(commid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData()+document.get("title")+document.get("memo"));
                        txt_id.setText(document.get("id").toString());
                        txt_memo.setText(document.get("memo").toString());
                        txt_title.setText(document.get("title").toString());
                        viewAnInt = Integer.parseInt(document.get("viewnum").toString());
                        db.collection("commity").document(commid).update("viewnum",viewAnInt+1);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        return view;    }
    public void set_id(String id){
        this.id = id;
    }
    public void set_commid(String id){
        commid = id;
    }
}
