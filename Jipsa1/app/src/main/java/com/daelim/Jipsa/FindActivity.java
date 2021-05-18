package com.daelim.Jipsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FindActivity extends AppCompatActivity {

    Button btnFindID, btnFindPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        btnFindID = findViewById(R.id.btn_FindID);
        btnFindID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FIDIntent = new Intent(FindActivity.this, FindIDActivity.class);
                startActivity(FIDIntent);
            }
        });

        btnFindPW = findViewById(R.id.btn_FindPW);
        btnFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FPWIntent = new Intent(FindActivity.this, FindPWActivity.class);
                startActivity(FPWIntent);
            }
        });

    }
}