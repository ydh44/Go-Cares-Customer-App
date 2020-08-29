package com.example.escort;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PesananActivity extends AppCompatActivity {
    ImageButton back;
    TabLayout tab;
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesanan);

        back = findViewById(R.id.backButton);
        tab = findViewById(R.id.tabpesanan);
        pager = findViewById(R.id.pagerpesanan);

        pager.setAdapter(new PesananPagerAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tab, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText("Belum Bayar");
                        break;
                    }
                    case 1: {
                        tab.setText("Dalam Proses");
                        break;
                    }
                    case 2: {
                        tab.setText("Sedang Merawat");
                        break;
                    }
                    case 3: {
                        tab.setText("Selesai");
                        break;
                    }
                }
            }
        }
        );
        tabLayoutMediator.attach();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}