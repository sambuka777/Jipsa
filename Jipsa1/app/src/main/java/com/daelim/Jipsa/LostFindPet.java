package com.daelim.Jipsa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LostFindPet extends AppCompatActivity {

    String id;

    MainActivity mainActivity;
    ImageButton close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_write);

        /*close_btn = findViewById(R.id.btn_close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.setFrag(7);
            }
        });*/

    }


    public void set_id(String id) {
        this.id = id;
    }
}
