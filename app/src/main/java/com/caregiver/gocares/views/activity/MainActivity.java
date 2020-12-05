package com.caregiver.gocares.views.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.HomeBinding;
import com.caregiver.gocares.generated.callback.OnClickListener;
import com.caregiver.gocares.models.Cg;
import com.caregiver.gocares.models.Pesanan;
import com.caregiver.gocares.utils.Card;
import com.caregiver.gocares.utils.GetLocation;
import com.caregiver.gocares.utils.LoadingDialog;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.viewmodels.MainViewModel;
import com.caregiver.gocares.views.adapter.CgAdapter;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

  HomeBinding binding;
  MainViewModel viewModel;
  LoadingDialog loadingDialog;
  AutoTransition transition;
  AppBarLayout appBarLayout;
  Handler handler;
  Runnable runnable;
  private BottomSheetBehavior sheetBehavior;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = new MainViewModel(this);
    binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.home, null, false);
    setContentView(this.binding.getRoot());
    binding.setMainModel(viewModel);

    transition = new AutoTransition();
    loadingDialog = new LoadingDialog(this);
    handler = new Handler();

    binding.cardTop.setVisibility(View.GONE);
    binding.layanan.setVisibility(View.GONE);
    binding.berlangsung.setVisibility(View.GONE);
    binding.pelajari.setVisibility(View.GONE);

    cekConn();
    busy();
    jumlahCg();

    transition.setDuration(150);

    appBarLayout = binding.appbar;
    Toolbar toolbar = binding.toolbar;
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
    appBarLayout.setLayoutParams(params);

    sheetBehavior = BottomSheetBehavior.from(binding.bottomsheet.bottomsheet);
    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    sheetBehavior.setSkipCollapsed(true);
    binding.bottomsheet.scroll.setNestedScrollingEnabled(false);
    binding.layananBtn.setOnClickListener(view -> {
      appBarLayout.setExpanded(false, true);
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    });

    sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        switch (newState) {
          case BottomSheetBehavior.STATE_HIDDEN:
            appBarLayout.setExpanded(true, true);
            handler.removeCallbacks(runnable);
            break;
          case BottomSheetBehavior.STATE_COLLAPSED:
          case BottomSheetBehavior.STATE_EXPANDED:
            runnable = new Runnable() {
              @Override
              public void run() {
                appBarLayout.setExpanded(false, false);
                Log.d(TAG, "run: " );
                handler.postDelayed(this, 10);
              }
            };handler.postDelayed(runnable, 10);
            break;
          case BottomSheetBehavior.STATE_DRAGGING:
            handler.removeCallbacks(runnable);
            break;
          case BottomSheetBehavior.STATE_HALF_EXPANDED:
          case BottomSheetBehavior.STATE_SETTLING:
            break;
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    });

  }

  private void cekConn() {
    viewModel.internet.observe(this, aBoolean -> {
      if(!aBoolean){
        binding.cardTop.setVisibility(View.GONE);
        binding.layanan.setVisibility(View.GONE);
        binding.berlangsung.setVisibility(View.GONE);
        binding.pelajari.setVisibility(View.GONE);
        binding.errorconn.setVisibility(View.VISIBLE);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
      }
    });
  }

  public void jumlahCg(){
    viewModel.jumlahCg.observe(this, s -> {
      if(s != null){
        viewModel.jumlahCg.removeObservers(this);
        binding.jumlahcg.setText(s);
        TransitionManager.beginDelayedTransition(binding.cons, transition);
        binding.jumlahcg.setVisibility(View.VISIBLE);
      }
    });
  }

  public void busy(){

    final SkeletonScreen[] skeleton = new SkeletonScreen[1];
      viewModel.busy.observe(this, integer -> {
        if(integer == 1){
          loadingDialog.dismissLoading();
        }else if(integer == 2){
          loadingDialog.startLoading();
        }else if(integer == 3){
           skeleton[0] = Skeleton.bind(binding.cons)
                  .load(R.layout.home_load)
                  .show();
        }else if(integer == 0){
          skeleton[0].hide();
        }
      });
  }

  public void available(Boolean condition){
    if(!condition){
      binding.cardTop.setVisibility(View.VISIBLE);
      binding.berlangsung.setVisibility(View.VISIBLE);
      binding.pelajari.setVisibility(View.VISIBLE);
      Pesanan pesanan = viewModel.pesananBerlangsung;
      binding.layanan.setVisibility(View.GONE);

      TextView actionTv = findViewById(R.id.actionTv);
      ConstraintLayout actionBtn = findViewById(R.id.actionBtn);

      ConstraintLayout expandableView_detail_pesanans = findViewById(R.id.expandableView_constraint_pesanan);
      ConstraintLayout expandableView_detail_lansias = findViewById(R.id.expandableView_constraint_lansia);
      ConstraintLayout expandableView_detail_ulasans = findViewById(R.id.expandableView_constraint_ulasan);
      LinearLayout expandableView_Cardviews = findViewById(R.id.expandableView);
      ConstraintLayout detail_pesananHeads = findViewById(R.id.detailpesananHead);
      ConstraintLayout detail_lansiaHeads = findViewById(R.id.detaillansiaHead);
      ConstraintLayout detail_ulasanHeads = findViewById(R.id.detailulasanHead);
      ConstraintLayout detailBtns = findViewById(R.id.detailBtn);
      TextView detailTvs = findViewById(R.id.detailTv);
      ImageView detail_pesananBtns = findViewById(R.id.detailpesananBtn);
      ImageView detail_lansiaBtns = findViewById(R.id.detaillansiaBtn);
      ImageView detail_ulasanBtns = findViewById(R.id.ulasanBtn);
      new Card(
              expandableView_detail_pesanans, expandableView_detail_lansias, expandableView_detail_ulasans,
              expandableView_Cardviews, detail_pesananHeads, detail_lansiaHeads, detail_ulasanHeads,
              detailBtns, detailTvs, detail_pesananBtns, detail_lansiaBtns, detail_ulasanBtns, binding.cons
      );

      View ulasanGaris = findViewById(R.id.view4);
      ConstraintLayout keahlian = findViewById(R.id.keahlian);
      ConstraintLayout rating = findViewById(R.id.rating);
      Button batalBtn = findViewById(R.id.batalBtn);

      detail_ulasanHeads.setVisibility(View.GONE);
      ulasanGaris.setVisibility(View.GONE);
      keahlian.setVisibility(View.GONE);
      rating.setVisibility(View.GONE);

      ImageView foto = findViewById(R.id.fotocg);
      TextView cg_nama = findViewById(R.id.namacgTv);
      TextView pesanan_status = findViewById(R.id.status1Tv);
      TextView tanggal = findViewById(R.id.tanggal1Tv);
      TextView durasi = findViewById(R.id.durasi1Tv);
      TextView telepon = findViewById(R.id.telepon1Tv);
      TextView alamat = findViewById(R.id.alamat1Tv);
      TextView aktivitas = findViewById(R.id.aktivitas1Tv);
      TextView total = findViewById(R.id.total1Tv);
      TextView lansia_nama = findViewById(R.id.nama1Tv);
      TextView lansia_umur = findViewById(R.id.umur1Tv);
      TextView lansia_gender = findViewById(R.id.gender1Tv);
      TextView lansia_hobi = findViewById(R.id.hobi1Tv);
      TextView lansia_riwayat = findViewById(R.id.riwayat1Tv);


      assert pesanan != null;
      Picasso.get().load(pesanan.getPhoto()).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(foto);
      cg_nama.setText(pesanan.getEsccort_name());
      pesanan_status.setText(pesanan.getStatus());
      tanggal.setText(pesanan.getOrder_time());
      durasi.setText(pesanan.getDurasi());
      telepon.setText(pesanan.getNomor_telp());
      alamat.setText(pesanan.getAlamat());
      aktivitas.setText(pesanan.getDeskripsi_kerja());
      total.setText(pesanan.getTotal_bayar());
      lansia_nama.setText(pesanan.getLansia_name());
      lansia_umur.setText(pesanan.getUmur());
      lansia_gender.setText(pesanan.getLansia_gender());
      lansia_hobi.setText(pesanan.getHobi());
      lansia_riwayat.setText(pesanan.getRiwayat());


      if(Objects.equals(pesanan.getStatus(), "Belum Bayar")){
          actionTv.setText("Bayar Sekarang");
          actionBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, TransaksiActivity.class);
            i.putExtra("id_cg", pesanan.getEsccort_id());
            i.putExtra("id_pesanan", pesanan.getId());
            i.putExtra("harga", pesanan.getTotal_bayar());
            startActivity(i);
          });
          batalBtn.setVisibility(View.VISIBLE);
          batalBtn.setOnClickListener(view -> new AlertDialog.Builder(MainActivity.this)
                  .setTitle("Anda yakin ingin membatalkan pesanan ini ?")
                  .setCancelable(false)
                  .setPositiveButton("Iya", (dialogInterface, i) -> viewModel.batalPesanan(pesanan.getId())

                  ).setNegativeButton("Batal", null)
                  .show());
      }else {
        actionTv.setVisibility(View.GONE);
        actionBtn.setVisibility(View.GONE);
      }
    }else {
      binding.cardTop.setVisibility(View.VISIBLE);
      binding.layanan.setVisibility(View.VISIBLE);
      binding.pelajari.setVisibility(View.VISIBLE);
      dataCg();
    }
  }

  public void dataCg(){
    binding.bottomsheet.etKota.setText("Semua", false);
    binding.bottomsheet.etGender.setText("Semua", false);

    viewModel.kotaCust.observe(this, s -> {
      if(s != null){
        viewModel.kotaCust.removeObservers(this);
        binding.bottomsheet.etKota.setText(s, false);
      }
    });

    viewModel.caregiverTampil.observe(this, cgs -> {
      if(cgs != null){
        Collections.shuffle(cgs);
        CgAdapter cgAdapter = new CgAdapter(MainActivity.this, cgs, binding.bottomsheet.scroll);
        binding.bottomsheet.recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.bottomsheet.recyclerview.setAdapter(cgAdapter);


        List<String> daftarGender = new ArrayList<>();
        daftarGender.add("Semua");
        daftarGender.add("Laki - laki");
        daftarGender.add("Perempuan");
        ArrayAdapter<String> daftarKotaAdap = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.dropdown_item,
                viewModel.kota
                );
        ArrayAdapter<String> daftarGenderAdap = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.dropdown_item,
                daftarGender
        );
        binding.bottomsheet.etKota.setAdapter(daftarKotaAdap);
        binding.bottomsheet.etGender.setAdapter(daftarGenderAdap);
      }
    });
  }


  @Override
  protected void onResume() {
    super.onResume();

    if(SessionLog.GetHomeRefresh(this)){
      SessionLog.SaveHomeRefresh(this, false);
      Intent intent = getIntent();
      finish();
      startActivity(intent);
    }

    if(SessionLog.GetHomeMessage(this)){
      SessionLog.SaveHomeMessage(this, false);
      Snackbar snackbar = Snackbar
              .make(binding.cons, "Care giver sedang dalam pesanan lain. Jika Anda sudah menyelesaikan pesanan, hubungi admin", BaseTransientBottomBar.LENGTH_LONG)
              .setDuration(10000);
      snackbar.show();
    }
  }
}