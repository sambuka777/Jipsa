package com.daelim.Jipsa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentChat extends Fragment {

    private View view;
    ImageButton ImgbtnChatBack, ImgbtnChatSerach;
    MainActivity mainActivity;
    LinearLayout chat1;
    String id;
    FirebaseFirestore db;
    private static final String TAG = "FragmentCommu";
    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }
    String temp;
    ArrayList<Chat> chats;
    ListView ListChat;
    private static ChattingAdapter chattingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat, container, false);
        System.out.println(id);
//        db = FirebaseFirestore.getInstance();
//        CollectionReference ref = db.collection("chat");
//
//        ref.whereGreaterThan("user1",id).whereLessThan("user2",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//
//                    }
//
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.getException());
//                }
//            }
//        });
//----------------------------------------------------------
        chats = new ArrayList<>();
        chats.add(new Chat("관리자", "만나서 반갑습니다 ㅎㅎ", "2021-06-10"));//여기


        ListChat = view.findViewById(R.id.list_Chat);
        chattingAdapter = new ChattingAdapter(getContext(), chats);

        ListChat.setAdapter(chattingAdapter);
        ListChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = view.findViewById(R.id.ch_name1).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Chatroom2.class);
                intent.putExtra("id", "관리자2");//여기
                startActivity(intent);
            }

//            FirebaseFirestore db;
//            db = FirebaseFirestore.getInstance();
//
//            DocumentReference docRef = db.collection("members").document(id);
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());
//                            System.out.println(document.get("name").toString());
//                            Intent intent = new Intent(getActivity(), Chatroom2.class);
//                            intent.putExtra("id",document.get("name").toString());
//
//                            startActivity(intent);
//                        } else {
//                            Log.d("TAG", "No such document");
//                        }
//                    } else {
//                        Log.d("TAG", "get failed with ", task.getException());
//                    }
//                }
//            });

        });

//        chat1 = view.findViewById(R.id.chat_1);
//        chat1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //mainActivity.setFrag(5);
//                FirebaseFirestore db;
//                db = FirebaseFirestore.getInstance();
//
//                DocumentReference docRef = db.collection("members").document(id);
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
//                                System.out.println(document.get("name").toString());
//                                Intent intent = new Intent(getActivity(), Chatroom2.class);
//                                intent.putExtra("id",document.get("name").toString());
//
//                                startActivity(intent);
//                            } else {
//                                Log.d("TAG", "No such document");
//                            }
//                        } else {
//                            Log.d("TAG", "get failed with ", task.getException());
//                        }
//                    }
//                });
//
//            }
//        });

//        chat2 = view.findViewById(R.id.chat_2);
//        chat2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.setFrag(5);
//            }
//        });


        return view;
    }

    class Chat {
        private String chatName;
        private String chatContents;
        private String chatDate;

        public Chat(String chatName, String chatContents, String chatDate) {
            this.chatName = chatName;
            this.chatContents = chatContents;
            this.chatDate = chatDate;
        }

        public String getChatName() {
            return chatName;
        }

        public String getChatContents() {
            return chatContents;
        }

        public String getChatDate() {
            return chatDate;
        }
    }

    public void set_id(String id){
        this.id = id;
    }

}

