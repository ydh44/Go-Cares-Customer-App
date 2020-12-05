package com.caregiver.gocares.utils;

import android.util.Log;

import com.caregiver.gocares.models.Cg;
import com.google.gson.internal.$Gson$Preconditions;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

public class ParseData {
	public String parseStatus(String status){
		String statuses;
		switch (status){
			case "belum" :
				statuses = "Belum Bayar";
				break;
			case "menunggu" :
				statuses = "Menunggu Konfirmasi";
				break;
			case "dikonfirmasi" :
				statuses = "Telah Dikonfirmasi";
				break;
			case "merawat" :
				statuses = "Sedang Merawat";
				break;
			case "diterima" :
				statuses = "Pesanan Berhasil";
				break;
			case "ditolak" :
				statuses = "Pesanan Gagal";
				break;

			default:
				throw new IllegalStateException("Unexpected value: " + status);
		}
		return statuses;
	}

	public String parseUang(String jumlah){
		Locale localeid = new Locale("in", "ID");
		NumberFormat format = NumberFormat.getCurrencyInstance(localeid);
		Double jumlahs = Double.parseDouble(jumlah);
		return String.valueOf(format.format(jumlahs));
	}

	public String parseTanggal(String tanggal) throws ParseException {
		Locale localeid = new Locale("in", "ID");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", localeid);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = df.parse(tanggal);
		df.setTimeZone(TimeZone.getDefault());
		assert date != null;
		String formattedDate = df.format(date);
		return formattedDate;
	}

	public String parseDurasi(String durasi, String paket){
		String duras;
		switch (paket){
			case "harian" :
				duras = durasi + " Hari";
				break;
			case "bulanan" :
				duras = durasi + " Bulan";
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + paket);
		}
		return duras;
	}

	public String parseBackDurasi(String durasi, String paket){
		String duras;
		switch (paket){
			case "harian" :
				String harian = " Hari";
				duras = durasi.replaceAll(harian, "");
				break;
			case "bulanan" :
				String bulanan = " Bulan";
				duras = durasi.replaceAll(bulanan, "");
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + paket);
		}
		Log.d(TAG, "parseBackDurasi: " + duras);
		return duras;
	}

	public String parseGender(String gender){
		String genders;
		switch (gender){
			case "L" :
				genders = "Laki - laki";
				break;
			case "P" :
				genders = "Perempuan";
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + gender);
		}
		return genders;
	}

	public String parseBackGender(String gender){
		String genders = null;
		if(gender != null){
			switch (gender){
				case "Laki - laki" :
					genders = "L";
					break;
				case "Perempuan" :
					genders = "P";
					break;
				default:
					genders = "null";
			}
		}
		return genders;
	}

	public String parseRating(String rating){
		String ratings;
		Log.d("TAG", "parseRating: " + rating);
		if(rating == null){
			ratings = "0.0";
		}else {
			ratings = rating;
		}
		return ratings + " / 5.0";
	}

	public String parseUmur(String umur){
		return umur + " Tahun";
	}

	public List<Cg> parseCgByCity(List<Cg> cg, String kota){
		List<Cg> cgs = new ArrayList<>();
		for(int i = 0 ; i < cg.size(); i ++ ){
			if(cg.get(i).getAddress().equals(kota)){
				cgs.add(cg.get(i));
			}
		}
		return cgs;
	}

	public List<Cg> parseCgByGender(List<Cg> cg, String gender){
		List<Cg> cgs = new ArrayList<>();
		for(int i = 0 ; i < cg.size(); i ++ ){
			if(cg.get(i).getGender().equals(gender)){
				cgs.add(cg.get(i));
			}
		}
		return cgs;
	}
}
