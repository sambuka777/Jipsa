package com.daelim.Jipsa;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentUser extends Fragment {

    private View view;

    MainActivity mainActivity;

    Button btn_email,btn_emailck, btnreCom;
    EditText ed_pwd,ed_pwdck,ed_email,ed_emailck,ed_name;
    TextView ed_id,ed_birth;
    String id,numOfEmail,email,numOfEmailck,pwd,pwdck;
    boolean flag_email=false;
    FirebaseFirestore db;
    ImageButton ImgBtnUserBack;
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
        view = inflater.inflate(R.layout.fragment_user, container, false);
        ed_id = view.findViewById(R.id.ed_reID);
        ed_id.setText(id);
        //ed_id.getFreezesText();
        ed_name= view.findViewById(R.id.ed_reName);
        ed_birth = view.findViewById(R.id.ed_reBirth);
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("members").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData()+document.get("title")+document.get("memo"));
                        ed_name.setText(document.get("name").toString());
                        ed_birth.setText(document.get("birth").toString());
                        ed_birth.getFreezesText();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        btn_email = view.findViewById(R.id.btn_reEM);
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_email = view.findViewById(R.id.ed_reEM);
                email = ed_email.getText().toString();
                if(!email.equals("")){
                    JSONEmail JE = new JSONEmail();
                    JE.setEmail(email);
                    JE.execute("http://192.168.6.1:3000/mail");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("????????? ?????? ??????");
                    builder.setMessage("???????????? ???????????? ??????????????? ?????????????????????");
                    builder.setPositiveButton("??????", null);
                    builder.create().show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("????????? ?????? ??????");
                    builder.setMessage("????????? ????????? ????????? ????????????");
                    builder.setPositiveButton("??????", null);
                    builder.create().show();
                    //Toast.makeText(mainActivity.getApplicationContext(), "???????????? ?????????????????? ????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_emailck = view.findViewById(R.id.btn_reCerC);
        btn_emailck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_emailck = view.findViewById(R.id.ed_reCerNum);
                numOfEmailck = ed_emailck.getText().toString();
                if(numOfEmailck.equals(numOfEmail)){
                    Toast.makeText(mainActivity.getApplicationContext(), "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                    flag_email=true;
                }else{
                    Toast.makeText(mainActivity.getApplicationContext(), "??????????????? ????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImgBtnUserBack = view.findViewById(R.id.Imgbtn_UserBack);
        ImgBtnUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(4, null);
            }
        });

        btnreCom = view.findViewById(R.id.btn_reCom);
        btnreCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_pwd = view.findViewById(R.id.ed_rePW);
                ed_pwdck = view.findViewById(R.id.ed_rePWC);
                pwd = ed_pwd.getText().toString();
                pwdck = ed_pwdck.getText().toString();
                db = FirebaseFirestore.getInstance();
                if(!pwd.equals("")&&pwdck.equals("")){
                    if(pwd.equals(pwdck)){
                        if(flag_email){
                            db.collection("members").document(id).update("pwd",pwd);
                            db.collection("members").document(id).update("email",email);
                            mainActivity.setFrag(4, null);
                        }else{
                            Toast.makeText(mainActivity.getApplicationContext(), "????????? ????????? ??????????????????????????????", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(mainActivity.getApplicationContext(), "??????????????? ????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mainActivity.getApplicationContext(), "????????? ?????????????????? ????????????", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;    }
    public void set_id(String id) {
        this.id = id;
    }
    public class JSONEmail extends AsyncTask<String, String, String> {
        String email;
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject??? ????????? key value ???????????? ?????? ???????????????.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", email);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //????????? ???
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST???????????? ??????
                    con.setRequestProperty("Cache-Control", "no-cache");//?????? ??????
                    con.setRequestProperty("Content-Type", "application/json");//application JSON ???????????? ??????
                    con.setRequestProperty("Accept", "text/html");//????????? response ???????????? html??? ??????
                    con.setDoOutput(true);//Outstream?????? post ???????????? ?????????????????? ??????
                    con.setDoInput(true);//Inputstream?????? ??????????????? ????????? ???????????? ??????
                    con.connect();

                    //????????? ?????????????????? ????????? ??????
                    OutputStream outStream = con.getOutputStream();
                    //????????? ???????????? ??????
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//????????? ?????????

//                    ????????? ?????? ???????????? ??????
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    System.out.println(buffer.toString());
                    return buffer.toString();//????????? ?????? ?????? ?????? ???????????? ?????? OK!!??? ???????????????

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//????????? ?????????
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            numOfEmail = result.substring(60,66);

            System.out.println(numOfEmail);

//            tvData = (TextView)findViewById(R.id.tvData);
//        tvData.setText(result);//????????? ?????? ?????? ?????? ??????????????? ???
        }
        protected  void setEmail(String email){
            this.email = email;
        }
    }
}

