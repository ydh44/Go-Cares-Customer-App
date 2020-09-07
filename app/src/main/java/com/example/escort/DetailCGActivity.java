package com.example.escort;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailCGActivity extends AppCompatActivity {
    ImageButton back, next;
    ImageView gambarcg;
    TextView namaTv, umurTv, kotaTv, genderTv, gajiTv, ratingTv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailcg);

        Intent i = getIntent();
        String urlgambar = i.getStringExtra("urlgambar");
        String nama = i.getStringExtra("nama");
        String umur = i.getStringExtra("umur");
        String kota = i.getStringExtra("kota");
        String rating = i.getStringExtra("rating");
        String gender = i.getStringExtra("gender");
        String gaji = i.getStringExtra("gaji");

        namaTv = findViewById(R.id.nama);
        kotaTv = findViewById(R.id.kota);
        umurTv = findViewById(R.id.umur);
        genderTv = findViewById(R.id.gender);
        gajiTv = findViewById(R.id.gaji);
        ratingTv = findViewById(R.id.rating);
        back = findViewById(R.id.btnBack);
        next = findViewById(R.id.btnNext);
        gambarcg = findViewById(R.id.imageView);

        namaTv.setText(nama);
        umurTv.setText(umur);
        genderTv.setText(gender);
        ratingTv.setText(rating);
        kotaTv.setText(kota);
        gajiTv.setText(gaji);

        Picasso.get().load(urlgambar).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(gambarcg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailCGActivity.this, CheckoutActivity.class);
                startActivity(i);
            }
        });
    }
}
