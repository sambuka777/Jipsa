package com.daelim.Jipsa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class FragmentCommu extends Fragment {

    private View view;
    Button btn_write;
    MainActivity mainActivity;
    EditText edt_title1,edt_title2,edt_title3,edt_name1
            ,edt_name2,edt_name3,edt_date1,edt_date2,edt_date3
            ,edt_views1,edt_views2,edt_views3;
    LinearLayout cbl1,cbl2,cbl3;
    FirebaseFirestore db;
    ArrayList<String> db_id = new ArrayList<String>();
    ArrayList<String> db_title = new ArrayList<String>();
    ArrayList<String> db_memo = new ArrayList<String>();
    ArrayList<String> db_date = new ArrayList<String>();
    ArrayList<Integer> db_viewnum = new ArrayList<Integer>();
    int temp = 0;
    private static final String TAG = "FragmentCommu";
    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
        temp = 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("실행 2");
        view = inflater.inflate(R.layout.activity_commu, container, false);
//db
        edt_title1 = view.findViewById(R.id.Ed_ctitle1);
        edt_title2 = view.findViewById(R.id.Ed_ctitle2);
        edt_title3 = view.findViewById(R.id.Ed_ctitle3);
        edt_date1 = view.findViewById(R.id.Ed_cdate1);
        edt_date2 = view.findViewById(R.id.Ed_cdate2);
        edt_date3 = view.findViewById(R.id.Ed_cdate3);
        edt_name1 = view.findViewById(R.id.Ed_cname1);
        edt_name2 = view.findViewById(R.id.Ed_cname2);
        edt_name3 = view.findViewById(R.id.Ed_cname3);
        edt_views1 = view.findViewById(R.id.Ed_views1);
        edt_views2 = view.findViewById(R.id.Ed_views2);
        edt_views3 = view.findViewById(R.id.Ed_views3);
        db = FirebaseFirestore.getInstance();

        db.collection("commity").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                db_id.add(document.get("id").toString());
                                db_title.add(document.get("title").toString());
                                db_memo.add(document.get("memo").toString());
                                Date from = new Date( document.getTimestamp("date").toDate().getTime());
                                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String to = transFormat.format(from);
                                System.out.println(to);
                                db_date.add(to);
                                db_viewnum.add(Integer.parseInt(document.get("viewnum").toString()));
                                temp++;
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        btn_write = view.findViewById(R.id.btn_Write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(6);
            }
        });

        cbl1 = view.findViewById(R.id.CBL1);
        cbl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
            }
        });

        cbl2 = view.findViewById(R.id.CBL2);
        cbl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
            }
        });

        cbl3 = view.findViewById(R.id.CBL3);
        cbl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
            }
        });


        return view;
    }
}

