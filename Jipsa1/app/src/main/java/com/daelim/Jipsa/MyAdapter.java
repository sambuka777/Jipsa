package com.daelim.Jipsa;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;

    public MyAdapter(@NonNull FragmentHome fragmentActivity, int count) {
        super(fragmentActivity);

        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new Fragment_lost1();
        else if(index==1) return new Fragment_lost2();
        else if(index==2) return new Fragment_lost3();
        else return new Fragment_lost4();
    }


    @Override
    public int getItemCount() {
        return 2000;
    }

    private int getRealPosition(int position) {
        return position % mCount;
    }
}
