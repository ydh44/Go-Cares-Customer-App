package com.example.escort;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PesananPagerAdapter extends FragmentStateAdapter {
    public PesananPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new FragPesanan2();
            case 2:
                return new FragPesanan3();
            case 3:
                return new FragPesanan4();
            case 4:
                return new FragPesanan5();
            default:
                return new FragPesanan1();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
