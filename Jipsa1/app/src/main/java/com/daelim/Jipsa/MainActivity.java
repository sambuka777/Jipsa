package com.daelim.Jipsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;  //  바텀 내비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentChat fragmentchat;
    private FragmentCommu fragmentcommu;
    private FragmentHome fragmenthome;
    private FragmentSearch fragmentsearch;
    private FragmentMyPage fragmentmypage;
    private FragmentChatRoom fragmentchatroom;
    private FragmentComuWrite fragmentcomuwrite;
    private FragmentCommuView fragmentCommuView;
    private FragmentLost fragmentLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent getId = getIntent();
        String id = getId.getExtras().getString("id");
        Integer frag = getId.getExtras().getInt("frag");

        //바텀 내비게이션 뷰
        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.HomeItem :
                        setFrag(0,null);
                        break;
                    case R.id.ChatItem :
                        setFrag(1,null);
                        break;
                    case R.id.CommuItem :
                        setFrag(2,null);
                        break;
                    case R.id.SearchItem :
                        setFrag(3,null);
                        break;
                    case R.id.MyPageItem :
                        setFrag(4,null);
                        break;
                }

                return true;
            }
        });

        fragmenthome = new FragmentHome();
        fragmenthome.set_id(id);
        fragmentchat = new FragmentChat();
        fragmentchat.set_id(id);
        fragmentcommu = new FragmentCommu();
        fragmentcommu.set_id(id);
        fragmentsearch = new FragmentSearch();
        fragmentsearch.set_id(id);
        fragmentmypage = new FragmentMyPage();
        fragmentmypage.set_id(id);
        fragmentchatroom = new FragmentChatRoom();
        fragmentchatroom.set_id(id);
        fragmentcomuwrite = new FragmentComuWrite();
        fragmentcomuwrite.set_id(id);
        fragmentLost = new FragmentLost();
        fragmentLost.set_id(id);
        fragmentCommuView = new FragmentCommuView();
        fragmentCommuView.set_id(id);

        if(frag == 7){
            setFrag(7,null);//실종신고 취소 or 등록 후 화면 지정
        }else{
            setFrag(0,null); //첫 시작 프래그먼트 화면 지정
        }


    }

    // 바텀 내비게이션 뷰
    // 프래그먼트 교체가 일어나는 실행문
    void setFrag(int n,String temp_id) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0 :
                ft.replace(R.id.MainFrame, fragmenthome);
                ft.commit();
                break;
            case 1 :
                ft.replace(R.id.MainFrame, fragmentchat);
                ft.commit();
                break;
            case 2 :
                ft.replace(R.id.MainFrame, fragmentcommu);
                ft.commit();
                break;
            case 3 :
                ft.replace(R.id.MainFrame, fragmentsearch);
                ft.commit();
                break;
            case 4 :
                ft.replace(R.id.MainFrame, fragmentmypage);
                ft.commit();
                break;
            case 5 :
                ft.replace(R.id.MainFrame, fragmentchatroom);
                ft.commit();
                break;
            case 6 :
                ft.replace(R.id.MainFrame, fragmentcomuwrite);
                ft.commit();
                break;
            case 7 :
                ft.replace(R.id.MainFrame, fragmentLost);
                ft.commit();
                break;
            case 8 :
                fragmentCommuView.set_commid(temp_id);
                ft.replace(R.id.MainFrame, fragmentCommuView);
                ft.commit();


                break;
        }
    }

}