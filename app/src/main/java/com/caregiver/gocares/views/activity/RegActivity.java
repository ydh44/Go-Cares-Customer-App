package com.caregiver.gocares.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.bumptech.glide.load.model.ModelLoader;
import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.RegBinding;
import com.caregiver.gocares.utils.CekForm;
import com.caregiver.gocares.utils.LoadingDialog;
import com.caregiver.gocares.viewmodels.RegViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;


public class RegActivity extends AppCompatActivity {

    RegBinding binding;
    Snackbar snackbar;
    RegViewModel viewModel;
    LoadingDialog loadingDialog = new LoadingDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel =  new RegViewModel(this);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.reg, null, false);
        setContentView(binding.getRoot());
        binding.setRegModel(viewModel);

        busy();

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


      new CekForm(this , binding.etEmail, binding.ilEmail, false);
      new CekForm(this, binding.etNama, binding.ilNama, false);
      new CekForm(this , binding.etUmur, binding.ilUmur, false);
      new CekForm(this, binding.etGender, binding.ilGender, false);
      new CekForm(this , binding.etTelepon, binding.ilTelepon, false);
      new CekForm(this, binding.etAlamat, binding.ilAlamat, false);
      new CekForm(this , binding.etPass1, binding.ilPass1, false);
      new CekForm(this, binding.etPass2, binding.ilPass2, false);
    }

  public void connection(Boolean b){
    if(b) {
      binding.connectionError.getRoot().setVisibility(View.GONE);
      binding.buttonreg.setVisibility(View.VISIBLE);
    }else {
      binding.connectionError.getRoot().setVisibility(View.VISIBLE);
      binding.buttonreg.setVisibility(View.GONE);
    }
  }

  public void busy(){
    viewModel.busy.observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean aBoolean) {
        if(aBoolean){
          loadingDialog.startLoading();
        }else {
          loadingDialog.dismissLoading();
        }
      }
    });
  }

    public void failure(String error)
    {
        switch (error){
          case "email":
            binding.ilEmail.setErrorEnabled(true);
            binding.ilEmail.setError("Masukan email yang valid");
            break;
          case  "nama":
            binding.ilNama.setErrorEnabled(true);
            binding.ilNama.setError("Masukan nama Anda");
            break;
          case "umur":
            binding.ilUmur.setErrorEnabled(true);
            binding.ilUmur.setError("Masukan umur Anda");
            break;
          case "gender" :
            binding.ilGender.setErrorEnabled(true);
            binding.ilGender.setError("Masukan gender");
            break;
          case "telepon":
            binding.ilTelepon.setErrorEnabled(true);
            binding.ilTelepon.setError("Masukan telepon yang valid, diawali 0 ");
            break;
          case  "alamat":
            binding.ilAlamat.setErrorEnabled(true);
            binding.ilAlamat.setError("Masukan alamat Anda");
            break;
          case "password1":
            binding.ilPass1.setErrorEnabled(true);
            binding.ilPass1.setError("Masukan email yang valid");
            break;
          case "password2":
            binding.ilPass2.setErrorEnabled(true);
            binding.ilPass2.setError("Masukan email yang valid");
            break;
          case "password3":
            binding.ilPass1.setErrorEnabled(true);
            binding.ilPass2.setErrorEnabled(true);
            binding.ilPass1.setError("Password Tidak Sama");
            binding.ilPass2.setError("Password Tidak Sama");
            break;
          case "wrong":
            binding.ilEmail.setErrorEnabled(true);
            binding.ilEmail.setError("Email Salah");
             snackbar = Snackbar
                    .make(binding.cons, "Email Telah Terdaftar Sebelumnya", BaseTransientBottomBar.LENGTH_LONG)
                    .setDuration(5000);
            snackbar.show();
            break;
          case "conn":
            snackbar= Snackbar
                    .make(binding.cons, "Koneksi Gagal", BaseTransientBottomBar.LENGTH_LONG)
                    .setDuration(5000);
            snackbar.show();
            break;
        }
    }

    public void reg(){
      Intent i = new Intent(this, LoginActivity.class);
      i.putExtra("status", true);
      startActivity(i);
      finish();
    }

}