package com.daelim.Jipsa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class JoinActivity extends AppCompatActivity {

    EditText edjID, edEm, edName,edpwd,edFEm,edpwck,edFCerNum,edbirth;
    Button btnOl, btnCer, btnCom,btnFCer,btn_emailck;
    TextView tvData;
    String id,pwd,name,email,numOfEmail,numOfEmailck,pwdck,birth;
    boolean flag_idok=false,flag_email=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        btnFCer = (Button)findViewById(R.id.btn_Cer);
        btnCom = (Button)findViewById(R.id.btn_Com);
        btnOl = (Button)findViewById(R.id.btn_Ol);
        btn_emailck = findViewById(R.id.btn_FCerC);
        edjID = (EditText)findViewById(R.id.ed_JID);
        edpwd = (EditText)findViewById(R.id.ed_JPW);
        edpwck = (EditText)findViewById(R.id.ed_PWc);
        edName = (EditText)findViewById(R.id.ed_Name);
        edFEm =(EditText)findViewById(R.id.ed_Em);
        edpwck=(EditText)findViewById(R.id.ed_PWc);
        edFCerNum=(EditText)findViewById(R.id.ed_CerNum);
        edbirth=(EditText)findViewById(R.id.ed_Birth);
        btnOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id= String.valueOf(edjID.getText());
                JSONIdck Ji = new JSONIdck();

                Ji.content_idck(id);
                Ji.execute("http://192.168.6.1:3000/idck");
//                if(){
//                    flag_idok = true;
//                    btnOl.setEnabled(false);
//                    edjID.setEnabled(false);
//                }
            }
        });
        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd= String.valueOf(edpwd.getText());
                pwdck = String.valueOf(edpwck.getText());
                name= String.valueOf(edName.getText());
                birth = String.valueOf(edbirth.getText());

                System.out.println(name+pwd);
                if (flag_idok) {
                    if(!edpwd.getText().toString().equals("")
                            &&!edpwck.getText().toString().equals("")&&!edName.getText().toString().equals("")
                            &&!edbirth.getText().toString().equals("")){
                        if(pwd.equals(pwdck)){
                            if (flag_email) {
                                JSONTask js = new JSONTask();
                                js.content(id, pwd, name, birth, email);
                                js.execute("http://192.168.6.1:3000/post");
                                Toast.makeText(getApplicationContext(), "회원가입 성공 ", Toast.LENGTH_SHORT).show();
                                Intent JoinIntent = new Intent(JoinActivity.this, LoginActivity.class);
                                startActivity(JoinIntent);

                            }else{
                                Toast.makeText(getApplicationContext(), "이메일인증을 완료해 주십시오", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "모든 항목을 입력해 주십시오", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "중복확인 인증을 부탁드립니다", Toast.LENGTH_SHORT).show();
                }
                //AsyncTask 시작시킴
//                new JSONTask().execute("http://192.168.6.1:3000/post");//AsyncTask 시작시킴

            }
        });
        btnFCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email =String.valueOf(edFEm.getText());
                if(!email.equals("")) {
                    JSONEmail JE = new JSONEmail();
                    JE.setEmail(email);
                    JE.execute("http://192.168.6.1:3000/mail");
                }else{
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주시기 바랍니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_emailck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edFCerNum.getText().toString().equals("")){
                    numOfEmailck=String.valueOf(edFCerNum.getText());
                    if(numOfEmail.equals(numOfEmailck)){
                        flag_email=true;
                        Toast.makeText(getApplicationContext(), "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "인증번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "인증번호를 입력해주시기 바랍니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class JSONIdck extends AsyncTask<String, String, String> {
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
                Toast.makeText(getApplicationContext(), "Pressed OK", Toast.LENGTH_SHORT).show();
                btnOl.setEnabled(false);
                flag_idok = true;
            }else
                Toast.makeText(getApplicationContext(), "no!", Toast.LENGTH_SHORT).show();
//            tvData = (TextView)findViewById(R.id.tvData);
//        tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
        }
        protected  void content_idck(String id){
            this.id = id;
        }
    }
    public class JSONTask extends AsyncTask<String, String, String> {
        String id,pwd,name,birth,email;
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", id);
                jsonObject.accumulate("pwd", pwd);
                jsonObject.accumulate("name", name);
                jsonObject.accumulate("birth", birth);
                jsonObject.accumulate("email", email);

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
            if(result.equals("OK!")){

            }
//            tvData = (TextView)findViewById(R.id.tvData);
//        tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
        }
        protected  void content(String id,String pwd,String name,String birth,String email){
            this.id = id;
            this.pwd = pwd;
            this.name = name;
            this.birth = birth;
            this.email = email;
        }
    }
    public class JSONEmail extends AsyncTask<String, String, String> {
        String email;
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", email);
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

//                    서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    System.out.println(buffer.toString());
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
            numOfEmail = result.substring(60,66);

            System.out.println(numOfEmail);

//            tvData = (TextView)findViewById(R.id.tvData);
//        tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
        }
        protected  void setEmail(String email){
            this.email = email;
        }
    }
}

