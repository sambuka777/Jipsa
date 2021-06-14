package com.daelim.Jipsa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMyPage extends Fragment {

    private View view;
    MainActivity mainActivity;
    TextView txt_username,txt_useremail;
    LinearLayout LayoutNotice, LayoutQnA, LayoutUser, LayoutLogout;
    String id, img;
    CircleImageView img_prof;
    FirebaseFirestore db;
    private static final String TAG = "LoginActivity";
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
        view = inflater.inflate(R.layout.activity_mypage, container, false);

        //db
        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("members").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData()+document.get("pwd"));
                        txt_useremail = view.findViewById(R.id.tv_inforEm);
                        txt_username = view.findViewById(R.id.tv_InforUN);
                        txt_username.setText(document.get("name").toString());
                        txt_useremail.setText(document.get("email").toString());
                        img_prof = (CircleImageView) Objects.requireNonNull(getActivity()).findViewById(R.id.prof);

                        img = document.get("image").toString();

                        //프로필 이미지 세팅
                        if(img.equals("null")) {
                            img_prof.setImageResource(R.drawable.profile_user);
                        }else{
                            FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
                            StorageReference storageRef = firebaseStorage.getReference();
                            storageRef.child("profile/"+img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(FragmentMyPage.this).load(uri).into(img_prof);
                                }
                            });
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        LayoutNotice = view.findViewById(R.id.layout_notice);
        LayoutQnA = view.findViewById(R.id.layout_qna);
        LayoutUser = view.findViewById(R.id.layout_user);
        LayoutLogout =view.findViewById(R.id.layout_logout);
        LayoutNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(9, null);
            }
        });

        LayoutQnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(12, null);
            }
        });

        LayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(11, id);
            }
        });
        LayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("로그아웃");
                builder.setMessage("정말 로그아웃 하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent JoinIntent = new Intent(getContext(), LoginActivity.class);
                        startActivity(JoinIntent);
                    }
                });
                builder.setNegativeButton("취소",null);
                builder.create().show();

            }
        });
        return view;    }
    public void set_id(String id){
        this.id = id;
    }
}

