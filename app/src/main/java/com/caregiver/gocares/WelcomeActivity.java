package com.caregiver.gocares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.caregiver.gocares.databinding.WelcomeBinding;
import com.github.florent37.viewanimator.ViewAnimator;

public class WelcomeActivity extends AppCompatActivity {
    WelcomeBinding binding;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonlog.setOnClickListener(view1 -> startActivity(i = new Intent(WelcomeActivity.this, LoginActivity.class)));
        binding.buttonreg.setOnClickListener(view1 -> startActivity(i = new Intent(WelcomeActivity.this, RegActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewAnimator
                .animate(binding.grafis)
                .translationX(-200, 0 )
                .alpha(0,1)
                .duration(700)
                .start();
    }
}