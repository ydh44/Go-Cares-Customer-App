package com.caregiver.gocares;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.caregiver.gocares.databinding.RegBinding;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegActivity extends AppCompatActivity {

    RegBinding binding;
    LoadingDialog loadingDialog = new LoadingDialog(this);
    APIinterface apIinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MaterialAutoCompleteTextView etGender = findViewById(R.id.etGender);
        String[] pilgender = new String[] {
                "Laki-laki",
                "Perempuan"
        };
        ArrayAdapter<String> adapgender = new ArrayAdapter<>(
                this,
                R.layout.dropdown_item,
                pilgender
        );
       etGender.setAdapter(adapgender);

       binding.toolbar.setOnMenuItemClickListener(item -> {
         if (item.getItemId() == R.id.back) {
           finish();
           UIUtil.hideKeyboard(this);
         }
         return false;
       });

      binding.buttonreg.setOnClickListener(item ->
              {
                register();
                UIUtil.hideKeyboard(this);
              }
      );

      new CekForm(this , binding.etEmail, binding.ilEmail, false);
      new CekForm(this, binding.etNama, binding.ilNama, false);
      new CekForm(this , binding.etUmur, binding.ilUmur, false);
      new CekForm(this, binding.etGender, binding.ilGender, false);
      new CekForm(this , binding.etTelepon, binding.ilTelepon, false);
      new CekForm(this, binding.etAlamat, binding.ilAlamat, false);
      new CekForm(this , binding.etPass1, binding.ilPass1, false);
      new CekForm(this, binding.etPass2, binding.ilPass2, false);
    }

    void register()
    {
        if(!validation()){
            binding.etPass1.setText(null);
            binding.etPass2.setText(null);
        }else{
            loadingDialog.startLoading();
            //api
            String email, nama, umur, gender, alamat, telepon, password1, password2;
            email = binding.etEmail.getText().toString().trim();
            nama = binding.etNama.getText().toString().trim();
            umur = binding.etUmur.getText().toString().trim();
            if(binding.etGender.getText().toString().equals("Laki-laki")){
              gender = "l";
            }else {
              gender = "P";
            }
            telepon = binding.etTelepon.getText().toString().trim();
            alamat = binding.etAlamat.getText().toString().trim();
            password1 = binding.etPass1.getText().toString().trim();
            password2 = binding.etPass2.getText().toString().trim();

            apIinterface = APIClient.GetClient().create(APIinterface.class);
            Call<ResponseBody> call = apIinterface.register(
            email, nama, umur, gender, telepon, alamat, password1, password2);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        if(response.code() == 200){
                            Intent i = new Intent(RegActivity.this, LoginActivity.class);
                            i.putExtra("status", true);
                            loadingDialog.dismissLoading();
                            startActivity(i);
                            finish();
                        }
                    }else{
                        Toast.makeText(RegActivity.this, "Email Telah Digunakan", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissLoading();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(RegActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onFailure: " + t);
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
            valid = false;
        }
        if(binding.etNama.getText().length()<1){
            binding.ilNama.setErrorEnabled(true);
            binding.ilNama.setError("Masukan nama Anda");
            valid = false;
        }
        if(binding.etUmur.getText().length()<1){
          binding.ilUmur.setErrorEnabled(true);
          binding.ilUmur.setError("Masukan umur Anda");
          valid = false;
        }
        if(binding.etGender.getText().length()<1){
          binding.ilGender.setErrorEnabled(true);
          binding.ilGender.setError("Masukan jenis kelamin");
          valid = false;
        }
        if(binding.etTelepon.getText().length()<1 || !Patterns.PHONE.matcher(binding.etTelepon.getText()).matches()){
          binding.ilTelepon.setErrorEnabled(true);
          binding.ilTelepon.setError("Masukan nomor telepon yang valid");
          valid = false;
        }
        if(binding.etAlamat.getText().length()<1){
          binding.ilAlamat.setErrorEnabled(true);
          binding.ilAlamat.setError("Masukan alamat Anda");
          valid = false;
        }
        if(binding.etPass1.getText().length()<8){
          binding.ilPass1.setErrorEnabled(true);
          binding.ilPass1.setError("Masukan email yang valid");
          valid = false;
        }
        if(binding.etPass2.getText().length()<8){
          binding.ilPass2.setErrorEnabled(true);
          binding.ilPass2.setError("Masukan email yang valid");
          valid = false;
        }
        if(!binding.etPass1.getText().toString().equals(binding.etPass2.getText().toString())){
          binding.ilPass2.setErrorEnabled(true);
          binding.ilPass1.setErrorEnabled(true);
          binding.ilPass1.setError("Masukan password yang sama");
          valid = false;
        }
        if(binding.etTelepon.getText().toString().length() <= 10 || binding.etTelepon.getText().length() > 13){
          binding.ilTelepon.setErrorEnabled(true);
          binding.ilTelepon.setError("Masukan nomor telepon yang valid");
          valid = false;
        }
        if(!binding.etTelepon.getText().toString().startsWith("0")){
          binding.ilTelepon.setErrorEnabled(true);
          binding.ilTelepon.setError("Masukan nomor hp, harus diawali dengan nol");
            valid = false;
        }
        if(binding.etTelepon.getText().length() != 0){
            String tel;
            tel = binding.etTelepon.getText().toString().substring(0, 2);
            if(!tel.equals("08")){
              binding.ilTelepon.setErrorEnabled(true);
              binding.ilTelepon.setError("Masukan nomor hp, harus diawali dengan nol");
                valid = false;
            }
        }
        return valid;
    }

}