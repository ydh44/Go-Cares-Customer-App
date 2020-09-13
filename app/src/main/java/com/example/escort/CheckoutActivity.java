package com.example.escort;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckoutActivity extends AppCompatActivity {

    @BindView(R.id.nameEdtx) EditText namaEt;
    @BindView(R.id.ageEdtx) EditText umurEt;
    @BindView(R.id.hobbyEdtx) EditText hobiEt;
    @BindView(R.id.sickEdtx) EditText sakitEt;
    @BindView(R.id.addressEdtx) EditText alamatEt;
    @BindView(R.id.phoneEdtx) EditText teleponEt;
    @BindView(R.id.descEdtx) EditText deskripsiEt;

    @BindView(R.id.nameTv) TextView namaTv;
    @BindView(R.id.ageTv) TextView umurTv;
    @BindView(R.id.genderTv) TextView genderTv;
    @BindView(R.id.hobbyTv) TextView hobiTv;
    @BindView(R.id.sickTv) TextView sakitTv;
    @BindView(R.id.pkgTv) TextView paketTv;
    @BindView(R.id.addressTv) TextView alamatTv;
    @BindView(R.id.phoneTv) TextView teleponTv;
    @BindView(R.id.descTv) TextView deskripsiTv;

    @OnClick(R.id.btnBack)
            public void pindah(){
        finish();
    }

    @OnClick(R.id.btnNext)
    public void next(){
        Intent i = new Intent(CheckoutActivity.this, PembayaranActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        ButterKnife.bind(this);

        cek_form(namaEt, namaTv);
        cek_form(umurEt, umurTv);
        cek_form(hobiEt, hobiTv);
        cek_form(sakitEt, sakitTv);
        cek_form(alamatEt, alamatTv);
        cek_form(teleponEt, teleponTv);
        cek_form(deskripsiEt, deskripsiTv);
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
                    textView.setTextColor(getResources().getColor(R.color.colorBlack));
                }
            }
        });
    }
}