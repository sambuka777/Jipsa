package com.daelim.Jipsa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

    MainActivity mainActivity;

    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();

        System.out.println("실행 1");


    }

    public void onDetach() {
        super.onDetach();
        mainActivity = null;

    }

    private View view;
    String id,commid;
    TextView txt_title,txt_memo,txt_id,txt_viewnum,txt_date;
    LinearLayout rere;
    ImageButton remore;
    int viewAnInt;
    private static final String TAG = "FragmentCommu";
    FirebaseFirestore db;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_commuview, container, false);
        viewAnInt=0;
        txt_id = view.findViewById(R.id.comuv_name);
        txt_memo = view.findViewById(R.id.txt_memo);
        txt_title = view.findViewById(R.id.txt_title);
        txt_viewnum = view.findViewById(R.id.comuv_vn);
        txt_date = view.findViewById(R.id.comuv_wd);
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
                        txt_id.setText(document.get("name").toString());
                        txt_memo.setText(Html.fromHtml(document.get("memo").toString().replaceAll("InE", "<br/>")));
                        txt_title.setText(document.get("title").toString());
                        viewAnInt = Integer.parseInt(document.get("viewnum").toString());
                        viewAnInt =viewAnInt+1;
                        txt_viewnum.setText(viewAnInt+"");
                        Date from = new Date(document.getTimestamp("date").toDate().getTime());
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String to = transFormat.format(from);
                        System.out.println(to);
                        txt_date.setText(to);
                        db.collection("commity").document(commid).update("viewnum",viewAnInt);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        ImageButton btnback = view.findViewById(R.id.backcomu);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(2,null);
            }
        });

        rere = view.findViewById(R.id.rere);
        remore = view.findViewById(R.id.comumore);

        remore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rere.setVisibility(View.VISIBLE);

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
