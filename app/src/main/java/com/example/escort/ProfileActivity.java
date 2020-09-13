package com.example.escort;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {
    @OnClick(R.id.backButton)
    void back(){
        finish();
    }

    @OnClick(R.id.buttonout)
    void out(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setMessage("Logout dari akun ?")
                .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SessionLog.Delete(getBaseContext());
                        Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
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

        namaTv.setText(nama);
        emailTv.setText(email);
        umurTv.setText(umur);
        genderTv.setText(gender);
        teleponTv.setText(telepon);
        alamatTv.setText(alamat);

    }

}