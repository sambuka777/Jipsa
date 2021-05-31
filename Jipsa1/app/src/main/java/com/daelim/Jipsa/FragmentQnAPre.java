package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
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

public class FragmentQnAPre extends Fragment {

    private View view;
    private MainActivity mainActivity;

    ImageButton ImgBtnQpBack, ImgBtnQpWrite;
    private static final String TAG = "FragmentCommu";
    FirebaseFirestore db;
    ArrayList<String> db_id;

    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }

    ArrayList<QnApre> qnapres;
    ListView ListQp;
    private static QnApreAdapter qnapreAdapter;
    String id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_qnapre, container, false);
        db_id = new ArrayList<String>();

        qnapres = new ArrayList<>();
        //
        db = FirebaseFirestore.getInstance();
//        CollectionReference citiesRef = db.collection("commity");
//        db.collection("commity").get;
        db.collection("QnAof").document(id).collection("Qoftitle").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        Date from = new Date(document.getTimestamp("date").toDate().getTime());
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String to = transFormat.format(from);
                        System.out.println(to);
                        db_id.add(document.getId());

                        qnapres.add(new QnApre(document.get("memoOfQ").toString(), to));
                        //

                    }
                    ListQp = view.findViewById(R.id.list_Qp);
                    qnapreAdapter = new QnApreAdapter(getContext(), qnapres);
                    ListQp.setAdapter(qnapreAdapter);
                    ListQp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = view.findViewById(R.id.tv_listQpQuestion).getTag().toString();
                            Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
                            mainActivity.setFrag(14, db_id.get(position));
                        }
                    });

                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });


        ImgBtnQpBack = view.findViewById(R.id.Imgbtn_QpBack);
        ImgBtnQpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(4, null);
            }
        });

        ImgBtnQpWrite = view.findViewById(R.id.Imgbtn_QpWrite);
        ImgBtnQpWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(10, null);
            }
        });


        return view;
    }
    public void set_id(String id){
        this.id = id;
    }

    class QnApre {
        private String qpQuestion;
        private String qpDate;

        public QnApre(String qpQuestion, String qpDate) {
            this.qpQuestion = qpQuestion;
            this.qpDate = qpDate;
        }

        public String getQpQuestion() {
            return qpQuestion;
        }

        public String getQpDate() {
            return qpDate;
        }
    }

}
