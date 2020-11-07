package com.caregiver.gocares;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonElement;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.view.ViewCompat.setNestedScrollingEnabled;


public class MainActivity extends AppCompatActivity {

    APIinterface apIinterface;
    Window window;
    DrawerLayout drawerLayout;
    NavigationView navigationView, navigationView2;
    RecyclerView recyclerView, recyclerView2;
    ImageButton menu, search, back1, back2, menu1, menu2, menu3, menu4, back3, btnsearch;
    Adapter adapter;
    SwipeRefreshLayout swipe;
    Boolean err;
    TextView error, error1, error2;
    String id, urlgambar,  nama, email, umur, gender, telepon, alamat;
    RadioGroup rggender;
    TextView agetv1, agetv2, gendertv1,gendertv2;
    EditText etumur1,etumur2;
    View filter;
    ProgressBar prgs;
    String kelamin = null;
    ImageView icsearch;
    //BackdropContainer backdropContainer;
    Toolbar toolbar;

    static String reload = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_v2);

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
        menu4 = findViewById(R.id.menu4);
        swipe = findViewById(R.id.swipe);
        error = findViewById(R.id.noo);
        error1 = findViewById(R.id.nooo2);
        error2 = findViewById(R.id.noo2);

        filter = findViewById(R.id.filter);
        recyclerView2 = findViewById(R.id.recyclerview2);
        prgs = findViewById(R.id.prgs);
        agetv1 = findViewById(R.id.agetv);
        agetv2 = findViewById(R.id.agetv2);
        gendertv1 = findViewById(R.id.gendertv);
        gendertv2 = findViewById(R.id.gendertv2);
        back3 = findViewById(R.id.backout);
        error1 = findViewById(R.id.nooo2);
        rggender = findViewById(R.id.genderEdtx);
        etumur1 = findViewById(R.id.etumur1);
        etumur2 = findViewById(R.id.etumur2);
        btnsearch = findViewById(R.id.btnsearch);
        icsearch = findViewById(R.id.icsearch);

        //backdropContainer = findViewById(R.id.rel);
        toolbar = findViewById(R.id.bar);

        //LinearLayout contentLayout = findViewById(R.id.linear);

        window = this.getWindow();

        /*
        BottomSheetBehavior  sheetBehavior = BottomSheetBehavior.from(contentLayout);
        sheetBehavior.setFitToContents(false);;
        sheetBehavior.setHideable(false);//prevents the boottom sheet from completely hiding off the screen
        sheetBehavior.setState(BottomSheetBehavior.STATE_DRAGGING);//initially state to fully expanded

        Log.d("TAG", "onCreate: " + sheetBehavior.getState());

         */


        setSupportActionBar(toolbar);

        /*backdropContainer.attachToolbar(toolbar)
                .dropInterpolator(new LinearInterpolator())
                .dropHeight(50)
                .build();

         */

        rggender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio0:
                        agetv1.setTextColor(getResources().getColor(R.color.darkgray));
                        kelamin = "L";
                        break;
                    case R.id.radio1:
                        agetv1.setTextColor(getResources().getColor(R.color.darkgray));
                        kelamin = "P";
                        break;
                }
                filter();
            }
        });;

        control(this);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                control(MainActivity.this);
                //backdropContainer.showBackview();
            }
        });

        //set on click di menu
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
            }
        });

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
            if(nama != null){
                i.putExtra("status", "yes");
            }
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
        menu4.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Logout dari akun ?")
                    .setPositiveButton("Lanjutkan", (dialog, id) -> {
                        SessionLog.Delete(MainActivity.this);
                        Intent i = new Intent(this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    })
                    .setNegativeButton("Batalkan", (dialog, id) -> {
                        // User cancelled the dialog
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
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
                cek_form(etumur1, agetv1);
                cek_form(etumur2, agetv1);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                isOpen = false;
                UIUtil.hideKeyboard(MainActivity.this);
                etumur1.setBackgroundResource(R.drawable.backwhite);
                etumur1.setBackgroundResource(R.drawable.backwhite);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                etumur1.setBackgroundResource(R.drawable.backwhite);
                etumur1.setBackgroundResource(R.drawable.backwhite);
            }
        });

    }

    public void control(Context context){
        if(GetSess(context)){
            swipe.setRefreshing(true);
            GetCust();
            GetCg();
        }else{
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            Log.d("TAG", "control: " + SessionLog.GetFcm(this));
            finish();
        }
    }

    public void onResume() {
        super.onResume();
        if(reload != null){
            rggender.clearCheck();
            filter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            swipe.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            icsearch.setVisibility(View.VISIBLE);
            control(MainActivity.this);
            reload = null;
        }
    }


    public Boolean GetSess(Context context){
        if(!SessionLog.GetStatus(context) || SessionLog.GetToken(context) == null) {
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
                    hand(true);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    SessionLog.Delete(MainActivity.this);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hand(false);
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
                        JSONObject a = new JSONObject(response.body().toString());
                        String b = a.getString("data");
                        String b2 = a.getString("dalam_proses");
                        JSONObject c = new JSONObject(b);
                        String d = c.getString("data");
                        JSONArray e = new JSONArray(d);
                        JSONArray e2 = new JSONArray(b2);
                        data = new ArrayList<>();
                        for (int i = 0 ; i < e.length(); i++){
                            JSONObject f = e.getJSONObject(i);
                            String id = f.getString("id");
                            String idgunaguna = "available";
                            for (int i2 = 0 ; i2 < e2.length(); i2++){
                                String idguna =  String.valueOf(e2.get(i2));
                                if(idguna.equals(id)){
                                    idgunaguna = "unavailable";
                                }
                            }
                            data.add(new CGdata(
                                    id,
                                    "http://40.88.4.113/esccortPhotos/" + f.getString("photo"),
                                    f.getString("name"),
                                    f.getString("age"),
                                    f.getString("gender"),
                                    f.getString("keahlian"),
                                    f.getString("address"),
                                    f.getString("salary"),
                                    f.getString("rating"),
                                    idgunaguna
                            ));
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        Collections.shuffle(data);
                        adapter = new Adapter(getBaseContext(), data);
                        recyclerView.setAdapter(adapter);
                        hand(true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        hand(false);
                    }
                }else{
                    hand(false);
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hand(false);
            }
        });
    }

    public void filter(){
        //if(valid()){
            error1.setVisibility(View.GONE);
            error2.setVisibility(View.GONE);
            icsearch.setVisibility(View.GONE);
            prgs.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.GONE);
            drawerLayout.closeDrawer(navigationView, true);
            UIUtil.hideKeyboard(MainActivity.this);
            swipe.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            filter.setVisibility(View.VISIBLE);
            if(kelamin == "L"){
                gendertv2.setText("Laki laki");
            }else{
                gendertv2.setText("Perempuan");
            }
            back3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rggender.clearCheck();
                    filter.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    swipe.setVisibility(View.VISIBLE);
                    search.setVisibility(View.VISIBLE);
                    icsearch.setVisibility(View.VISIBLE);
                    control(MainActivity.this);
                }
            });
            Call<JsonElement> call = apIinterface.getfilter(kelamin, "", "");
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if (response.isSuccessful()){
                        List<CGdata> datas = null;
                        try {
                            JSONObject ba = new JSONObject(response.body().toString());
                            String bb = ba.getString("success");
                            String bb2 = ba.getString("dalam_proses");
                            JSONArray bc = new JSONArray(bb);
                            JSONArray bc2 = new JSONArray(bb2);
                            datas = new ArrayList<>();
                            for (int i = 0 ; i < bc.length(); i++){
                                JSONObject bf = bc.getJSONObject(i);
                                String bid = bf.getString("id");
                                String bidgunaguna = "available";
                                for (int i2 = 0 ; i2 < bc2.length(); i2++){
                                    String bidguna =  String.valueOf(bc2.get(i2));
                                    if(bidguna.equals(bid)){
                                        bidgunaguna = "unavailable";
                                    }
                                }
                                datas.add(new CGdata(
                                        bid,
                                        "http://40.88.4.113/esccortPhotos/" + bf.getString("photo"),
                                        bf.getString("name"),
                                        bf.getString("age"),
                                        bf.getString("gender"),
                                        bf.getString("keahlian"),
                                        bf.getString("address"),
                                        bf.getString("salary"),
                                        "",
                                        bidgunaguna
                                ));
                            }
                            recyclerView2.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView2.setVisibility(View.VISIBLE);
                            Collections.shuffle(datas);
                            adapter = new Adapter(getBaseContext(), datas);
                            recyclerView2.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            error1.setVisibility(View.VISIBLE);
                            prgs.setVisibility(View.GONE);
                        }
                    }else{
                        error1.setVisibility(View.VISIBLE);
                        prgs.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    prgs.setVisibility(View.GONE);
                    error2.setVisibility(View.VISIBLE);
                }
            });

        //}

    }
    /*
    public boolean valid(){
        boolean valid = true;
        if(etumur1.getText().length()<1){
            etumur1.setError("Masukan Umur");
            etumur1.setBackgroundResource(R.drawable.borderred);
            agetv1.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(etumur2.getText().length()<1){
            etumur2.setError("Masukan umur");
            etumur2.setBackgroundResource(R.drawable.borderred);
            agetv1.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
       if(!(etumur1.getText().length() < 1) &&!(etumur2.getText().length() < 1)){
           int satu = Integer.parseInt(etumur1.getText().toString());
           int dua = Integer.parseInt(etumur2.getText().toString());
           if(dua < satu){
               etumur1.setError("Masukan rentang umur yang valid");
               etumur2.setError("Masukan rentang umur yang valid");
               etumur1.setBackgroundResource(R.drawable.borderred);
               etumur2.setBackgroundResource(R.drawable.borderred);
               agetv1.setTextColor(getResources().getColor(R.color.colorRed));
               valid = false;
           }
       }
        if (kelamin == null){
            gendertv1.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }

        return valid;
    }

     */

    public void hand(Boolean err){
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

    void cek_form(final EditText editText, final TextView textView){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length()<1)
                {
                    editText.setBackgroundResource(R.drawable.borderred);
                    textView.setTextColor(getResources().getColor(R.color.colorRed));
                }
                else if (charSequence.length()>=1){
                    editText.setBackgroundResource(R.drawable.borderbluebig);
                    textView.setTextColor(getResources().getColor(R.color.colorDarkblue));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    editText.setBackgroundResource(R.drawable.borderbluebig);
                    textView.setTextColor(getResources().getColor(R.color.colorDarkblue));
                }
                if(!hasFocus){
                    editText.setBackgroundResource(R.drawable.borderblue);
                    textView.setTextColor(getResources().getColor(R.color.darkgray));
                }
            }
        });
    }
}