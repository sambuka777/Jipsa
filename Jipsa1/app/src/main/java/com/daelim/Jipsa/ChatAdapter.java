package com.daelim.Jipsa;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by KPlo on 2018. 10. 28..
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickName;
    String usernick;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_time, my_nickname;
        public TextView TextView_msg,my_msg,my_time;
        public View rootView;
        public LinearLayout nickname;
        public RelativeLayout mychat, userchat;
        public TextView txt_username;
        CircleImageView img_prof;
        FirebaseFirestore db;
        String img;
        public MyViewHolder(View v) {
            super(v);
            /*TextView_nickname = v.findViewById(R.id.TextView_nickname);*/
            TextView_msg = v.findViewById(R.id.TextView_msg);
            my_nickname = v.findViewById(R.id.my_nickname);
            my_msg = v.findViewById(R.id.my_msg);
            my_time = v.findViewById(R.id.my_time);
            nickname = v.findViewById(R.id.nickname);
            mychat = v.findViewById(R.id.my_chat);
            userchat = v.findViewById(R.id.user_chat);
            TextView_time = v.findViewById(R.id.TextView_time);
            img_prof = v.findViewById(R.id.chat_img);
            db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("members").document("admin");//여기
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("TAG", "DocumentSnapshot data: " + document.getData()+document.get("pwd"));

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
                                        Glide.with(itemView).load(uri).into(img_prof);
                                    }
                                });
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });
            rootView = v;
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatData> myDataset, Context context, String myNickName) {
        //{"1","2"}
        mDataset = myDataset;
        this.myNickName = myNickName;
    }

    //data setting
    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatData chat = mDataset.get(position);

        if(chat.getNickname().equals(this.myNickName)) {
            holder.nickname.setVisibility(View.GONE);
            holder.userchat.setVisibility(View.GONE);

            holder.mychat.setVisibility(View.VISIBLE);

            holder.TextView_msg.setText(chat.getMsg());
            holder.TextView_time.setText(chat.getTime());

        }
        else {
            usernick(chat.getNickname());
            holder.nickname.setVisibility(View.VISIBLE);
            holder.userchat.setVisibility(View.VISIBLE);

            holder.mychat.setVisibility(View.GONE);

            holder.my_nickname.setText(chat.getNickname());
            holder.my_msg.setText(chat.getMsg());
            holder.my_time.setText(chat.getTime());

        }

    }
    public void usernick(String nick){
        this.usernick = nick;
    }
    public String getnick(){
        return this.usernick;
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        //삼항 연산자
        return mDataset == null ? 0 :  mDataset.size();
    }

    public ChatData getChat(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatData chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1); //갱신
    }


}