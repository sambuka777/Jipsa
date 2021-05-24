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
import java.util.List;
import java.util.Objects;

public class FragmentCommu extends Fragment implements Runnable{

    //ListView 변수

    //ListView 변수


    private Context context;
    private List list;
    ArrayList<Actor> actors;
    ListView customListView;
    private static CustomAdapter customAdapter;


    private View view;
    Button btn_write;
    MainActivity mainActivity;
    EditText edt_title1, edt_title2, edt_title3, edt_name1, edt_name2, edt_name3, edt_date1, edt_date2, edt_date3, edt_views1, edt_views2, edt_views3;
    LinearLayout cbl1, cbl2, cbl3;
    FirebaseFirestore db;
    ArrayList<String> db_id = new ArrayList<String>();
    ArrayList<String> db_title = new ArrayList<String>();
    ArrayList<String> db_memo = new ArrayList<String>();
    ArrayList<String> db_date = new ArrayList<String>();
    ArrayList<Integer> db_viewnum = new ArrayList<Integer>();
    int temp = 0;
    String id;
    private static final String TAG = "FragmentCommu";

    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
        //
        run();
        System.out.println("실행 1");


    }

    public void onDetach() {
        super.onDetach();
        mainActivity = null;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("실행 2");
        view = inflater.inflate(R.layout.activity_commu, container, false);


//..리스트뷰
//        actors = new ArrayList<>();
//        for(int i =  0; i<=temp-1; i++) {
//            actors.add(new Actor(db_id.get(i), db_date.get(i), db_memo.get(i), db_title.get(i), db_viewnum.get(i).toString()));
////            actors.add(new Actor("name1", "date1", "memo1", "title1", "viewn1"));
//        }
//db

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        customListView = (ListView) view.findViewById(R.id.listview);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), actors);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.Ed_cname3).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position + " " + selectedItem, Toast.LENGTH_SHORT).show();
                mainActivity.setFrag(8);
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


//        cbl3 = view.findViewById(R.id.CBL3);
//        cbl3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
//            }
//        });


        return view;
    }

    public void set_id(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        actors = new ArrayList<>();
        actors.add(new Actor("name1", "date1", "memo1", "title1", "viewn1"));

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
                        Date from = new Date(document.getTimestamp("date").toDate().getTime());
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String to = transFormat.format(from);
                        System.out.println(to);
                        db_date.add(to);
                        db_viewnum.add(Integer.parseInt(document.get("viewnum").toString()));
                        temp++;
                        System.out.println(document.get("id").toString());
                        actors.add(new Actor(document.get("id").toString(), to, document.get("memo").toString(), document.get("title").toString(), document.get("viewnum").toString()));
                        actors.add(new Actor("name21", "dat3e1", "memo1", "title1", "viewn1"));
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }


    class Actor {
        private String name;
        private String cdate;
        private String memo;
        private String title;
        private String viewn;
        //private String thumb_url;

        public Actor(String name, /*String thumb_url,*/ String cdate, String memo, String title, String viewn) {
            this.name = name;
            this.cdate = cdate;
            this.memo = memo;
            this.title = title;
            this.viewn = viewn;
            //this.thumb_url = thumb_url;
        }

        public String getName() {
            return name;
        }

        public String getdate() {
            return cdate;
        }

        public String getmemo() {
            return memo;
        }

        public String getviewn() {
            return viewn;
        }

        public String gettitle() {
            return title;
        }

//        public String getThumb_url() {
//            return thumb_url;
//        }


    }
}

