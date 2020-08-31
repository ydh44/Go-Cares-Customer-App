package com.example.escort;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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

        pager.setAdapter(new TentangPagerAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tab, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText("Deskripsi");
                        break;
                    }
                    case 1: {
                        tab.setText("Perawatan Sosial");
                        break;
                    }
                    case 2: {
                        tab.setText("Kontak");
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