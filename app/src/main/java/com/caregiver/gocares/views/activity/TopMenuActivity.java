package com.caregiver.gocares.views.activity;

import android.content.Intent;
import android.os.Bundle;

import com.caregiver.gocares.databinding.TopmenuBinding;
import com.caregiver.gocares.views.fragment.ProfileFragment;
import com.caregiver.gocares.views.fragment.RiwayatFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.ViewBindingAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import com.caregiver.gocares.R;

import java.util.Objects;
import java.util.zip.Inflater;

public class TopMenuActivity extends AppCompatActivity {
	ProfileFragment profileFragment;
	RiwayatFragment riwayatFragment;
	TopmenuBinding binding;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = TopmenuBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		Toolbar toolbar = binding.toolbar;
		setSupportActionBar(toolbar);
		binding.toolbar.setNavigationOnClickListener(v -> {
			finish();
		});

		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

		profileFragment = new ProfileFragment();
		riwayatFragment = new RiwayatFragment();

		Intent i = getIntent();
		String layout = i.getStringExtra("layout");
		if(layout.equals("profile")){
			getSupportActionBar().setTitle("Profil Saya");
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace(binding.frame.getId(), profileFragment);
			fragmentTransaction.commit();
		}else if(layout.equals("riwayat")){
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("riwayat",i.getParcelableArrayListExtra("riwayat"));
			riwayatFragment.setArguments(bundle);
			getSupportActionBar().setTitle("Riwayat Pesanan");
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace(binding.frame.getId(), riwayatFragment);
			fragmentTransaction.commit();
		}
	}
}