package com.caregiver.gocares;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caregiver.gocares.utils.SessionLog;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    @Nullable
    @BindView(R.id.backdur) RelativeLayout durasiBg;

    @Nullable
    @BindView(R.id.cv2) CardView card;

    @BindView(R.id.genderEdtx)
    RadioGroup genderEt;

    @BindView(R.id.paketEdtx)
    RadioGroup paketEt;

    @OnClick(R.id.btnBack)
            public void pindah(){
        finish();
    }

    @OnClick(R.id.btnNext)
    public void next(){
       pesan();
    }

    APIinterface apIinterface;

    CheckBox check;

    String useslansia, idlansia, idcg, iduser, nama, umur, gender, hobi, sakit, paket, durasi, alamat, telepon, desc;
    String t_idlansia, t_nama, t_umur, t_gender, t_hobi, t_sakit;

    Boolean status, status2;


    Guideline hor1, hor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_v2);
        ButterKnife.bind(this);
        Window window = this.getWindow();

        check = findViewById(R.id.check);
        status2 = false;

        Intent i = getIntent();
        idcg = i.getStringExtra("id");
        if(i.getStringExtra("status").equals("there")){
            t_idlansia = i.getStringExtra("lansia_id");
            t_nama = i.getStringExtra("nama");
            t_umur = i.getStringExtra("umur");
            t_gender = i.getStringExtra("gender");
            t_hobi = i.getStringExtra("hobi");
            t_sakit = i.getStringExtra("sakit");
            check.setChecked(true);
            namaEt.setText(t_nama);
            umurEt.setText(t_umur);
            hobiEt.setText(t_hobi);
            sakitEt.setText(t_sakit);
            if(t_gender.equals("L")){
                genderEt.check(R.id.radio0);
                gender = "L";
            }else {
                genderEt.check(R.id.radio1);
                gender = "P";
            }
            status = true;
        }else {
            status = false;
            check.setVisibility(View.GONE);
        }

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                gender = t_gender;
                if(b){
                    namaEt.setText(t_nama);
                    umurEt.setText(t_umur);
                    hobiEt.setText(t_hobi);
                    sakitEt.setText(t_sakit);
                    status2 = true;
                    if(t_gender.equals("L")){
                        gender = "L";
                        genderEt.check(R.id.radio0);
                        status2 = false;
                    }else {
                        gender = "P";
                        genderEt.check(R.id.radio1);
                        status2 = false;
                    }
                }else{
                }
            }
        });

        genderEt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio0:
                        gender = "L";
                        genderTv.setTextColor(getResources().getColor(R.color.colorBlack));
                        break;
                    case R.id.radio1:
                        gender = "P";
                        genderTv.setTextColor(getResources().getColor(R.color.colorBlack));
                        break;
                }if(!status2){
                    if(t_gender != null){
                        if(!t_gender.equals(gender)){
                            check.setChecked(false);
                            status2 = false;
                        }
                    }

                }
            }
        });
        paketEt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.harian:
                        paket = "harian";
                        paketTv.setTextColor(getResources().getColor(R.color.colorBlack));
                        durasiTv.setVisibility(View.VISIBLE);
                        durasi2Tv.setVisibility(View.VISIBLE);
                        durasiEt.setVisibility(View.VISIBLE);
                        durasiBg.setVisibility(View.VISIBLE);
                        durasi2Tv.setText("HARI");
                        break;
                    case R.id.bulanan:
                        paket = "bulanan";
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

        /*
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean b) {

                hor1 = findViewById(R.id.cardhor1);
                hor2 = findViewById(R.id.cardhor2);

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) hor1.getLayoutParams();
                ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) hor2.getLayoutParams();
                ConstraintLayout.LayoutParams params3 = (ConstraintLayout.LayoutParams) card.getLayoutParams();

                if (b) {
                    params.guidePercent = 0.01f;
                    params2.guidePercent = 0.9f;
                    params3.dimensionRatio = "1:1.15";
                    hor1.setLayoutParams(params);
                    hor2.setLayoutParams(params2);
                    card.setLayoutParams(params3);
                }else {
                    params.guidePercent = 0.095f;
                    params2.guidePercent = 0.97f;
                    params3.dimensionRatio = "1:1.5";
                    hor1.setLayoutParams(params);
                    hor2.setLayoutParams(params2);
                    card.setLayoutParams(params3);
                }
            }
        });
         */
        cek_form2(namaEt, namaTv, t_nama);
        cek_form2(umurEt, umurTv, t_umur);
        cek_form2(hobiEt, hobiTv, t_hobi);
        cek_form2(sakitEt, sakitTv, t_sakit);
        cek_form(durasiEt, durasiTv);
        cek_form(alamatEt, alamatTv);
        cek_form(teleponEt, teleponTv);
        cek_form(deskripsiEt, deskripsiTv);
    }
      // /*
    void pesan(){
        Log.d("TAG", "onCreate: " + idcg);
        UIUtil.hideKeyboard(CheckoutActivity.this);
        getCgStatus();
        if(validation()){
            loading(true);
            //api
            Call<ResponseBody> call;
            String alamat, telepon, deskripsi, userid, cgid, pakets;
            int durasi;

            userid = SessionLog.GetId(CheckoutActivity.this);
            cgid = idcg;
            durasi = Integer.parseInt(durasiEt.getText().toString().trim());
            pakets = paket;
            alamat = alamatEt.getText().toString().trim();
            telepon = teleponEt.getText().toString().trim();
            deskripsi = deskripsiEt.getText().toString().trim();

            apIinterface = APIClient.GetClient().create(APIinterface.class);
            if(status){
                if(namaEt.getText().toString().trim().equals(t_nama) &&
                        umurEt.getText().toString().trim().equals(t_umur) &&
                        hobiEt.getText().toString().trim().equals(t_hobi) &&
                        sakitEt.getText().toString().trim().equals(t_sakit) &&
                        gender.equals(t_gender)){
                    useslansia = "lama";
                    call = apIinterface.pesan(
                            "", "", "", "", "", pakets, durasi, alamat, telepon, deskripsi, userid, cgid, t_idlansia, useslansia
                    );
                    Log.d("TAG", "pesan: " + pakets + durasi + alamat + telepon + deskripsi + userid + cgid + t_idlansia + useslansia);
                }else {
                    useslansia = "baru";
                    String nama = namaEt.getText().toString().trim();
                    String umur = umurEt.getText().toString().trim();
                    String hobi = hobiEt.getText().toString().trim();
                    String sakit = sakitEt.getText().toString().trim();
                    String kelamin = gender;
                    call = apIinterface.pesan(
                            nama, umur, kelamin, hobi, sakit, pakets, durasi, alamat, telepon, deskripsi, userid, cgid, t_idlansia, useslansia
                    );
                    Log.d("TAG", "pesan: " +nama + umur + hobi + sakit + kelamin + pakets + durasi + alamat + telepon + deskripsi + userid + cgid + t_idlansia + useslansia);
                }
            }else{
                String nama = namaEt.getText().toString().trim();
                String umur = umurEt.getText().toString().trim();
                String hobi = hobiEt.getText().toString().trim();
                String sakit = sakitEt.getText().toString().trim();
                String kelamin = gender;
                call = apIinterface.pesan(
                        nama, umur, kelamin, hobi, sakit, pakets, durasi, alamat, telepon, deskripsi, userid, cgid, "", "baru"
                );
                Log.d("TAG", "pesan: " +nama + umur + hobi + sakit + kelamin + pakets + durasi + alamat + telepon + deskripsi + userid + cgid);
            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Log.d("TAG", "onResponse: " + response.code());
                        if(response.code() == 200){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String a = jsonObject.getString("success");
                                JSONObject jsonObject2 = new JSONObject(a);
                                String b = jsonObject2.getString("id");
                                String c = jsonObject2.getString("total_bayar");
                                Intent i = new Intent(CheckoutActivity.this , PembayaranActivity.class);
                                i.putExtra("idcg", idcg);
                                i.putExtra("id", b);
                                i.putExtra("total", c);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Log.d("TAG", "onCreate: " + cgid);
                                startActivity(i);
                                finish();
                                Log.d("TAG", "onResponse: " + a + b + c);
                                loading(false);
                            } catch (JSONException  | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        Log.d("TAG", "onResponse: " +  response.body().toString());
                        Toast.makeText(CheckoutActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
        }

    }


    public void getCgStatus(){
        loading(true);
        APIinterface apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<ResponseBody> call = apIinterface.getstatus(idcg);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    finish();
                    loading(false);
                }else if (response.code() == 401){
                    loading(false);
                }else {
                    finish();
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
    }
    // */
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
            teleponEt.setError("Masukan nomor hp yang valid");
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
            deskripsiEt.setError("Masukan deskripsi pekerjaan");
            deskripsiEt.setBackgroundResource(R.drawable.borderred);
            deskripsiTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        int duras = 0;
        if(durasiEt.getText().length() != 0){
            duras = Integer.parseInt(durasiEt.getText().toString());
        }
        if(durasiEt.getText().length()<1 || duras < 1) {
            durasiEt.setError("Masukan durasi");
            durasiEt.setBackgroundResource(R.drawable.borderred);
            durasiTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if (gender == null){
            genderTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if (paket == null){
            paketTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(teleponEt.getText().toString().length() <= 10 || teleponEt.getText().length() > 13){
            teleponEt.setError("Masukan nomor hp yang valid");
            teleponEt.setBackgroundResource(R.drawable.borderred);
            teleponTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(!teleponEt.getText().toString().startsWith("0")){
            teleponEt.setError("Masukan nomor hp, harus diawali dengan nol");
            teleponEt.setBackgroundResource(R.drawable.borderred);
            teleponTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(teleponEt.getText().length() != 0){
            String tel = null;
            tel = teleponEt.getText().toString().substring(0, 2);
            if(!tel.equals("08")){
                teleponEt.setError("Masukan nomor hp, harus diawali dengan nol");
                teleponEt.setBackgroundResource(R.drawable.borderred);
                teleponTv.setTextColor(getResources().getColor(R.color.colorRed));
                valid = false;
            }
        }

        return valid;
    }

    void cek_form2(final EditText editText, final TextView textView, final String value){
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
                if(!editText.getText().toString().equals(value)){
                    check.setChecked(false);
                }
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

    public void loading(Boolean status){
        RelativeLayout prgbar;
        TextView titleTv;
        CardView cv;
        prgbar = findViewById(R.id.progressbar);
        titleTv = findViewById(R.id.tit);
        cv = findViewById(R.id.cv2);
        if(status){
            prgbar.setVisibility(View.VISIBLE);
            cv.setVisibility(View.INVISIBLE);
        }else {
            prgbar.setVisibility(View.INVISIBLE);
            cv.setVisibility(View.VISIBLE);
        }
    }
}