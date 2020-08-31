package com.example.escort;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TentangPagerAdapter extends FragmentStateAdapter {
    public TentangPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragTentang1();
            case 1:
                return new FragTentang2();
            default:
                return new FragTentang3();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
