package com.example.escort;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.LifecycleOwner;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    APIinterface apIinterface;
    Window window;

    @BindView(R.id.cons) ConstraintLayout constraintLayout;

    @OnClick(R.id.buttonreg)
    public void pindah() {
        Intent i = new Intent (LoginActivity.this, RegActivity.class);
        startActivity(i);
        finish();
    }
    @OnClick(R.id.buttonlog)
    void in(){
        login();
    }
    @BindView(R.id.emailEdtx) EditText emailEt;
    @BindView(R.id.passEdtx) EditText passEt;
    @BindView(R.id.emailTv) TextView emailTv;
    @BindView(R.id.passTv) TextView passTv;
    @BindView(R.id.progressbar) RelativeLayout prgbar;
    @BindView(R.id.text) TextView text;
    @BindView(R.id.logo) ImageView logo;
    @BindView(R.id.cardhor1) Guideline hor1;
    @BindView(R.id.cardhor2) Guideline hor2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        constraintLayout = findViewById(R.id.cons);
        window = this.getWindow();
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) hor1.getLayoutParams();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) hor2.getLayoutParams();

        Intent i = getIntent();
        boolean status = i.getBooleanExtra("status", false);
        if(status){
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "Berhasil Daftar, Silahkan Masuk", BaseTransientBottomBar.LENGTH_LONG)
                    .setDuration(10000);
            snackbar.show();
        }

        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        KeyboardVisibilityEvent.setEventListener(LoginActivity.this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean b) {
                if (b){
                    text.setVisibility(View.GONE);
                    logo.setVisibility(View.GONE);
                    params.guidePercent = 0.0f;
                    params2.guidePercent = 0.7f;
                    hor1.setLayoutParams(params);
                    hor2.setLayoutParams(params2);

                }else {
                    text.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.VISIBLE);
                    params.guidePercent = 0.35f;
                    params2.guidePercent = 0.85f;
                    hor1.setLayoutParams(params);
                    hor2.setLayoutParams(params2);
                }
            }
        });

        cek_form(emailEt, emailTv);
        cek_form(passEt, passTv);
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
    void login()
    {
        String email, password;
        email = emailEt.getText().toString();
        password = passEt.getText().toString();
        if(!validation()){
            passEt.setText(null);
        }else {
            prgbar.setVisibility(View.VISIBLE);
            apIinterface = APIClient.GetClient().create(APIinterface.class);
            Call<ResponseBody> call = apIinterface.login(
                    email, password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        if(response.code() == 200){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String a = jsonObject.getString("success");
                                JSONObject jsonObject2 = new JSONObject(a);
                                String b = jsonObject2.getString("token");
                                Log.d("TAG", "onResponse: " + b);
                                SessionLog.SaveStatus(LoginActivity.this, true);
                                SessionLog.SaveToken(LoginActivity.this, b);
                                prgbar.setVisibility(View.INVISIBLE);
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        prgbar.setVisibility(View.INVISIBLE);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
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
        if(passEt.getText().length()<8){
            passEt.setError("Masukan Password Anda");
            passEt.setBackgroundResource(R.drawable.borderred);
            passTv.setTextColor(getResources().getColor(R.color.colorRed));
            valid = false;
        }
        return valid;
    }
}