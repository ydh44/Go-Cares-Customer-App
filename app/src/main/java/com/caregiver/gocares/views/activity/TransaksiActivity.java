package com.caregiver.gocares.views.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.TransaksisBinding;
import com.caregiver.gocares.viewmodels.TransaksiViewModel;
import com.caregiver.gocares.views.fragment.CheckoutFragment;
import com.caregiver.gocares.views.fragment.PembayaranFragment;

public class TransaksiActivity extends AppCompatActivity {
	public String idCg, idPesanan;
	TransaksisBinding binding;
	TransaksiViewModel viewModel;
	CheckoutFragment checkoutFragment;
	PembayaranFragment pembayaranFragment;
	String harga;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewModel = new TransaksiViewModel(this);
		binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.transaksis, null, false);
		setContentView(this.binding.getRoot());
		binding.setTransaksiModel(viewModel);



		checkoutFragment = new CheckoutFragment();
		pembayaranFragment = new PembayaranFragment();

		binding.collapsing.setTitleEnabled(true);
		binding.collapsing.setExpandedTitleTypeface(Typeface.DEFAULT_BOLD);
		binding.collapsing.setCollapsedTitleTypeface(Typeface.DEFAULT);
		binding.collapsing.setCollapsedTitleTextColor(getResources().getColor(R.color.darkgray));
		binding.collapsing.setExpandedTitleTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));

		setSupportActionBar(binding.toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		binding.toolbar.setNavigationOnClickListener(v -> {
          finish();
      });
		cekConn();


		Intent intent = getIntent();
		idCg = intent.getStringExtra("id_cg");
		idPesanan = intent.getStringExtra("id_pesanan");
		harga = intent.getStringExtra("harga");

		if (idPesanan == null) {
			checkout();
		} else {
			bayar(idPesanan, idCg, harga);
		}
	}

	public void checkout() {
		Bundle bundle = new Bundle();
		bundle.putString("id_cg", idCg);
		binding.collapsing.setTitle("Checkout");
		binding.appbar.setExpanded(true);

		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		checkoutFragment.setArguments(bundle);
		fragmentTransaction.replace(binding.content.getId(), checkoutFragment);
		fragmentTransaction.commit();
	}

	public void bayar(String idPesanan, String idCg, String jumlahBayar) {
		binding.appbar.setExpanded(true);
		Bundle bundle = new Bundle();
		bundle.putString("id_pesanan", idPesanan);
		bundle.putString("id_cg", idCg);
		bundle.putString("jumlah_bayar", jumlahBayar);
		binding.collapsing.setTitle("Pembayaran");
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		pembayaranFragment.setArguments(bundle);
		fragmentTransaction.replace(binding.content.getId(), pembayaranFragment);
		fragmentTransaction.commit();
	}

	private void cekConn() {
		viewModel.conn.observe(this, new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (aBoolean) {
					binding.content.setVisibility(View.VISIBLE);
					binding.errorconn.getRoot().setVisibility(View.GONE);
				} else {
					binding.content.setVisibility(View.GONE);
					binding.errorconn.getRoot().setVisibility(View.VISIBLE);
				}
			}
		});
	}

}