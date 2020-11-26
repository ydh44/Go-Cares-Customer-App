package com.caregiver.gocares;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.views.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {
    @OnClick(R.id.backButton)
    void back(){
        finish();
    }

    @OnClick(R.id.buttonout)
    public void out(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setMessage("Logout dari akun ?")
                .setPositiveButton("Lanjutkan", (dialog, id) -> {
                    SessionLog.Delete(ProfileActivity.this);
                    Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton("Batalkan", (dialog, id) -> {
                    // User cancelled the dialog
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    @BindView(R.id.nama) TextView namaTv;
    @BindView(R.id.email) TextView emailTv;
    @BindView(R.id.umur) TextView umurTv;
    @BindView(R.id.gender) TextView genderTv;
    @BindView(R.id.telepon) TextView teleponTv;
    @BindView(R.id.alamat) TextView alamatTv;
    @BindView(R.id.a) TextView a;
    @BindView(R.id.b) TextView b;
    @BindView(R.id.c) TextView c;
    @BindView(R.id.d) TextView d;
    @BindView(R.id.e) TextView e;
    @BindView(R.id.cardView) CardView cv;
    @BindView(R.id.noo) TextView error;

    String nama, email, umur, gender, telepon, alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);

        Intent i = getIntent();
        nama = i.getStringExtra("nama");
        email = i.getStringExtra("email");
        umur = i.getStringExtra("umur");
        gender = i.getStringExtra("gender");
        telepon = i.getStringExtra("telepon");
        alamat = i.getStringExtra("alamat");

        if(i.getStringExtra("status") == null){
            error.setVisibility(View.VISIBLE);
            cv.setVisibility(View.GONE);
            a.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
            namaTv.setVisibility(View.GONE);
            emailTv.setVisibility(View.GONE);
            umurTv.setVisibility(View.GONE);
            genderTv.setVisibility(View.GONE);
            teleponTv.setVisibility(View.GONE);
            alamatTv.setVisibility(View.GONE);
        }else {
            ConstraintLayout.LayoutParams paramsa = (ConstraintLayout.LayoutParams) alamatTv.getLayoutParams();

            if(alamat.length() >= 32 && alamat.length() < 64){
                paramsa.matchConstraintPercentHeight = (float) 0.057f;
                alamatTv.setLayoutParams(paramsa);
            }else if(alamat.length() >= 64 && alamat.length() < 95){
                paramsa.matchConstraintPercentHeight = (float) 0.95f;
                alamatTv.setLayoutParams(paramsa);
            }else if(alamat.length() >= 95){
                paramsa.matchConstraintPercentHeight = (float) 0.125f;
                alamatTv.setLayoutParams(paramsa);
            }
            namaTv.setText(nama);
            emailTv.setText(email);
            umurTv.setText(umur + " Tahun");
            if(gender.equals("L")){
                genderTv.setText("Laki - laki");
            }else if(gender.equals("P")){
                genderTv.setText("Perempuan");
            }else {
                genderTv.setText("-");
            }
            teleponTv.setText(telepon);
            alamatTv.setText(alamat);
        }

    }

}