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
    private FragmentUser fragmentUser;
    private FragmentNotice fragmentNotice;
    private FragmentQnA fragmentQnA;
    private FragmentQnAPre fragmentQnAPre;
    private FragmentNoticeView fragmentNoticeView;
    private FragmentQnApreView fragmentQnApreView;

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
                        setFrag(7,null);
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
//        fragmentchatroom = new FragmentChatRoom();
//        fragmentchatroom.set_id(id);
        fragmentcomuwrite = new FragmentComuWrite();
        fragmentcomuwrite.set_id(id);
        fragmentLost = new FragmentLost();
        fragmentLost.set_id(id);
        fragmentCommuView = new FragmentCommuView();
        fragmentCommuView.set_id(id);
        fragmentNotice = new FragmentNotice();
        fragmentQnA = new FragmentQnA();
        fragmentQnA.set_id(id);
        fragmentUser = new FragmentUser();
        fragmentUser.set_id(id);
        fragmentQnAPre = new FragmentQnAPre();
        fragmentQnAPre.set_id(id);
        fragmentNoticeView = new FragmentNoticeView();
        fragmentQnApreView = new FragmentQnApreView();

        if(frag == 7){
            setFrag(7,null);//실종신고 취소 or 등록 후 화면 지정
        }
        else if(frag ==1){
            setFrag(1,null); //첫 시작 프래그먼트 화면 지정
        }
        else{
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
            case 9 :
                ft.replace(R.id.MainFrame, fragmentNotice);
                ft.commit();
                break;
            case 10 :
                ft.replace(R.id.MainFrame, fragmentQnA);
                ft.commit();
                break;
            case 11 :
                fragmentUser.set_id(temp_id);
                ft.replace(R.id.MainFrame, fragmentUser);
                ft.commit();
                break;
            case 12 :
                ft.replace(R.id.MainFrame, fragmentQnAPre);
                ft.commit();
                break;
            case 13 :
                fragmentNoticeView.set_commid(temp_id);
                ft.replace(R.id.MainFrame, fragmentNoticeView);
                ft.commit();
                break;
            case 14 :
                fragmentQnApreView.set_QnAid(temp_id);
                ft.replace(R.id.MainFrame, fragmentQnApreView);
                ft.commit();
                break;
        }
    }

}