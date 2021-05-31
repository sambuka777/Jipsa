package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentQnApreView extends Fragment {

    private View view;
    private MainActivity mainActivity;
    String commid;
    ImageButton ImgbtnQpvBack;
    Button btnQpvCheck;
    TextView txt_title,txt_memo,txt_anser,txt_date;
    private static final String TAG = "FragmentCommu";
    FirebaseFirestore db;
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
        view = inflater.inflate(R.layout.activity_qnapreview, container, false);
        txt_title = view.findViewById(R.id.tv_QpvTitle);
        txt_date = view.findViewById(R.id.tv_QpvDate);
        txt_anser = view.findViewById(R.id.tv_QpvContents);

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
                        txt_title.setText(document.get("title").toString());
                        Date from = new Date(document.getTimestamp("date").toDate().getTime());
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String to = transFormat.format(from);
                        System.out.println(to);
                        txt_date.setText(to);
                        txt_anser.setText("memoOfA");
//                        txt_id.setText(document.get("name").toString());
//                        txt_memo.setText(Html.fromHtml(document.get("memo").toString().replaceAll("InE", "<br/>")));
//                        txt_title.setText(document.get("title").toString());
//                        viewAnInt = Integer.parseInt(document.get("viewnum").toString());
//                        viewAnInt =viewAnInt+1;
//                        txt_viewnum.setText(viewAnInt+"");

//                        txt_date.setText(to);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        ImgbtnQpvBack = view.findViewById(R.id.Imgbtn_QpvBack);
        ImgbtnQpvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(12, null);
            }
        });

        btnQpvCheck = view.findViewById(R.id.btn_QpvCheck);
        btnQpvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(12, null);
            }
        });

        return view;
    }
    public void set_QnAid(String id){
        commid = id;
    }
}
