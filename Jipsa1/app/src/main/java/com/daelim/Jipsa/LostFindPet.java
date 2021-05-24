package com.daelim.Jipsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LostFindPet extends AppCompatActivity {

    String id;

    MainActivity mainActivity;
    ImageButton close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_write);

        Intent intent = getIntent();
        TextView title = (TextView)findViewById(R.id.write_title);
        title.setText("펫 " + intent.getStringExtra("text")+"신고");

        close_btn = findViewById(R.id.btn_close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostFindPet.this, MainActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("frag",7);
                startActivity(intent);
            }
        });

    }


    public void set_id(String id) {
        this.id = id;
    }
}
