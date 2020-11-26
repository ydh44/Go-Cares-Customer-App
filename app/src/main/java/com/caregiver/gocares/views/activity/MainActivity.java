package com.caregiver.gocares.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;


import android.os.Bundle;

import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.HomeBinding;
import com.caregiver.gocares.utils.DisposableManager;
import com.caregiver.gocares.utils.GetLocation;
import com.caregiver.gocares.viewmodels.MainViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
  HomeBinding binding;
  MainViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = new MainViewModel(this);
    binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.home, null, false);
    setContentView(this.binding.getRoot());
    binding.setMainModel(viewModel);


    GetLocation loc = new GetLocation(this);
    loc.kota.observe(this, new Observer<String>() {
      @Override
      public void onChanged(String s) {
        if(s != null){
          Snackbar snackbar = Snackbar
                  .make(binding.cons, "lokasi : " + s, BaseTransientBottomBar.LENGTH_LONG)
                  .setDuration(10000);
          snackbar.show();
        }
      }
    });

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

  }



}