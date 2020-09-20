package com.example.escort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailLansiaActivity extends AppCompatActivity {

    @OnClick(R.id.btnBack)
    void back(){
        finish();
    }

    @BindView(R.id.nama)
    TextView namaTv;
    @BindView(R.id.umur)
    TextView umurTv;
    @BindView(R.id.gender)
    TextView genderTv;
    @BindView(R.id.hobi)
    TextView hobiTv;
    @BindView(R.id.riwayat)
    TextView riwayatTv;

    String nama, umur, gender, hobi, riwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaillansia);
        ButterKnife.bind(this);

        Intent i = getIntent();
        nama = i.getStringExtra("nama");
        umur = i.getStringExtra("umur");
        gender = i.getStringExtra("gender");
        hobi= i.getStringExtra("hobi");
        riwayat = i.getStringExtra("riwayat");

        ConstraintLayout.LayoutParams paramsa = (ConstraintLayout.LayoutParams) hobiTv.getLayoutParams();
        ConstraintLayout.LayoutParams paramsb = (ConstraintLayout.LayoutParams) riwayatTv.getLayoutParams();

        if(hobi.length() >= 32 && hobi.length() < 64){
            paramsa.matchConstraintPercentHeight = (float) 0.067f;
            hobiTv.setLayoutParams(paramsa);
        }else if(hobi.length() >= 64 && hobi.length() < 95){
            paramsa.matchConstraintPercentHeight = (float) 0.105f;
            hobiTv.setLayoutParams(paramsa);
        }else if(hobi.length() >= 95){
            paramsa.matchConstraintPercentHeight = (float) 0.135f;
            hobiTv.setLayoutParams(paramsa);
        }

        if(riwayat.length() >= 32 && riwayat.length() < 64){
            paramsb.matchConstraintPercentHeight = (float) 0.075f;
            riwayatTv.setLayoutParams(paramsb);
        }else if(riwayat.length() >= 64 && riwayat.length() < 95){
            paramsb.matchConstraintPercentHeight = (float) 0.105f;
            riwayatTv.setLayoutParams(paramsb);
        }else if(riwayat.length() >= 95){
            paramsb.matchConstraintPercentHeight = (float) 0.135f;
            riwayatTv.setLayoutParams(paramsb);
        }
        namaTv.setText(nama);
        umurTv.setText(umur + " Tahun");
        if(gender.equals("L")){
            genderTv.setText("Laki - laki");
        }else if(gender.equals("P")){
            genderTv.setText("Perempuan");
        }else {
            genderTv.setText("-");
        }
        hobiTv.setText(hobi);
        umurTv.setText(umur + " Tahun");
        riwayatTv.setText(riwayat);
    }
}