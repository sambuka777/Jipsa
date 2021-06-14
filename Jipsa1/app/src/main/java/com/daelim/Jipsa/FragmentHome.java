package com.daelim.Jipsa;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator3;

public class FragmentHome extends Fragment {

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    private View view;
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;
    private CircleIndicator3 mIndicator;
    private String id;
    private DrawerLayout dw;
    private NavigationView dwv;
    FirebaseFirestore db;
    TextView txt_commu_view1,txt_commu_view2,txt_commu_view3,txt_commu_view4,txt_commu_view5;
    ImageView ViewPager;
    TextView TvComuTitle, TvComuList1, TvComuList2, TvComuList3, TvComuList4,TvComuList5, TvLostAnimal;
    Button BtnComuMore, BtnLostAniMore,btnmlost,btnmcomu,btnmchat,btnmmy,btnmgong,btnmmoon,btnmjang;
    ImageButton IbLost1, IbLost2, IbLost3,BtnMenu;
    LinearLayout menuu,commu1,commu2,commu3,commu4,commu5;
    ArrayList<String> db_comtilte;
    ArrayList<String> db_comint;
    ArrayList<String> db_id;

    MainActivity mainActivity;

    //ViewPager 이미지 슬라이드
    class FragmentAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments = new ArrayList<>();

        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addItem(FragmentImage fragment) {
            fragments.add(fragment);
        }

    }

    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        System.out.println(id);
        db_comint = new ArrayList<>();
        db_comtilte = new ArrayList<>();
        db_id = new ArrayList<>();
        //commuity view line
        TvComuList1= view.findViewById(R.id.tv_comulist1);
        TvComuList2= view.findViewById(R.id.tv_comulist2);
        TvComuList3= view.findViewById(R.id.tv_comulist3);
        TvComuList4= view.findViewById(R.id.tv_comulist4);
        TvComuList5= view.findViewById(R.id.tv_comulist5);
        txt_commu_view1=view.findViewById(R.id.tv_comulistv1);
        txt_commu_view2=view.findViewById(R.id.tv_comulistv2);
        txt_commu_view3=view.findViewById(R.id.tv_comulistv3);
        txt_commu_view4=view.findViewById(R.id.tv_comulistv4);
        txt_commu_view5=view.findViewById(R.id.tv_comulistv5);
        commu1 = view.findViewById(R.id.home_comm1);
        commu2 = view.findViewById(R.id.home_comm2);
        commu3 = view.findViewById(R.id.home_comm3);
        commu4 = view.findViewById(R.id.home_comm4);
        commu5 = view.findViewById(R.id.home_comm5);

        db = FirebaseFirestore.getInstance();
//        CollectionReference citiesRef = db.collection("commity");
//        db.collection("commity").get;
        db.collection("commity").orderBy("viewnum", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        db_comtilte.add(document.get("title").toString());
                        db_comint.add(document.get("viewnum").toString());
                        db_id.add(document.getId());
                    }
                    TvComuList1.setText(db_comtilte.get(0));
                    TvComuList2.setText(db_comtilte.get(1));
                    TvComuList3.setText(db_comtilte.get(2));
                    TvComuList4.setText(db_comtilte.get(3));
                    TvComuList5.setText(db_comtilte.get(4));
                    txt_commu_view1.setText(db_comint.get(0));
                    txt_commu_view2.setText(db_comint.get(1));
                    txt_commu_view3.setText(db_comint.get(2));
                    txt_commu_view4.setText(db_comint.get(3));
                    txt_commu_view5.setText(db_comint.get(4));

                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
        commu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8,db_id.get(0));
            }
        });
        commu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8,db_id.get(1));
            }
        });
        commu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8,db_id.get(2));
            }
        });
        commu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8,db_id.get(3));
            }
        });
        commu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(8,db_id.get(4));
            }
        });
        //ViewPager2
        mPager = view.findViewById(R.id.ViewPager_lost);
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator
        mIndicator = view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(3);
        menuu = view.findViewById(R.id.menuu);

        menuu.bringToFront();

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position % num_page);
            }
        });

        dw =view.findViewById(R.id.main_drawer_layout);
        dwv = view.findViewById(R.id.main_navigationView);
        BtnMenu = view.findViewById(R.id.menubtn);
        BtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dw.openDrawer(dwv);

            }
        });

        btnmlost = view.findViewById(R.id.menulost);
        btnmcomu = view.findViewById(R.id.menucomu);
        btnmchat = view.findViewById(R.id.menuchat);
        btnmmy = view.findViewById(R.id.menumy);
        btnmgong = view.findViewById(R.id.menugong);
        btnmmoon = view.findViewById(R.id.menumoon);
        btnmjang = view.findViewById(R.id.menujang);

        btnmlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(7,null);

            }
        });
        btnmcomu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(8,null);

            }
        });
        btnmchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(1,null);

            }
        });
        btnmmy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(11,null);

            }
        });
        btnmgong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(9,null);

            }
        });
        btnmmoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(10,null);

            }
        });
        btnmjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFrag(11,null);

            }
        });

        dwv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.account:
                        Toast.makeText(getContext(), "SelectedItem 1", Toast.LENGTH_SHORT).show();
                        mainActivity.setFrag(2,null);
                    case R.id.item2:
                        Toast.makeText(getContext(), "SelectedItem 2", Toast.LENGTH_SHORT).show();
                    case R.id.item3:
                        Toast.makeText(getContext(), "SelectedItem 3", Toast.LENGTH_SHORT).show();
                }

                DrawerLayout dw = view.findViewById(R.id.main_drawer_layout);
                dw.closeDrawer(GravityCompat.START);
                return true;
            }
        });




            BtnComuMore = view.findViewById(R.id.btn_ComuMore);
        BtnComuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.setFrag(2,null);
            }
        });

        BtnLostAniMore = view.findViewById(R.id.btn_LostAniMore);
        BtnLostAniMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.setFrag(7,null);
            }
        });

        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.cat1);
        listImage.add(R.drawable.cat2);
        listImage.add(R.drawable.cat3);

        ViewPager viewPager = view.findViewById(R.id.Image_Slide);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

        for (int i = 0; i < listImage.size(); i++) {
            FragmentImage imageFragment = new FragmentImage();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        fragmentAdapter.notifyDataSetChanged();

        return view;
    }

    public void set_id(String id){
        this.id = id;
    }

}


