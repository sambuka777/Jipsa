package com.daelim.Jipsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FindPWActivity extends AppCompatActivity {

    TextView TvFPID, TvFPName, TvFPPhone;
    EditText EdFPID, EdFPName, EdFPPhone;
    Button BtnFPCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        BtnFPCom = findViewById(R.id.btn_FPCom);
        BtnFPCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FIComIntent = new Intent(FindPWActivity.this, LoginActivity.class);
                startActivity(FIComIntent);
            }
        });




    }
}