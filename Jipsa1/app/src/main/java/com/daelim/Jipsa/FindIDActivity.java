package com.daelim.Jipsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FindIDActivity extends AppCompatActivity {

    TextView TvFname, TvFEmail;
    EditText EdFname, EdFEmail,EdFCerNum;
    Button BtnFICom;

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
                Intent FIComIntent = new Intent(FindIDActivity.this, LoginActivity.class);
                startActivity(FIComIntent);
            }
        });



    }
}