package com.caregiver.gocares;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.caregiver.gocares.databinding.LoginBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    LoginBinding binding;
    final LoadingDialog loadingDialog = new LoadingDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

       binding.toolbar.setOnMenuItemClickListener(item -> {
           if (item.getItemId() == R.id.back) {
               finish();
               UIUtil.hideKeyboard(this);
           }
           return false;
       });

        binding.buttonlog.setOnClickListener(view1 -> {
            login();
            UIUtil.hideKeyboard(LoginActivity.this);
        });

        Intent i = getIntent();
        boolean status = i.getBooleanExtra("status", false);
        if(status){
            Snackbar snackbar = Snackbar
                    .make(binding.cons, "Berhasil Daftar, Silahkan Masuk", BaseTransientBottomBar.LENGTH_LONG)
                    .setDuration(10000);
            snackbar.show();
        }

        new CekForm(this , binding.etEmail, binding.ilEmail, true);
        new CekForm(this, binding.etPass, binding.ilPass, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    void login()
    {
        String email, password;
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPass.getText().toString().trim();
        if(!validation()){
            binding.etPass.setText(null);
        }else {
            loadingDialog.startLoading();
            APIinterface apIinterface = APIClient.GetClient().create(APIinterface.class);
            Call<ResponseBody> call = apIinterface.login(
                    email, password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
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
                                loadingDialog.dismissLoading();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Email / Password Salah", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissLoading();
                        binding.ilEmail.setErrorEnabled(true);
                        binding.ilEmail.setError("Email atau password salah");
                        binding.ilPass.setErrorEnabled(true);
                        binding.ilPass.setError("Email atau password salah");
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    Log.d("s", "onFailure: " + t);
                    loadingDialog.dismissLoading();
                }
            });
        }
    }

    public boolean validation()
    {
        boolean valid = true;
        if(binding.etEmail.getText().length()<1 || !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()){
            binding.ilEmail.setErrorEnabled(true);
            binding.ilEmail.setError("Masukan email yang valid");
            binding.ilEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
            valid = false;
        }
        if(binding.etPass.getText().length()<3){
            binding.ilPass.setErrorEnabled(true);
            binding.ilPass.setError("Masukan password Anda");
            binding.ilPass.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
            valid = false;
        }
        return valid;
    }

}