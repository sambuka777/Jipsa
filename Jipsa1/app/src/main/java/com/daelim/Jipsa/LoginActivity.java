package com.daelim.Jipsa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

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

public class LoginActivity extends AppCompatActivity {

    EditText edID, edPW;
    Button btnLOG,btnFIND,btnJoin;
    String id,pwd,pwdOfDb;
    boolean flag_id, flag_pwd;
    FirebaseFirestore db;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edID = (EditText)findViewById(R.id.ed_ID);
        edPW = (EditText)findViewById(R.id.ed_PW);
        btnLOG = findViewById(R.id.btn_Log);
        edID.setText("");
        edPW.setText("");
        btnLOG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = String.valueOf(edID.getText());
                pwd = String.valueOf(edPW.getText());
                if(!id.equals("")&&!pwd.equals("")){
                    db = FirebaseFirestore.getInstance();

                    DocumentReference docRef = db.collection("members").document(id);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData()+document.get("pwd"));
                                    if(pwd.equals(document.get("pwd"))){
                                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                        Intent JoinIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        JoinIntent.putExtra("id",id);
                                        startActivity(JoinIntent);
                                    }else{
                                        //Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle("로그인 실패");
                                    builder.setMessage("아이디 또는 비밀번호를 확인해 주십시오");
                                    builder.setPositiveButton("확인", null);
                                    builder.create().show();
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }else{
                    //Toast.makeText(getApplicationContext(), "입력좀", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("로그인 실패");
                    builder.setMessage("아이디 또는 비밀번호를 입력해 주십시오");
                    builder.setPositiveButton("확인", null);
                    builder.create().show();
                }
            }

        });
        btnJoin = findViewById(R.id.btn_Join);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent JoinIntent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(JoinIntent);
            }
        });

        btnFIND = findViewById(R.id.btn_Find);
        btnFIND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FindIntent = new Intent(LoginActivity.this, FindActivity.class);
                startActivity(FindIntent);
            }
        });

    }
}