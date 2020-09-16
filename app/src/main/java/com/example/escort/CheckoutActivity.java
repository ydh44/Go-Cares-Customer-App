package com.example.escort;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @BindView(R.id.durasiTv) TextView durasiTv;
    @BindView(R.id.durasi2Tv) TextView durasi2Tv;
    @BindView(R.id.durasiEdtx) EditText durasiEt;
    @BindView(R.id.bacdur) RelativeLayout durasiBg;

    @BindView(R.id.genderEdtx)
    RadioGroup genderEt;

    @BindView(R.id.paketEdtx)
    RadioGroup paketEt;

    @OnClick(R.id.btnBack)
            public void pindah(){
        finish();
    }

    //@OnClick(R.id.btnNext)
    //public void next(){
    //    pesan();
    //}

    APIinterface apIinterface;

    CheckBox check;

    String t_useslansia, t_idlansia, t_idcg, t_iduser, t_nama, t_umur, t_gender, t_hobi, t_sakit, t_paket, t_durasi, t_alamat, t_telepon, t_desc;

    Guideline hor1, hor2;

    Boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        ButterKnife.bind(this);

        check = findViewById(R.id.check);

        Intent i = getIntent();
        t_idcg = i.getStringExtra("id");
        if(i.getStringExtra("status").equals("there")){
            t_idlansia = i.getStringExtra("lansia_id");
            t_nama = i.getStringExtra("nama");
            t_umur = i.getStringExtra("umur");
            t_gender = i.getStringExtra("gender");
            t_hobi = i.getStringExtra("hobi");
            t_sakit = i.getStringExtra("sakit");
            checked();

        }else {
            unchecked();
        }



        genderEt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio0:
                        t_gender = "L";
                        genderTv.setTextColor(getResources().getColor(R.color.colorBlack));
                        break;
                    case R.id.radio1:
                        t_gender = "P";
                        genderTv.setTextColor(getResources().getColor(R.color.colorBlack));
                        break;
                }
            }
        });
        paketEt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.harian:
                        t_paket = "harian";
                        paketTv.setTextColor(getResources().getColor(R.color.colorBlack));
                        durasiTv.setVisibility(View.VISIBLE);
                        durasi2Tv.setVisibility(View.VISIBLE);
                        durasiEt.setVisibility(View.VISIBLE);
                        durasiBg.setVisibility(View.VISIBLE);
                        durasi2Tv.setText("HARI");
                        break;
                    case R.id.bulanan:
                        t_paket = "bulanan";
                        paketTv.setTextColor(getResources().getColor(R.color.colorBlack));
                        durasiTv.setVisibility(View.VISIBLE);
                        durasi2Tv.setVisibility(View.VISIBLE);
                        durasiEt.setVisibility(View.VISIBLE);
                        durasiBg.setVisibility(View.VISIBLE);
                        durasi2Tv.setText("BULAN");
                        break;
                }
            }
        });

        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean b) {

                hor1 = findViewById(R.id.cardhor1);
                hor2 = findViewById(R.id.cardhor2);

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) hor1.getLayoutParams();
                ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) hor2.getLayoutParams();

                if (b) {
                    params.guidePercent = 0.0f;
                    params2.guidePercent = 0.9f;
                    hor1.setLayoutParams(params);
                    hor2.setLayoutParams(params2);
                }else {
                    params.guidePercent = 0.095f;
                    params2.guidePercent = 0.97f;
                    hor1.setLayoutParams(params);
                    hor2.setLayoutParams(params2);
                }
            }
        });

        cek_form(durasiEt, durasiTv);
        cek_form(alamatEt, alamatTv);
        cek_form(teleponEt, teleponTv);
        cek_form(deskripsiEt, deskripsiTv);
    }
    public void checked(){
        namaEt.setText(t_nama);
        umurEt.setText(t_umur);
        hobiEt.setText(t_hobi);
        sakitEt.setText(t_sakit);
        t_useslansia = "lama";
        cek_form2(namaEt, namaTv);
        cek_form2(umurEt, umurTv);
        cek_form2(hobiEt, hobiTv);
        cek_form2(sakitEt, sakitTv);
        check.setChecked(true);
        if(t_gender.equals("L")){
            genderEt.check(R.id.radio0);
            t_gender = "L";
        }else if (t_gender.equals("P")){
            genderEt.check(R.id.radio1);
            t_gender = "P";
        }

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    namaEt.setText(t_nama);
                    umurEt.setText(t_umur);
                    hobiEt.setText(t_hobi);
                    sakitEt.setText(t_sakit);
                    checked = true;
                }else {
                    checked = false;
                }
            }
        });
    }
    void unchecked(){
        cek_form(namaEt, namaTv);
        cek_form(umurEt, umurTv);
        cek_form(hobiEt, hobiTv);
        cek_form(sakitEt, sakitTv);
        namaEt.setText("");
        umurEt.setText("");
        hobiEt.setText("");
        sakitEt.setText("");
        t_useslansia = "baru";
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    namaEt.setText(t_nama);
                    umurEt.setText(t_umur);
                    hobiEt.setText(t_hobi);
                    sakitEt.setText(t_sakit);
                }else {
                    unchecked();
                }
            }
        });

    }
    /*void pesan(){
        if(validation()){
            loading(true);
            //api
            String nama, umur, hobi, sakit, alamat, telepon, deskripsi, userid;
            int durasi;
            nama = namaEt.getText().toString();
            umur = umurEt.getText().toString();
            hobi = hobiEt.getText().toString();
            sakit = sakitEt.getText().toString();
            durasi = Integer.parseInt(durasiEt.getText().toString());
            alamat = alamatEt.getText().toString();
            telepon = teleponEt.getText().toString();
            deskripsi = deskripsiEt.getText().toString();
            userid = SessionLog.GetId(CheckoutActivity.this);

            apIinterface = APIClient.GetClient().create(APIinterface.class);
            Call<ResponseBody> call = apIinterface.pesan(
                    nama, umur, kelamin, hobi, sakit, paket, durasi, alamat, telepon, deskripsi, userid, cgid, ""
            );
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        if(response.code() == 200){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String a = jsonObject.getString("success");
                                JSONObject jsonObject2 = new JSONObject(a);
                                String b = jsonObject2.getString("id");
                                String c = jsonObject2.getString("total_bayar");
                                Intent i = new Intent(CheckoutActivity.this , PembayaranActivity.class);
                                i.putExtra("id", b);
                                i.putExtra("total", c);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                                Log.d("TAG", "onResponse: " + a + b + c);
                                loading(false);
                            } catch (JSONException  | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        Toast.makeText(CheckoutActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onResponse: " + response.message() + nama + umur + kelamin + hobi + paket + durasi + alamat + telepon + deskripsi +userid + cgid);
                        loading(false);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(CheckoutActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onFailure: " + t);
                    loading(false);
                }
            });
        }else {

        }

    }*/
    public boolean validation(){
        boolean valid = true;
        if(namaEt.getText().length()<1){
            namaEt.setError("Masukan Email yang valid");
            namaEt.setBackgroundResource(R.drawable.borderred);
            namaTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(hobiEt.getText().length()<1){
            hobiEt.setError("Masukan nama Anda");
            hobiEt.setBackgroundResource(R.drawable.borderred);
            hobiTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(sakitEt.getText().length()<1){
            sakitEt.setError("Masukan nama Anda");
            sakitEt.setBackgroundResource(R.drawable.borderred);
            sakitTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(umurEt.getText().length()<1){
            umurEt.setError("Masukan umur Anda");
            umurEt.setBackgroundResource(R.drawable.borderred);
            umurTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(teleponEt.getText().length()<1 || !Patterns.PHONE.matcher(teleponEt.getText()).matches()){
            teleponEt.setError("Masukan nomor telepon yang valid");
            teleponEt.setBackgroundResource(R.drawable.borderred);
            teleponTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(alamatEt.getText().length()<1){
            alamatEt.setError("Masukan alamat Anda");
            alamatEt.setBackgroundResource(R.drawable.borderred);
            alamatTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(deskripsiEt.getText().length()<1) {
            deskripsiEt.setError("Masukan alamat Anda");
            deskripsiEt.setBackgroundResource(R.drawable.borderred);
            deskripsiTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(durasiEt.getText().length()<1) {
            durasiEt.setBackgroundResource(R.drawable.borderred);
            durasiTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if (t_gender == null){
            genderTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if (t_paket == null){
            paketTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        return valid;
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
    void cek_form2(final EditText editText, final TextView textView){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
                check.setChecked(false);
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
    public void loading(Boolean status){
        RelativeLayout prgbar;
        TextView titleTv;
        CardView cv;
        prgbar = findViewById(R.id.progressbar);
        titleTv = findViewById(R.id.tit);
        cv = findViewById(R.id.cv2);
        if(status){
            prgbar.setVisibility(View.VISIBLE);
            titleTv.setVisibility(View.GONE);
            cv.setVisibility(View.INVISIBLE);
        }else {
            prgbar.setVisibility(View.INVISIBLE);
            titleTv.setVisibility(View.VISIBLE);
            cv.setVisibility(View.VISIBLE);
        }
    }
}