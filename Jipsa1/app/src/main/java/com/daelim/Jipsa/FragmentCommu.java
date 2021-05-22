package com.daelim.Jipsa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

    //ListView 변수
    static final String[] LIST_MENU = {"fff", "LIST2", "LIST3"} ;
    //ListView 변수

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
    String id;
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
        //새로운 코드(List View) 시작

        ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) view.findViewById(R.id.listview1) ;
        listview.setAdapter(Adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 코드 계속 ...

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                mainActivity.setFrag(8);

            }
        }) ;


        //새로운 코드(List View) 끝
//db

        
        //원래코드 변수스타트

//        edt_title1 = view.findViewById(R.id.Ed_ctitle1);
//        edt_title2 = view.findViewById(R.id.Ed_ctitle2);
//        edt_title3 = view.findViewById(R.id.Ed_ctitle3);
//        edt_date1 = view.findViewById(R.id.Ed_cdate1);
//        edt_date2 = view.findViewById(R.id.Ed_cdate2);
//        edt_date3 = view.findViewById(R.id.Ed_cdate3);
//        edt_name1 = view.findViewById(R.id.Ed_cname1);
//        edt_name2 = view.findViewById(R.id.Ed_cname2);
//        edt_name3 = view.findViewById(R.id.Ed_cname3);
//        edt_views1 = view.findViewById(R.id.Ed_views1);
//        edt_views2 = view.findViewById(R.id.Ed_views2);
//        edt_views3 = view.findViewById(R.id.Ed_views3);


        //원래코드 변수끝

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
        
        //원래 코드 스타트

//        cbl1 = view.findViewById(R.id.CBL1);
//        cbl1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
//            }
//        });
//
//        cbl2 = view.findViewById(R.id.CBL2);
//        cbl2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
//            }
//        });
//
//        cbl3 = view.findViewById(R.id.CBL3);
//        cbl3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
//            }
//        });
        //원래코드 끝


        return view;
    }
    public void set_id(String id){
        this.id = id;
    }
}

