package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
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

public class FragmentNotice extends Fragment {

    private View view;
    private MainActivity mainActivity;
    FirebaseFirestore db;
    ImageButton ImgBtnNoticeBack;
    private static final String TAG = "FragmentCommu";
    ArrayList<String> db_id;
    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }

    ArrayList<Notitle> notitles;
    ListView ListNotice;
    private static NoticeAdapter noticeAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_notice, container, false);
//db

        db_id = new ArrayList<>();
        notitles = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("Notice").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        Date from = new Date(document.getTimestamp("date").toDate().getTime());
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String to = transFormat.format(from);
                        System.out.println(to);

                        notitles.add(new Notitle(document.get("title").toString(),to));
                        db_id.add(document.getId());
                    }
                    ListNotice = view.findViewById(R.id.list_Notice);
                    noticeAdapter = new NoticeAdapter(getContext(), notitles);
                    ListNotice.setAdapter(noticeAdapter);
                    ListNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = view.findViewById(R.id.tv_listNTitle).getTag().toString();
                            Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
                            mainActivity.setFrag(13, db_id.get(position));
                        }
                    });

                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });




        ImgBtnNoticeBack = view.findViewById(R.id.Imgbtn_NoticeBack);
        ImgBtnNoticeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(4, null);
            }
        });

        return view;
    }

    class Notitle {
        private String noticeTitle;
        private String noticeDate;

        public Notitle(String noticeTitle, String noticeDate) {
            this.noticeTitle = noticeTitle;
            this.noticeDate = noticeDate;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public String getNoticeDate() {
            return noticeDate;
        }
    }
}
