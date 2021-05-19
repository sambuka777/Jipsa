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
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

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
    String id,pwd;
    boolean flag_id, flag_pwd;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edID = (EditText)findViewById(R.id.ed_ID);
        edPW = (EditText)findViewById(R.id.ed_PW);
        btnLOG = findViewById(R.id.btn_Log);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnLOG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = String.valueOf(edID.getText());
                pwd = String.valueOf(edPW.getText());
                JSONIdLogin Ji = new JSONIdLogin();
                Ji.content_idck(id);
                Ji.execute("http://192.168.6.1:3000/idlogin");
                if(flag_id&&flag_pwd){
                    Intent JoinIntent = new Intent(LoginActivity.this, MainActivity.class);
                    JoinIntent.putExtra("id",id);
                    startActivity(JoinIntent);
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
//    private void readUser(){
//        mDatabase.child("members").setaddValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                if(dataSnapshot.getValue(User.class) != null){
//                    User post = dataSnapshot.getValue(User.class);
//                    Log.w("FireBaseData", "getData" + post.toString());
//                } else {
//                    Toast.makeText(getApplicationContext(), "아이디 없음", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
//            }
//        });
//    }
    @IgnoreExtraProperties
    public class User{
        public String db_id;
        public User(){

        }
        public User(String db_id) {
            this.db_id = db_id;
        }

        public String getDb_id() {
            return db_id;
        }

        public void setUserName(String db_id) {
            this.db_id = db_id;
        }



    }

    public class JSONIdLogin extends AsyncTask<String, String, String> {
        String id;
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", id);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

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
                            reader.close();//버퍼를 닫아줌
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
            if(result.equals("No!")) {
                Toast.makeText(getApplicationContext(), "아이디 없음", Toast.LENGTH_SHORT).show();
                flag_id = false;
            }else {
                if(pwd.equals(result)){
                    flag_id = true;
                    flag_pwd = true;
                }else{
                    Toast.makeText(getApplicationContext(), "비번 없음", Toast.LENGTH_SHORT).show();
                }
            }
        }
        protected  void content_idck(String id){
            this.id = id;
        }
    }
}