package com.daelim.Jipsa;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
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

    ImageView ViewPager;
    TextView TvComuTitle, TvComuList1, TvComuList2, TvComuList3, TvComuList4, TvLostAnimal;
    Button BtnComuMore, BtnLostAniMore;
    ImageButton IbLost1, IbLost2, IbLost3;

    MainActivity mainActivity;

//    ViewPager viewPager;
//    int images[] = {R.drawable.cat1, R.drawable.cat2, R.drawable.cat3};
//    MyCustomPagerAdapter myCustomPagerAdapter;
//    int currentPage = 0;
//
//    Timer timer;
//    final int num_pages = images.length;
//    final long delay_ms = 3000;
//    final long period_ms = 5000;

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

//        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

//        mPager.setPageTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float myOffset = position * -(2 * pageOffset + pageMargin);
//                if (mPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//                    if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                        page.setTranslationX(-myOffset);
//                    }
//                    else {
//                        page.setTranslationX(myOffset);
//                    }
//                }
//                else {
//                    page.setTranslationY(myOffset);
//                }
//            }
//        });

        BtnComuMore = view.findViewById(R.id.btn_ComuMore);
        BtnComuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.setFrag(2);
            }
        });

        BtnLostAniMore = view.findViewById(R.id.btn_LostAniMore);
        BtnLostAniMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.setFrag(7);
            }
        });

        return view;
    }
    public void set_id(String id){
        this.id = id;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            @Override
//            public void run() {
//                currentPage = viewPager.getCurrentItem();
//                int nextPage = currentPage + 1;
//
//                if (nextPage >= num_pages) {
//                    nextPage = 0;
//                }
//                viewPager.setCurrentItem(nextPage, true);
//                currentPage = nextPage;
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, delay_ms, period_ms);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }
}


