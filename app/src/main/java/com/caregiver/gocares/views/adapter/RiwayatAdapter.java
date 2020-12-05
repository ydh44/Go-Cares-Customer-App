package com.caregiver.gocares.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.caregiver.gocares.R;
import com.caregiver.gocares.models.Pesanan;
import com.caregiver.gocares.utils.Card;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.views.activity.TransaksiActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.ViewHolder> {

	public LayoutInflater layoutInflater;
	public List<Pesanan> Data;
	public Context contexts;
	public ViewGroup viewGroup;


	public RiwayatAdapter(Context context, List<Pesanan> cg, ViewGroup viewGroup) {
		contexts = context;
		this.Data = new ArrayList<>();
		this.layoutInflater = LayoutInflater.from(context);
		this.Data = cg;
		this.viewGroup = viewGroup;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = layoutInflater.inflate(R.layout.card, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		Pesanan pesanan = Data.get(i);
		ViewHolder viewHolder1 = viewHolder;

		Picasso.get().load(pesanan.getPhoto()).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(viewHolder1.foto);
		viewHolder1.cg_nama.setText(pesanan.getEsccort_name());
		viewHolder1.pesanan_status.setText(pesanan.getStatus());
		viewHolder1.tanggal.setText(pesanan.getOrder_time());
		viewHolder1.durasi.setText(pesanan.getDurasi());
		viewHolder1.telepon.setText(pesanan.getNomor_telp());
		viewHolder1.alamat.setText(pesanan.getAddress());
		viewHolder1.aktivitas.setText(pesanan.getDeskripsi_kerja());
		viewHolder1.total.setText(pesanan.getTotal_bayar());
		viewHolder1.lansia_nama.setText(pesanan.getLansia_name());
		viewHolder1.lansia_umur.setText(pesanan.getUmur());
		viewHolder1.lansia_gender.setText(pesanan.getLansia_gender());
		viewHolder1.lansia_hobi.setText(pesanan.getHobi());
		viewHolder1.lansia_riwayat.setText(pesanan.getRiwayat());

		if(SessionLog.GetDalamPesanan(contexts)){
			viewHolder1.action.setVisibility(View.GONE);
		}else {
			viewHolder1.action.setOnClickListener(n -> {
				Intent intent = new Intent(contexts, TransaksiActivity.class);
				intent.putExtra("id_cg", pesanan.getEsccort_id());
				contexts.startActivity(intent);
			});
		}
	}

	@Override
	public int getItemCount() {
		return Data.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView foto;
		TextView cg_nama, pesanan_status, tanggal, durasi, telepon, alamat, aktivitas, total, lansia_nama, lansia_umur, lansia_gender, lansia_hobi, lansia_riwayat;
		ConstraintLayout action;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			TextView actionTv = itemView.findViewById(R.id.actionTv);
			action = itemView.findViewById(R.id.actionBtn);

			ConstraintLayout expandableView_detail_pesanans = itemView.findViewById(R.id.expandableView_constraint_pesanan);
			ConstraintLayout expandableView_detail_lansias = itemView.findViewById(R.id.expandableView_constraint_lansia);
			ConstraintLayout expandableView_detail_ulasans = itemView.findViewById(R.id.expandableView_constraint_ulasan);
			LinearLayout expandableView_Cardviews = itemView.findViewById(R.id.expandableView);
			ConstraintLayout detail_pesananHeads = itemView.findViewById(R.id.detailpesananHead);
			ConstraintLayout detail_lansiaHeads = itemView.findViewById(R.id.detaillansiaHead);
			ConstraintLayout detail_ulasanHeads = itemView.findViewById(R.id.detailulasanHead);
			ConstraintLayout detailBtns = itemView.findViewById(R.id.detailBtn);
			TextView detailTvs = itemView.findViewById(R.id.detailTv);
			ImageView detail_pesananBtns = itemView.findViewById(R.id.detailpesananBtn);
			ImageView detail_lansiaBtns = itemView.findViewById(R.id.detaillansiaBtn);
			ImageView detail_ulasanBtns = itemView.findViewById(R.id.ulasanBtn);
			new Card(
							expandableView_detail_pesanans, expandableView_detail_lansias, expandableView_detail_ulasans,
							expandableView_Cardviews, detail_pesananHeads, detail_lansiaHeads, detail_ulasanHeads,
							detailBtns, detailTvs, detail_pesananBtns, detail_lansiaBtns, detail_ulasanBtns, viewGroup
			);

			ConstraintLayout keahlian = itemView.findViewById(R.id.keahlian);
			ConstraintLayout rating = itemView.findViewById(R.id.rating);
			Button batalBtn = itemView.findViewById(R.id.batalBtn);

			keahlian.setVisibility(View.GONE);
			rating.setVisibility(View.GONE);
			batalBtn.setVisibility(View.GONE);
			actionTv.setText("Pesan Lagi");

			foto = itemView.findViewById(R.id.fotocg);
			cg_nama = itemView.findViewById(R.id.namacgTv);
			pesanan_status = itemView.findViewById(R.id.status1Tv);
			tanggal = itemView.findViewById(R.id.tanggal1Tv);
			durasi = itemView.findViewById(R.id.durasi1Tv);
			telepon = itemView.findViewById(R.id.telepon1Tv);
			alamat = itemView.findViewById(R.id.alamat1Tv);
			aktivitas = itemView.findViewById(R.id.aktivitas1Tv);
			total = itemView.findViewById(R.id.total1Tv);
			lansia_nama = itemView.findViewById(R.id.nama1Tv);
			lansia_umur = itemView.findViewById(R.id.umur1Tv);
			lansia_gender = itemView.findViewById(R.id.gender1Tv);
			lansia_hobi = itemView.findViewById(R.id.hobi1Tv);
			lansia_riwayat = itemView.findViewById(R.id.riwayat1Tv);
		}

	}

}
