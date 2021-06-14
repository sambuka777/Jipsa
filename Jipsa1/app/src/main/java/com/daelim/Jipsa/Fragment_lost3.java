package com.daelim.Jipsa;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_lost3 extends Fragment {
    FirebaseFirestore db;
    ArrayList<String> db_missname;
    ArrayList<String> db_missgps;
    ArrayList<String> db_misssex;
    ArrayList<String> db_missdate;
    ArrayList<String> db_missmemo;
    List<Address> db_gps;
    ImageView iv;
    String img;
    TextView txt_name,txt_sex,txt_gps,txt_memo,txt_date,txt_missOrfind;
    String temp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_lost3, container, false);
        db_misssex = new ArrayList<>();
        db_missdate = new ArrayList<>();
        db_missgps = new ArrayList<>();
        db_missgps = new ArrayList<>();
        db_missmemo = new ArrayList<>();
        db_missname = new ArrayList<>();
        txt_name = rootView.findViewById(R.id.tv3_lostinfor1_1);
        txt_sex = rootView.findViewById(R.id.tv3_lostinfor2_1);
        txt_gps = rootView.findViewById(R.id.tv3_lostinfor3_1);
        txt_date = rootView.findViewById(R.id.tv3_lostinfor4_1);
        txt_memo = rootView.findViewById(R.id.tv3_lostinfor5_1);
        txt_missOrfind = rootView.findViewById(R.id.txt_missOrfind3);
        iv = rootView.findViewById(R.id.imgBanner3);
        db = FirebaseFirestore.getInstance();
//        CollectionReference citiesRef = db.collection("commity");
//        db.collection("commity").get;
        db.collection("petofmiss").orderBy("time", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        GeoPoint geoPoint = document.getGeoPoint("gps");
                        double lostlat = geoPoint.getLatitude();
                        double lostlng = geoPoint.getLongitude();
                        Geocoder geocoder  = new Geocoder(getContext());
                        String Area = null;
                        try {
                            db_gps = null;
                            db_gps = geocoder.getFromLocation(lostlat,lostlng,1);
//                            System.out.println(db_gps.get(0).toString());
                            Address mAddress = db_gps.get(0);
                            StringBuffer strbuf = new StringBuffer();
                            String buf;
                            for (int i = 0; (buf = mAddress.getAddressLine(i)) != null; i++) {

                                strbuf.append(buf + "\n");
                            }
                            Area = strbuf.toString();
                            System.out.println(Area);
                            String n = Area.substring(Area.lastIndexOf("국")+2);
                            txt_name.setText(document.get("petname").toString());
                            txt_memo.setText(document.get("petchr").toString().replaceAll("InE", " "));
                            txt_sex.setText(document.get("petsex").toString());
                            txt_gps.setText(n);
                            Date from = new Date(document.getTimestamp("time").toDate().getTime());
                            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String to = transFormat.format(from);
                            txt_date.setText(to);
                            if(document.getBoolean("isdiscovery")){
                                txt_missOrfind.setText("발견신고");
                            }else{
                                txt_missOrfind.setText("실종신고");
                            }

                            img = document.get("image").toString();
//                            if(img.equals("null")) {
                            iv.setImageResource(R.drawable.dogicon);
//                                Glide.with(Fragment_lost3.this).load(R.drawable.dogicon).into(iv);
//                            }else{
//                                FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
//                                StorageReference storageRef = firebaseStorage.getReference();
//                                storageRef.child("lostpet/"+img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        try {
//                                            Glide.with(Fragment_lost3.this).load(uri).into(iv);
//                                        }catch (Exception e){
//                                            iv.setImageResource(R.drawable.dogicon);
//                                        }
//
//                                    }
//                                });
//                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    //System.out.println(db_gps.toString());
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
        return rootView;
    }
}
