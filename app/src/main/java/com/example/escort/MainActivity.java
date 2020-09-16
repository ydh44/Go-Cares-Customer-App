package com.example.escort;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonElement;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    APIinterface apIinterface;
    Window window;
    DrawerLayout drawerLayout;
    NavigationView navigationView, navigationView2;
    RecyclerView recyclerView;
    ImageButton menu, search, back1, back2, menu1, menu2, menu3;
    Adapter adapter;
    SwipeRefreshLayout swipe;
    Boolean err;
    TextView error;
    String id, urlgambar,  nama, email, umur, gender, telepon, alamat;


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
        swipe = findViewById(R.id.swipe);
        error = findViewById(R.id.noo);
        window = this.getWindow();

        control();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                control();
            }
        });

        //set on click di menu
        menu.setOnClickListener(view -> drawerLayout.openDrawer(navigationView2, true));
        search.setOnClickListener(view -> drawerLayout.openDrawer(navigationView, true));
        back1.setOnClickListener((view -> drawerLayout.closeDrawer(navigationView2, true)));
        back2.setOnClickListener((view -> {
            drawerLayout.closeDrawer(navigationView, true);
            UIUtil.hideKeyboard(MainActivity.this);
        }));
        menu1.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, PesananActivity.class);
            startActivity(i);
            drawerLayout.closeDrawer(navigationView2, false);

        });
        menu2.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, ProfileActivity.class);
            i.putExtra("nama", nama);
            i.putExtra("email", email);
            i.putExtra("umur", umur);
            i.putExtra("gender", gender);
            i.putExtra("telepon", telepon);
            i.putExtra("alamat", alamat);
            startActivity(i);
            drawerLayout.closeDrawer(navigationView2, false);
        });
        menu3.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, TentangActivity.class);
            startActivity(i);
            drawerLayout.closeDrawer(navigationView2, false);
        });

        //Ubah warrna status bar sesuai drawer
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            boolean isOpen ;
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if(!isOpen){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.setStatusBarColor(getResources().getColor(R.color.gray));
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.setStatusBarColor(getResources().getColor(R.color.white));
                    }
                    drawerLayout.closeDrawer(drawerView, true);
                }
                if(!drawerLayout.isDrawerVisible(drawerView)){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.setStatusBarColor(getResources().getColor(R.color.white));
                    }
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

    }

    public void control(){
        if(GetSess()){
            swipe.setRefreshing(true);
            GetCust();
            GetCg();
        }else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    public Boolean GetSess(){
        if(!SessionLog.GetStatus(this) || SessionLog.GetToken(this) == null) {
            return false;
        }else{
            return true;
        }
    }

    public void GetCust(){
        apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<ResponseBody> call = apIinterface.getCust(
                "Bearer " + SessionLog.GetToken(this)
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TAG", "onResponse: " + response.body().toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String a = jsonObject.getString("success");
                    JSONObject jsonObject2 = new JSONObject(a);
                    id = jsonObject2.getString("id");
                    nama = jsonObject2.getString("name");
                    email = jsonObject2.getString("email");
                    umur = jsonObject2.getString("age");
                    gender = jsonObject2.getString("gender");
                    telepon = jsonObject2.getString("phone");
                    alamat = jsonObject2.getString("address");

                    SessionLog.SaveId(MainActivity.this, id);
                    Log.d("TAG", "onResponse: " + a);
                    err = true;
                    hand();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    SessionLog.Delete(MainActivity.this);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                err = false;
                hand();
            }
        });
    }

    public void GetCg(){
        apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<JsonElement> call = apIinterface.getCg();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("TAG", "onResponse: " + response.code());
                if (response.isSuccessful()){
                    List<CGdata> data = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String b = jsonObject.getString("data");
                        JSONArray jsonArray = new JSONArray(b);
                        Log.d("TAG", "onResponse: " + b);
                        data = new ArrayList<>();
                        for (int i = 0 ; i < jsonArray.length(); i++){
                            JSONObject a = jsonArray.getJSONObject(i);
                            data.add(new CGdata(
                                    a.getString("id"),
                                    "http://40.88.4.113/esccortPhotos/" + a.getString("photo"),
                                    a.getString("name"),
                                    a.getString("age"),
                                    a.getString("gender"),
                                    a.getString("keahlian"),
                                    a.getString("address"),
                                    a.getString("salary"),
                                    a.getString("rating")
                            ));
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        adapter = new Adapter(getBaseContext(), data);
                        recyclerView.setAdapter(adapter);
                        err = true;
                        hand();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        err = false;
                        hand();
                    }
                }else{
                    err = false;
                    hand();
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                err = false;
                hand();
            }
        });
    }

    public void hand(){
        if(err){
            swipe.setRefreshing(false);
            error.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            swipe.setRefreshing(false);
            error.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}