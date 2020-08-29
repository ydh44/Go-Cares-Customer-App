package com.example.escort;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailCGActivity extends AppCompatActivity {
    TextView namaTv, umurTv, kotaTv, genderTv, gajiTv, ratingTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailcg);

        Intent i = getIntent();
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
        namaTv.setText(nama);
        umurTv.setText(umur);
        genderTv.setText(gender);
        ratingTv.setText(rating);
        kotaTv.setText(kota);
        gajiTv.setText(gaji);

    }
}
