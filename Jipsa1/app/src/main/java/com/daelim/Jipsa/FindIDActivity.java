package com.daelim.Jipsa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

public class FindIDActivity extends AppCompatActivity {

    TextView TvFname, TvFEmail;
    EditText EdFname, EdFEmail,EdFCerNum;
    Button BtnFICom,btn_Email,btn_EmailCk;
    FirebaseFirestore db;
    String name,numOfEmail,email;
    boolean flag_email=false;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);

        EdFname = findViewById(R.id.ed_FName);
        EdFEmail = findViewById(R.id.ed_Em);
        EdFCerNum= findViewById(R.id.ed_FCerNum);

        BtnFICom = findViewById(R.id.btn_FPCom);
        BtnFICom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = EdFname.getText().toString();

                db = FirebaseFirestore.getInstance();
                db.collection("members").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if(name.equals(document.get("name"))&&email.equals(document.get("email"))){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);
                                    builder.setTitle("????????? ??????");
                                    builder.setMessage("???????????? ????????????"+document.getId()+"?????????");
                                    builder.setPositiveButton("??????", null);
                                    builder.create().show();
//                                    Toast.makeText(FindIDActivity.this, "???????????? ????????????"+document.getId()+"?????????", Toast.LENGTH_SHORT).show();
                                    Intent FIComIntent = new Intent(FindIDActivity.this, LoginActivity.class);
                                    startActivity(FIComIntent);
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);
                                    builder.setTitle("????????? ??????");
                                    builder.setMessage("?????????????????????");
                                    builder.setPositiveButton("??????", null);
                                    builder.create().show();
//                                    Toast.makeText(FindIDActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

            }
        });
        btn_Email=findViewById(R.id.btn_FCer);
        btn_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email= EdFEmail.getText().toString();
                if(!email.equals("")){
                    JSONEmail Ji = new JSONEmail();
                    Ji.setEmail(email);
                    Ji.execute("http://192.168.6.1:3000/mail");
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);
                    builder.setTitle("????????? ??????");
                    builder.setMessage("???????????? ?????????????????????");
                    builder.setPositiveButton("??????", null);
                    builder.create().show();
//                    Toast.makeText(FindIDActivity.this, "???????????? ?????????????????????", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_EmailCk = findViewById(R.id.btn_FCerC);
        btn_EmailCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EdFCerNum.getText().toString().equals(numOfEmail)){
                    System.out.println(EdFCerNum.getText().toString());
                    System.out.println(numOfEmail);
                    flag_email=true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);
                    builder.setTitle("????????? ??????");
                    builder.setMessage("?????????????????????");
                    builder.setPositiveButton("??????", null);
                    builder.create().show();
//                    Toast.makeText(FindIDActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);
                    builder.setTitle("???????????? ??????");
                    builder.setMessage("??????????????? ??????????????????");
                    builder.setPositiveButton("??????", null);
                    builder.create().show();
//                    Toast.makeText(FindIDActivity.this, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
                }

            }
        });

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