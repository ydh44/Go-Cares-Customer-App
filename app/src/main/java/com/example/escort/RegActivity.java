package com.example.escort;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegActivity extends AppCompatActivity {
    @OnClick(R.id.buttonlogin)
            public void pindah(){
        Intent a = new Intent(RegActivity.this, MainActivity.class);
        startActivity(a);
        finish();
    }
    @OnClick(R.id.buttonreg)
            void reg(){
        register();
    }

    APIinterface apIinterface;
    EditText emailEt, namaEt, umurEt, teleponEt, alamatEt, password1Et, password2Et;
    TextView emailTv, namaTv, umurTv, kelaminTv, teleponTv, alamatTv, password1Tv, password2Tv;
    RadioGroup kelaminEt;
    RelativeLayout prgbar;
    ProgressBar prgbar2;
    Window window;
    String kelamin = null;

    private static final String TAG = "RegActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        ButterKnife.bind(this);

        emailEt = (EditText) findViewById(R.id.emailEdtx);
        namaEt = (EditText) findViewById(R.id.nameEdtx);
        umurEt = (EditText) findViewById(R.id.ageEdtx);
        teleponEt = (EditText) findViewById(R.id.phoneEdtx);
        alamatEt = (EditText) findViewById(R.id.addEdtx);
        password1Et = (EditText) findViewById(R.id.pass1Edtx);
        password2Et = (EditText) findViewById(R.id.pass2Edtx);
        emailTv = (TextView) findViewById(R.id.emailTV);
        namaTv = (TextView) findViewById(R.id.nameTV);
        umurTv = (TextView) findViewById(R.id.ageTV);
        teleponTv = (TextView) findViewById(R.id.phoneTV);
        alamatTv = (TextView) findViewById(R.id.addTV);
        password1Tv = (TextView) findViewById(R.id.pass1TV);
        password2Tv = (TextView) findViewById(R.id.pass2TV);
        kelaminTv = (TextView) findViewById(R.id.genderTV);
        kelaminEt = (RadioGroup) findViewById(R.id.genderEdtx);
        prgbar = (RelativeLayout) findViewById(R.id.progressbar);
        prgbar2 = (ProgressBar) findViewById(R.id.prgs);
        window = this.getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        kelaminEt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio0 :
                        kelamin = "L";
                        break;
                    case R.id.radio1 :
                        kelamin = "P";
                        break;
                }
            }
        });


        cek_form(emailEt, emailTv);
        cek_form(namaEt, namaTv);
        cek_form(umurEt, umurTv);
        cek_form(teleponEt, teleponTv);
        cek_form(alamatEt, alamatTv);
        cek_form(password1Et, password1Tv);
        cek_form(password2Et, password2Tv);
    }

    void register()
    {
        if(!validation()){
            password1Et.setText(null);
            password2Et.setText(null);
        }else{
            prgbar.setVisibility(View.VISIBLE);
            //api
            String email, nama, umur, alamat, telepon, password1, password2;
            email = emailEt.getText().toString();
            nama = namaEt.getText().toString();
            umur = umurEt.getText().toString();
            telepon = teleponEt.getText().toString();
            alamat = alamatEt.getText().toString();
            password1 = password1Et.getText().toString();
            password2 = password2Et.getText().toString();

            apIinterface = APIClient.GetClient().create(APIinterface.class);
            Call<ResponseBody> call = apIinterface.register(
            email, nama, umur, kelamin, telepon, alamat, password1, password2);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        if(response.code() == 200){
                            Intent i = new Intent(RegActivity.this, LoginActivity.class);
                            i.putExtra("status", true);
                            prgbar.setVisibility(View.INVISIBLE);
                            startActivity(i);
                        }
                    }else{
                        Toast.makeText(RegActivity.this, "Email Telah Digunakan", Toast.LENGTH_SHORT).show();
                        prgbar.setVisibility(View.INVISIBLE);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(RegActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + t);
                    prgbar.setVisibility(View.INVISIBLE);
                }
            });


        }
    }

    public boolean validation()
    {
        boolean valid = true;
        if(emailEt.getText().length()<1 || !Patterns.EMAIL_ADDRESS.matcher(emailEt.getText()).matches()){
            emailEt.setError("Masukan Email yang valid");
            emailEt.setBackgroundResource(R.drawable.borderred);
            emailTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(namaEt.getText().length()<1){
            namaEt.setError("Masukan nama Anda");
            namaEt.setBackgroundResource(R.drawable.borderred);
            namaTv.setTextColor(getResources().getColor(R.color.colorRed));
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
        if(password1Et.getText().length()<8){
            password1Et.setError("Masukan password minimal 8 karakter");
            password1Et.setBackgroundResource(R.drawable.borderred);
            password1Tv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(password2Et.getText().length()<8){
            password2Et.setBackgroundResource(R.drawable.borderred);
            password2Tv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if (kelamin == null){
            kelaminTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(!password1Et.getText().toString().equals(password2Et.getText().toString())){
            password1Et.setBackgroundResource(R.drawable.borderred);
            password1Tv.setTextColor(getResources().getColor(R.color.colorRed));
            password2Et.setBackgroundResource(R.drawable.borderred);
            password2Tv.setTextColor(getResources().getColor(R.color.colorRed));
            password2Et.setError("Masukan password yang sama");
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
}