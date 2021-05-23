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

public class FragmentCommu extends Fragment {

    //ListView 변수
    static final String[] LIST_MENU = {"fff", "LIST2", "LIST3"} ;
    //ListView 변수


    private Context context;
    private List list;
    ArrayList<Actor> actors;
    ListView customListView;
    private static CustomAdapter customAdapter;


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


        actors = new ArrayList<>();
        actors.add(new Actor("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));
        actors.add(new Actor("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        actors.add(new Actor("Cho Yeo-jeong", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5MgWM8pkUiYkj9MEaEpO0Ir1FD9.jpg", "Cho Yeo-jeong (조여정) is a South Korean actress. Born on February 10, 1981, she began her career as a model in 1997 at the age of 16 and launched her acting career two years later. She is best known for her roles in the provocative period films “The Servant” (2010) and “The Concubine” (2012) and the television dramas “I Need Romance” (2011), “Haeundae Lovers” (2012), “Divorce Lawyer in Love” (2015) and “Perfect Wife” (2017)."));
        actors.add(new Actor("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        actors.add(new Actor("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));

        customListView = (ListView) view.findViewById(R.id.listview);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), actors);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.Ed_cname3).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });


//db
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


//        cbl3 = view.findViewById(R.id.CBL3);
//        cbl3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.setFrag(8);//일단 글쓰는곳으로 임시 이동 연결
//            }
//        });


        return view;    }
    public void set_id(String id){
        this.id = id;
    }


    class Actor {
        private String name;
        private String summary;
        private String thumb_url;

        public Actor(String name, String thumb_url, String summary) {
            this.name = name;
            this.summary = summary;
            this.thumb_url = thumb_url;
        }

        public String getName() {
            return name;
        }

        public String getSummary() {
            return summary;
        }

        public String getThumb_url() {
            return thumb_url;
        }
    }



}

