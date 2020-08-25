package com.example.escort;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class RegActivity extends AppCompatActivity {
    Button buttonRegister, buttonLogin;
    EditText emailEt, namaEt, umurEt, teleponEt, alamatEt, password1Et, password2Et;
    TextView emailTv, namaTv, umurTv, teleponTv, alamatTv, password1Tv, password2Tv;
    RadioGroup kelaminEt;
    RelativeLayout prgbar;
    ProgressBar prgbar2;
    private Object Intent;
    private static final String TAG = "RegActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);

        buttonLogin = (Button) findViewById(R.id.buttonlogin);
        buttonRegister = (Button) findViewById(R.id.buttonreg);
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
        kelaminEt = (RadioGroup) findViewById(R.id.genderEdtx);
        prgbar = (RelativeLayout) findViewById(R.id.progressbar);
        prgbar2 = (ProgressBar) findViewById(R.id.prgs);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent = new Intent(RegActivity.this, MainActivity.class);
                startActivity((android.content.Intent)Intent);
                finish();
            }
        });
        AndroidNetworking.initialize(this);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
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
            AndroidNetworking.post("https://apitestsatu.000webhostapp.com/public/home")
                    .addBodyParameter("email", emailEt.getText().toString())
                    .addBodyParameter("nama", namaEt.getText().toString())
                    .addBodyParameter("umur", umurEt.getText().toString())
                    .addBodyParameter("kelamin", "pria")
                    .addBodyParameter("telepon", teleponEt.getText().toString())
                    .addBodyParameter("alamat", alamatEt.getText().toString())
                    .addBodyParameter("password", password1Et.getText().toString())
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d(TAG, "onResponse: " + response.getString("status"));
                                String status = response.getString("status");
                                if (status.equals("200")){
                                    prgbar.setVisibility(View.GONE);
                                    Intent = new Intent(RegActivity.this, MainActivity.class);
                                    startActivity((android.content.Intent)Intent);
                                    finish();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            prgbar.setVisibility(View.GONE);
                            if (error.getErrorCode() != 0) {
                                // received error from server
                                // error.getErrorCode() - the error code from server
                                // error.getErrorBody() - the error body from server
                                // error.getErrorDetail() - just an error detail
                                Toast.makeText(getApplicationContext(), "Error," + error.getErrorDetail(), Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                                Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                                Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                            } else {
                                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                                Toast.makeText(getApplicationContext(), "Error," + error.getErrorDetail(), Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                            }
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
        if(password1Et.getText().length()<6){
            password1Et.setError("Masukan password minimal 6 karakter");
            password1Et.setBackgroundResource(R.drawable.borderred);
            password1Tv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        if(password2Et.getText().length()<6){
            password2Et.setBackgroundResource(R.drawable.borderred);
            password2Tv.setTextColor(getResources().getColor(R.color.colorRed));
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