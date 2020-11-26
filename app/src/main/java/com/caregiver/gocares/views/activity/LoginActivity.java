package com.caregiver.gocares.views.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.Login2Binding;
import com.caregiver.gocares.utils.CekForm;
import com.caregiver.gocares.utils.GetLocation;
import com.caregiver.gocares.utils.LoadingDialog;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.viewmodels.LoginViewModel;
import com.caregiver.gocares.viewmodels.MainViewModel;
import com.caregiver.gocares.views.fragment.TransFragment;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity {
  Login2Binding binding;
  LoginViewModel viewModel;
  LoadingDialog loadingDialog = new LoadingDialog(this);
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new LoginViewModel(this);
    binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.login2, null, false);
    setContentView(this.binding.getRoot());
    binding.setLoginModel(viewModel);


    busy();

     KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
       @Override
       public void onVisibilityChanged(boolean b) {
        if(b){
          binding.grafis.setVisibility(View.GONE);
        }else {
          binding.grafis.setVisibility(View.VISIBLE);
           ViewAnimator
                   .animate(binding.grafis)
                   .alpha(0f, 1f)
                   .duration(500)
                   .start();
        }
       }
     });

    binding.toolbar.setOnMenuItemClickListener(item -> {
      if (item.getItemId() == R.id.back) {
        finish();
        UIUtil.hideKeyboard(this);
      }
      return false;
    });

      Intent i = getIntent();
      boolean status = i.getBooleanExtra("status", false);
      if (status) {
        Snackbar snackbar = Snackbar
                .make(binding.cons, "Berhasil Daftar, Silahkan Masuk", BaseTransientBottomBar.LENGTH_LONG)
                .setDuration(10000);
        snackbar.show();
      }

      new CekForm(this, binding.etEmail, binding.ilEmail, true);
      new CekForm(this, binding.etPass, binding.ilPass, true);
  }

  public void failure(String error){
    switch (error) {
      case "email":
        binding.ilEmail.setErrorEnabled(true);
        binding.ilEmail.setError("Masukan email yang valid");
        binding.ilEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
        break;
      case "password":
        binding.ilPass.setErrorEnabled(true);
        binding.ilPass.setError("Masukan password Anda");
        binding.ilPass.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
        break;
      case "wrong":
        binding.ilEmail.setErrorEnabled(true);
        binding.ilEmail.setError("Email / Password Salah");
        binding.ilPass.setErrorEnabled(true);
        binding.ilPass.setError("Email / Password Salah");
        binding.ilEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
        binding.ilPass.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
        break;
      case "conn":
        Snackbar snackbar = Snackbar
                .make(binding.cons, "Koneksi Gagal", BaseTransientBottomBar.LENGTH_LONG)
                .setDuration(5000);
        snackbar.show();
        break;
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

  public void connection(Boolean b){
    if(b) {
      binding.getRoot().findViewById(R.id.connectionError).setVisibility(View.GONE);
      binding.buttonlog.setVisibility(View.VISIBLE);
    }else {
      binding.getRoot().findViewById(R.id.connectionError).setVisibility(View.VISIBLE);
      binding.buttonlog.setVisibility(View.GONE);
    }
  }

  public void in(String token){
    TransFragment transFragment = new TransFragment();
    Bundle bundle = new Bundle();
    bundle.putString("bearer", token);
    transFragment.setArguments(bundle);
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.frameLayout, transFragment);
    binding.cons.setVisibility(View.GONE);
    fragmentTransaction.commit();  

  }
}