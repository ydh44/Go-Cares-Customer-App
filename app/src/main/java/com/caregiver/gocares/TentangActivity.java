package com.caregiver.gocares;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TentangActivity extends AppCompatActivity {
    ImageButton back;
    TabLayout tab;
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentang);

        back = findViewById(R.id.backButton);
        tab = findViewById(R.id.tabpesanan);
        pager = findViewById(R.id.pagerpesanan);
        Intent i = getIntent();
        int defaults = 0;
        int posts = i.getIntExtra("id", defaults);

        pager.setAdapter(new TentangPagerAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tab, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(posts == 1){
                    position = 2;
                    pager.setCurrentItem(position);
                }
                switch (position){
                    case 0: {
                        tab.setText("Panduan");
                        break;
                    }
                    case 1: {
                        tab.setText("Lingkup Kerja");
                        break;
                    }
                    case 2: {
                        tab.setText("Perawatan Sosial");
                        break;
                    }
                    case 3: {
                        tab.setText("Info Sekolah");
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