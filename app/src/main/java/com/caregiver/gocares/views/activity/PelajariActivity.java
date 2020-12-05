package com.caregiver.gocares.views.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.caregiver.gocares.databinding.PelajariBinding;
import com.caregiver.gocares.views.fragment.FragTentang1;
import com.caregiver.gocares.views.fragment.FragTentang2;
import com.caregiver.gocares.views.fragment.FragTentang3;
import com.caregiver.gocares.views.fragment.FragTentang4;

import java.util.Objects;

public class PelajariActivity extends AppCompatActivity {
	FragTentang1 fragTentang1;
	FragTentang2 fragTentang2;
	FragTentang3 fragTentang3;
	FragTentang4 fragTentang4;
	PelajariBinding binding;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = PelajariBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		Toolbar toolbar = binding.toolbar;
		setSupportActionBar(toolbar);

		fragTentang1 = new FragTentang1();
		fragTentang2 = new FragTentang2();
		fragTentang3 = new FragTentang3();
		fragTentang4 = new FragTentang4();

		getSupportActionBar().setTitle("Pelajari");

		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

		binding.toolbar.setNavigationOnClickListener(v -> {
			finish();
		});


		Intent i = getIntent();
		String layout = i.getStringExtra("layout");
		if(layout.equals("frag1")){
			frag1();
		}else if(layout.equals("frag2")){
			frag2();
		}else if(layout.equals("frag3")){
			frag3();
		}else if(layout.equals("frag4")){
			frag4();
		}

		binding.pelajari1Btn.setOnClickListener(v -> {
			frag1();
		});

		binding.pelajari2Btn.setOnClickListener(v -> {
			frag2();
		});

		binding.pelajari3Btn.setOnClickListener(v -> {
			frag3();
		});

		binding.pelajari4Btn.setOnClickListener(v -> {
			frag4();
		});

	}

	public void frag1() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(binding.frame.getId(), fragTentang1);
		fragmentTransaction.commit();
	}

	public void frag2() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(binding.frame.getId(), fragTentang2);
		fragmentTransaction.commit();
	}

	public void frag3() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(binding.frame.getId(), fragTentang3);
		fragmentTransaction.commit();
	}

	public void frag4() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(binding.frame.getId(), fragTentang4);
		fragmentTransaction.commit();
	}
}