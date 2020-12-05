package com.caregiver.gocares.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.caregiver.gocares.models.Checkout;
import com.caregiver.gocares.models.Lansia;
import com.caregiver.gocares.models.LansiaResponse;
import com.caregiver.gocares.models.Pesanan;
import com.caregiver.gocares.models.RegUser;
import com.caregiver.gocares.repositories.TransaksiRepository;
import com.caregiver.gocares.utils.CekConnection;
import com.caregiver.gocares.utils.ParseData;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.views.activity.PelajariActivity;
import com.caregiver.gocares.views.activity.TransaksiActivity;
import com.caregiver.gocares.views.fragment.CheckoutFragment;
import com.caregiver.gocares.webservices.ApiServices;
import com.caregiver.gocares.webservices.RetrofitServices;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CheckoutViewModel extends ViewModel {
	public MutableLiveData<String> nama = new MutableLiveData<>();
	public MutableLiveData<String> umur = new MutableLiveData<>();
	public MutableLiveData<String> gender = new MutableLiveData<>();
	public MutableLiveData<String> hobi = new MutableLiveData<>();
	public MutableLiveData<String> riwayat = new MutableLiveData<>();
	public MutableLiveData<String> paket = new MutableLiveData<>();
	public MutableLiveData<String> durasi = new MutableLiveData<>();
	public MutableLiveData<String> aktivitas = new MutableLiveData<>();
	public MutableLiveData<String> alamat = new MutableLiveData<>();
	public MutableLiveData<String> telepon = new MutableLiveData<>();
	public MutableLiveData<Boolean> dataLansia = new MutableLiveData<>();
	public MutableLiveData<LansiaResponse> lansiaCust = new MutableLiveData<>();
	public MutableLiveData<String> pesananId = new MutableLiveData<>();
	public MutableLiveData<String> jumlahBayar = new MutableLiveData<>();
	public MutableLiveData<Integer> busy = new MutableLiveData<>();
	public String lansiaId;
	public Checkout datapesanan;
	ParseData parseData;
	CheckoutFragment view;
	Context context;
	TransaksiRepository transaksiRepository;



	public CheckoutViewModel(CheckoutFragment checkoutFragment){
		this.view = checkoutFragment;
		context = view.getContext();
		parseData = new ParseData();
		transaksiRepository = new TransaksiRepository();
		ParseData parseData = new ParseData();

		loadLansia();
	}



	public void loadLansia(){
		busy.postValue(3);
		ApiServices apiServices = RetrofitServices.Companion.getRetrofitServices().create(ApiServices.class);
		apiServices.getLansia(SessionLog.GetId(context)).enqueue(new Callback<LansiaResponse>() {
			@Override
			public void onResponse(@NonNull Call<LansiaResponse> call, @NonNull Response<LansiaResponse> response) {
				busy.postValue(0);
				if(response.body() != null){
					lansiaCust.postValue(response.body());
					lansiaId = String.valueOf(response.body().getLansia().getId());
				}
			}

			@Override
			public void onFailure(@NonNull Call<LansiaResponse> call, @NonNull Throwable t) {
				busy.postValue(0);
			}
		});
	}

	public void onChangeListener(){
		nama.observe(view, s -> {
			if(s != null){
				dataLansia.postValue(s.equals(Objects.requireNonNull(lansiaCust.getValue()).getLansia().getNama()));
			}
		});
		umur.observe(view, s -> {
			if(s != null){
				dataLansia.postValue(s.equals(Objects.requireNonNull(lansiaCust.getValue()).getLansia().getUmur()));
			}

		});
		gender.observe(view, s -> {
			if(s != null){
				dataLansia.postValue(parseData.parseBackGender(s).equals(Objects.requireNonNull(lansiaCust.getValue()).getLansia().getGender()));
			}

		});
		hobi.observe(view, s -> {
			if(s != null){
				dataLansia.postValue(s.equals(Objects.requireNonNull(lansiaCust.getValue()).getLansia().getHobi()));
			}
		});
		riwayat.observe(view, s -> {
			if(s != null) {
				dataLansia.postValue(s.equals(Objects.requireNonNull(lansiaCust.getValue()).getLansia().getRiwayat()));
			}
			});
	}

	public void onOutClick(){
		if(validation()){
			UIUtil.hideKeyboard(view.requireActivity());
			busy.postValue(2);
			if(lansiaId != null){
				if(view.getCheckState()){
					datapesanan = new Checkout(
									"",
									"",
									"",
									"",
									"",
									Objects.requireNonNull(paket.getValue()).toLowerCase(),
									parseData.parseBackDurasi(durasi.getValue(), paket.getValue().toLowerCase()),
									aktivitas.getValue(),
									alamat.getValue(),
									telepon.getValue(),
									SessionLog.GetId(view.getActivity()),
									view.idCg,
									lansiaId,
									"lama"
					);

				}else {
					datapesanan = new Checkout(
									nama.getValue(),
									umur.getValue(),
									parseData.parseBackGender(gender.getValue()),
									hobi.getValue(),
									riwayat.getValue(),
									Objects.requireNonNull(paket.getValue()).toLowerCase(),
									parseData.parseBackDurasi(durasi.getValue(), paket.getValue().toLowerCase()),
									aktivitas.getValue(),
									alamat.getValue(),
									telepon.getValue(),
									SessionLog.GetId(view.getActivity()),
									view.idCg,
									lansiaId,
									"baru"
					);
				}
			}

			else {
				datapesanan = new Checkout(
								nama.getValue(),
								umur.getValue(),
								parseData.parseBackGender(gender.getValue()),
								hobi.getValue(),
								riwayat.getValue(),
								Objects.requireNonNull(paket.getValue()).toLowerCase(),
								parseData.parseBackDurasi(durasi.getValue(), paket.getValue().toLowerCase()),
								aktivitas.getValue(),
								alamat.getValue(),
								telepon.getValue(),
								SessionLog.GetId(view.getActivity()),
								view.idCg,
								"",
								"baru"
				);
			}

			transaksiRepository.pesan(datapesanan).observe(view, pesanan -> {
				if(pesanan != null){
					busy.postValue(1);
					SessionLog.SaveHomeRefresh(view.getActivity(), true);
					pesananId.postValue(String.valueOf(Objects.requireNonNull(pesanan.getPesan2()).getId()));
					jumlahBayar.postValue(parseData.parseUang(String.valueOf(pesanan.getPesan2().getTotalBayar())));
				}
			});

		}
	}

	public Boolean validation() {
		boolean valid = true;

		if(nama.getValue() == null){
			view.failure("nama");
			valid = false;
		}

		if(umur.getValue()  == null){
			view.failure("umur");
			valid = false;
		}

		if(gender.getValue()  == null){
			view.failure("gender");
			valid = false;
		}

		if(hobi.getValue() == null){
			view.failure("hobi");
			valid = false;
		}

		if(riwayat.getValue() == null){
			view.failure("riwayat");
			valid = false;
		}

		if(paket.getValue() == null){
			view.failure("paket");
			valid = false;
		}

		if(durasi.getValue() == null){
			view.failure("durasi");
			valid = false;
		}

		if(aktivitas.getValue() == null){
			view.failure("aktivitas");
			valid = false;
		}

		if(alamat.getValue() == null){
			view.failure("alamat");
			valid = false;
		}

		if(telepon.getValue()  == null || !Patterns.PHONE.matcher(telepon.getValue()).matches()){
			view.failure("telepon");
			valid = false;
		}else if(telepon.getValue().length()<= 10 || telepon.getValue().length() > 13 || !telepon.getValue().startsWith("0")){
			view.failure("telepon");
			valid = false;
		}


		return valid;
	}

	public void infoPaket(){
		Intent i = new Intent(view.getActivity(), PelajariActivity.class);
		i.putExtra("layout", "frag2");
		view.requireActivity().startActivity(i);
	}
}