package com.daelim.Jipsa;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
    private FragmentLost fragmentLost ;
    private FragmentCommuView fragmentCommuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //바텀 내비게이션 뷰
        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.HomeItem :
                        setFrag(0);
                        break;
                    case R.id.ChatItem :
                        setFrag(1);
                        break;
                    case R.id.CommuItem :
                        setFrag(2);
                        break;
                    case R.id.SearchItem :
                        setFrag(3);
                        break;
                    case R.id.MyPageItem :
                        setFrag(4);
                        break;
                }

                return true;
            }
        });

        fragmenthome = new FragmentHome();
        fragmentchat = new FragmentChat();
        fragmentcommu = new FragmentCommu();
        fragmentsearch = new FragmentSearch();
        fragmentmypage = new FragmentMyPage();
        fragmentchatroom = new FragmentChatRoom();
        fragmentcomuwrite = new FragmentComuWrite();
        fragmentLost = new FragmentLost();
        fragmentCommuView = new FragmentCommuView();

        setFrag(0); //첫 시작 프래그먼트 화면 지정

    }

    // 바텀 내비게이션 뷰
    // 프래그먼트 교체가 일어나는 실행문
    void setFrag(int n) {
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
                ft.replace(R.id.MainFrame, fragmentCommuView);
                ft.commit();
                break;
        }
    }

}