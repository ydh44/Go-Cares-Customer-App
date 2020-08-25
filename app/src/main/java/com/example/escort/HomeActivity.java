package com.example.escort;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    ImageButton menu;
    List<FragmentPesanan> fragmentPesanansList;
    FragmentPesananAdapter adapter;
    List<CG> cgList;
    CGAdapter cgadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        recyclerView = findViewById(R.id.recyclerView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menu = findViewById(R.id.menuButton);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        init();

        drawerLayout.addDrawerListener((DrawerLayout.DrawerListener) menu);
    }

    private void init() {
        fragmentPesanansList = new ArrayList<>();
        adapter = new FragmentPesananAdapter(HomeActivity.this);
        recyclerView.setAdapter(adapter);
        fragmentPesanansList.add(new FragmentPesanan("CareGiver1", "Date", "5 Agustus 2020", "Status Konfirmasi"));
        fragmentPesanansList.add(new FragmentPesanan("CareGiver2", "Date", "6 Januari 2020", "Status Konfirmasi"));
        fragmentPesanansList.add(new FragmentPesanan("CareGiver3", "Date", "9 Februari 2020", "Status Merawat"));
        fragmentPesanansList.add(new FragmentPesanan("CareGiver4", "Date", "24 Desember 2020", "Status Selesai"));
        adapter.setFragmentpesananData(fragmentPesanansList);
        adapter.notifyDataSetChanged();

    }
}