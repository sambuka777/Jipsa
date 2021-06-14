package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentCommu extends Fragment{

    //ListView 변수

    //ListView 변수


    private Context context;
    private List list;
    ArrayList<Actor> actors ;
    ListView customListView;
    private static CustomAdapter customAdapter;


    private View view;
    ImageButton btn_write,btn_home;
    MainActivity mainActivity;
    EditText edt_title1, edt_title2, edt_title3, edt_name1, edt_name2, edt_name3, edt_date1, edt_date2, edt_date3, edt_views1, edt_views2, edt_views3;
    LinearLayout cbl1, cbl2, cbl3;
    FirebaseFirestore db;
    ArrayList<String> db_id;
    ArrayList<String> db_title = new ArrayList<String>();
    ArrayList<String> db_memo = new ArrayList<String>();
    ArrayList<String> db_date = new ArrayList<String>();
    ArrayList<Integer> db_viewnum = new ArrayList<Integer>();
    int temp = 0;
    String id;
    String docu_id;
    private static final String TAG = "FragmentCommu";

    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();

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
        actors = new ArrayList<>();
        db_id = new ArrayList<String>();
        view = inflater.inflate(R.layout.activity_commu, container, false);
        db = FirebaseFirestore.getInstance();
//        CollectionReference citiesRef = db.collection("commity");
//        db.collection("commity").get;
        db.collection("commity").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        Date from = new Date(document.getTimestamp("date").toDate().getTime());
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String to = transFormat.format(from);
                        System.out.println(to);

                        temp++;

                        actors.add(new Actor(document.get("name").toString(), to, /*document.get("memo").toString(),*/ document.get("title").toString(), document.get("viewnum").toString()));
                        db_id.add(document.getId());
                    }
                    customListView = (ListView) view.findViewById(R.id.listview);
                    CustomAdapter customAdapter = new CustomAdapter(getContext(), actors);
                    customListView.setAdapter(customAdapter);
                    customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                            //각 아이템을 분간 할 수 있는 position과 뷰
                            String selectedItem = (String) view.findViewById(R.id.Ed_cname3).getTag().toString();
                            //Toast.makeText(getContext(), "Clicked: " + position + " " + selectedItem, Toast.LENGTH_SHORT).show();
                            mainActivity.setFrag(8,db_id.get(position));
                        }
                    });

                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });



        btn_write = view.findViewById(R.id.write_btn);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(6,null);
            }
        });
        btn_home = view.findViewById(R.id.home_btn);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(0,null);
            }
        });

        return view;
    }


    public void set_id(String id) {
        this.id = id;
    }


    class Actor {
        private String name;
        private String cdate;
//        private String memo;
        private String title;
        private String viewn;
        //private String thumb_url;

        public Actor(String name, /*String thumb_url,*/ String cdate, /*String memo,*/ String title, String viewn) {
            this.name = name;
            this.cdate = cdate;
//            this.memo = memo;
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

//        public String getmemo() {
//            return memo;
//        }

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
