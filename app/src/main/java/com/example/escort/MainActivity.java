package com.example.escort;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    Window window;
    DrawerLayout drawerLayout;
    NavigationView navigationView, navigationView2;
    RecyclerView recyclerView;
    ImageButton menu, search, back1, back2, menu1, menu2, menu3;
    Adapter adapter;
    List<CGdata> items;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.recyclerview);
        drawerLayout =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView2 = findViewById(R.id.nav_view2);
        menu = findViewById(R.id.menuButton);
        search = findViewById(R.id.searchButton);
        back1 = findViewById(R.id.backdrawl);
        back2 = findViewById(R.id.backdrawr);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        window = this.getWindow();

        //set on click di menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView2, true);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView, true);
            }
        });
        back1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(navigationView2, true);
            }
        }));
        back2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(navigationView, true);
                UIUtil.hideKeyboard(MainActivity.this);
            }
        }));
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PesananActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(navigationView2, false);

            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(navigationView2, false);
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(navigationView2, false);
            }
        });

        //Ubah warrna status bar sesuai drawer
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            boolean isOpen ;
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if(!isOpen){
                    window.setStatusBarColor(getResources().getColor(R.color.gray));
                }else {
                    window.setStatusBarColor(getResources().getColor(R.color.white));
                    drawerLayout.closeDrawer(drawerView, true);
                }
                if(!drawerLayout.isDrawerVisible(drawerView)){
                    window.setStatusBarColor(getResources().getColor(R.color.white));
                }
            }
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                isOpen = true;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                isOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        items = new ArrayList<>();
        items.add(new CGdata("https://cdn2.tstatic.net/solo/foto/bank/images/12wqdasfg.jpg", "Care Giver 1 ", "12", "pria", "sem", "5jt", "4"));
        items.add(new CGdata("https://static.republika.co.id/uploads/images/inpicture_slide/teleskop-luar-angkasa-hubble-milik-nasa-menangkap-gambar-luas_200624075257-360.jpg", "Care Giver 2 ", "13", "pria", "sema", "1jt","3"));
        items.add(new CGdata("https://awsimages.detik.net.id/community/media/visual/2020/02/28/9b4813be-44a3-42e4-b63e-41654bb57018.jpeg?w=700&q=90", "Care Giver 3 ", "14", "pria", "semar", "2jt","45"));
        items.add(new CGdata("https://cdn2.tstatic.net/solo/foto/bank/images/12wqdasfg.jpg","Care Giver 4 ", "15", "pria", "semara", "1jt", "36"));
        items.add(new CGdata("https://awsimages.detik.net.id/community/media/visual/2020/02/28/9b4813be-44a3-42e4-b63e-41654bb57018.jpeg?w=700&q=90", "Care Giver 5 ", "16", "Spria", "semarang", "4jt", "43"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,items);
        recyclerView.setAdapter(adapter);


        if(!SessionLog.GetStatus(this) || SessionLog.GetToken(this) == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
           finish();
        }

    }
}