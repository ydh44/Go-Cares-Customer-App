package com.caregiver.gocares.viewmodels;

import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.caregiver.gocares.models.Caregiver;
import com.caregiver.gocares.models.Cg;
import com.caregiver.gocares.models.CgResponse;
import com.caregiver.gocares.models.Pesan;
import com.caregiver.gocares.models.Pesanan;
import com.caregiver.gocares.repositories.GantiStatus;
import com.caregiver.gocares.repositories.GetUser;
import com.caregiver.gocares.repositories.HomeRepository;
import com.caregiver.gocares.utils.CekConnection;
import com.caregiver.gocares.utils.GetLocation;
import com.caregiver.gocares.utils.ParseData;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.utils.ShorterName;
import com.caregiver.gocares.utils.UpToken;
import com.caregiver.gocares.views.activity.MainActivity;
import com.caregiver.gocares.views.activity.PelajariActivity;
import com.caregiver.gocares.views.activity.TopMenuActivity;
import com.caregiver.gocares.views.adapter.CgAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class MainViewModel extends ViewModel {
	final CekConnection cekConnection;
	public ObservableField<String> namaField = new ObservableField<>();
	public MutableLiveData<String> kotaField = new MutableLiveData<>();
	public MutableLiveData<String> genderField = new MutableLiveData<>();
	public MutableLiveData<String> jumlahCg = new MutableLiveData<>();
	public MutableLiveData<Integer> busy = new MutableLiveData<>();
	public MutableLiveData<Boolean> getCg = new MutableLiveData<>();
	public MutableLiveData<Boolean> countCg = new MutableLiveData<>();
	public MutableLiveData<Boolean> internet = new MutableLiveData<>();
	public Boolean nointernet;
	public Pesanan pesananBerlangsung;
	public ArrayList<Pesanan> pesananRiwayat;
	public List<Cg> caregiverTersedia;
	public MutableLiveData<List<Cg>> caregiverTampil = new MutableLiveData<>();
	public List<Integer> caregiverSibuk;
	public List<String> kota;
	public MutableLiveData<String> kotaCust = new MutableLiveData<>();

	MainActivity view;
	HomeRepository homeRepository;
	ParseData parseData;
	GetLocation loc;

	public MainViewModel(MainActivity mainActivity) {
		this.view = mainActivity;
		pesananRiwayat = new ArrayList<>();
		caregiverTersedia = new ArrayList<>();
		caregiverSibuk = new ArrayList<>();
		parseData = new ParseData();
		kota = new ArrayList<>();
		cekConnection = new CekConnection(view, true);
	  kota.add("Semua");
	  nointernet = false;


		String namas = new ShorterName(SessionLog.GetUserName(view)).getShort();
		if (namas != null) {
			namaField.set(namas);
		} else {
			namaField.set("Selamat Datang !");
		}

		checkConn();
		fieldObserver();

	}

	public void checkConn() {
		cekConnection.status.observe(view, aBooleans -> {
			if (aBooleans) {
				internet.postValue(true);
				if(nointernet){
					nointernet = false;
					Intent intent = view.getIntent();
					view.finish();
					view.startActivity(intent);
				}
				busy.postValue(0);
				new GetUser(view, SessionLog.GetToken(view)).user.observe(view, aBoolean -> {
					if (aBoolean) {
						new UpToken(view);
					} else {
						SessionLog.Delete(view);
						view.finish();
					}
				});

				busy.postValue(3);
				GetLoc();
				PesananData();
				CgData();
			}else {
				internet.postValue(false);
				nointernet = true;
			}
		});
	}

	private void GetLoc() {
		loc = new GetLocation(view);
		loc.kota.observe(view, s -> {
			if(s != null){
				countCg.observe(view, aBoolean -> {
					if(aBoolean){

						countCg.removeObservers(view);

						boolean available = false;
						for (int k = 0 ; k < kota.size() ; k++){
							Log.d(TAG, "onChanged: " + kota.get(k));
							if(s.equals(kota.get(k))){
								available = true;
							}
						}
						if (available){
							List<Cg> cege = parseData.parseCgByCity(caregiverTersedia, s);
							int jumlah = cege.size();
							if(jumlah < 1){
								jumlahCg.postValue("Tidak ada caregiver yang tersedia di kota Anda");
							}else {
								jumlahCg.postValue("Ada " + jumlah + " caregiver yang tersedia di kota Anda");
							}
							kotaCust.postValue(s);
						}else {
							jumlahCg.postValue("Belum ada layanan caregiver di kota Anda");
							caregiverTampil.postValue(caregiverTersedia);
						}

					}
				});
			}
		});
	}


	public void batalPesanan(String id) {
		busy.postValue(2);
		new GantiStatus().GantiStatus(id, "ditolak").observe(
						view, aBoolean -> {
							if (aBoolean) {
								busy.postValue(1);
								Intent intent = view.getIntent();
								view.finish();
								view.startActivity(intent);
							} else {
								busy.postValue(1);
							}
						}
		);
	}

	public void CgData() {
		getCg.observe(view, aBoolean -> {
			if(aBoolean){
				if( homeRepository.getCgs() != null){
					getCg.removeObservers(view);

					caregiverSibuk = homeRepository.getCgs().getDalamProses();
					for(int i = 0; i < Objects.requireNonNull(Objects.requireNonNull(homeRepository.getCgs().getData()).getCg()).size() ; i++){
						Caregiver caregiver = homeRepository.getCgs().getData().getCg().get(i);
						Cg cg = new Cg(
										String.valueOf(caregiver.getId()),
										"http://40.88.4.113/esccortPhotos/" + caregiver.getPhoto(),
										caregiver.getName(),
										parseData.parseGender(Objects.requireNonNull(caregiver.getGender())),
										caregiver.getAddress(),
										caregiver.getKeahlian(),
										parseData.parseRating(String.valueOf(caregiver.getRating()))
						);

						boolean available = false;
						for (int k = 0 ; k < kota.size() ; k++){
							if(Objects.equals(cg.getAddress(), kota.get(k))){
								available = true;
							}
						}
						if(!available){
							kota.add(cg.getAddress());
						}

						boolean tersedia = true;
						for(int s = 0; s < homeRepository.getCgs().getDalamProses().size(); s ++){
							String sibuk = homeRepository.getCgs().getDalamProses().get(s).toString();
							if(cg.getId().equals(sibuk)){
								tersedia = false;
							}
						}
						if(tersedia){
							caregiverTersedia.add(cg);
						}
					}

					countCg.postValue(true);
					busy.postValue(0);

				}
			}
		});
	}

	public void PesananData() {
		homeRepository = new HomeRepository(view);
		Objects.requireNonNull(homeRepository.getGetted()).observe(view, aBoolean -> {
			if (aBoolean) {
				if (homeRepository.getPesanans().getPesan() !=  null) {
					homeRepository.getGetted().removeObservers(view);
					for (int i = 0; i < homeRepository.getPesanans().getPesan().size(); i++) {
						Pesan pesan = homeRepository.getPesanans().getPesan().get(i);
						Pesanan pesanan = null;
						try {
							pesanan = new Pesanan(
											String.valueOf(pesan.getTransaksi_id()),
											String.valueOf(pesan.getEsccort_id()),
											pesan.getEsccort_name(),
											pesan.getEsccort_gender(),
											"http://40.88.4.113/esccortPhotos/" + pesan.getPhoto(),
											pesan.getAddress(),
											parseData.parseRating(pesan.getRating()),
											pesan.getKeahlian(),
											pesan.getLansia_name(),
											parseData.parseGender(pesan.getLansia_gender()),
											parseData.parseUmur(pesan.getUmur()),
											parseData.parseStatus(pesan.getStatus()),
											parseData.parseTanggal(pesan.getOrder_time()),
											parseData.parseDurasi(String.valueOf(pesan.getDurasi()), pesan.getPaket()),
											pesan.getAlamat(),
											pesan.getNomor_telp(),
											pesan.getDeskripsi_kerja(),
											pesan.getHobi(),
											pesan.getRiwayat(),
											parseData.parseUang(String.valueOf(pesan.getTotal_bayar()))
							);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						assert pesanan != null;
						if (Objects.equals(pesanan.getStatus(), "Pesanan Berhasil") || Objects.equals(pesanan.getStatus(), "Pesanan Gagal")) {
							pesananRiwayat.add(pesanan);

						} else {
							pesananBerlangsung = pesanan;
						}
					}

					homeRepository.getGetted().removeObservers(view);
					if (pesananBerlangsung != null) {
						SessionLog.SaveDalamPesanan(view, true);
						Handler handler = new android.os.Handler();
						Runnable runnable = () -> view.available(false);handler.postDelayed(runnable, 100);
						busy.postValue(0);
						getCg.postValue(false);
					} else {
						SessionLog.SaveDalamPesanan(view, false);
						view.available(true);
						getCg.postValue(true);
					}

				}else {
					SessionLog.SaveDalamPesanan(view, false);
					view.available(true);
					getCg.postValue(true);
				}
			}
		});
	}

	public void fieldObserver(){
		kotaField.observe(view, s -> {
			Log.d(TAG, "fieldObserver: " + s);
			if(caregiverTersedia != null){
				if(s.equals("Semua")){
					caregiverTampil.postValue(caregiverTersedia);
					fieldObserver2(caregiverTersedia);
				}else {
					List<Cg> els = parseData.parseCgByCity(caregiverTersedia, s);
					caregiverTampil.postValue(els);
					fieldObserver2(els);
				}
			}
		});
	}

	public void fieldObserver2(List<Cg> list){
		genderField.observe(view, s -> {
			if (caregiverTampil != null) {
				switch (s) {
					case "Semua":
						caregiverTampil.postValue(list);
						break;
					case "Laki - laki":
						caregiverTampil.postValue(parseData.parseCgByGender(list, "Laki - laki"));
						break;
					case "Perempuan":
						caregiverTampil.postValue(parseData.parseCgByGender(list, "Perempuan"));
						break;
				}
			}
		});
	}

	public void profile(){
		Intent intent = new Intent(view, TopMenuActivity.class);
		intent.putExtra("layout", "profile");
		view.startActivity(intent);
	}

	public void riwayat(){
		Intent intent = new Intent(view, TopMenuActivity.class);
		intent.putExtra("layout", "riwayat");
		intent.putParcelableArrayListExtra("riwayat",pesananRiwayat);
		view.startActivity(intent);
	}

	public void frag1(){
		Intent intent = new Intent(view, PelajariActivity.class);
		intent.putExtra("layout", "frag1");
		view.startActivity(intent);
	}

	public void frag2(){
		Intent intent = new Intent(view, PelajariActivity.class);
		intent.putExtra("layout", "frag2");
		view.startActivity(intent);
	}

	public void frag3(){
		Intent intent = new Intent(view, PelajariActivity.class);
		intent.putExtra("layout", "frag3");
		view.startActivity(intent);
	}

	public void frag4(){
		Intent intent = new Intent(view, PelajariActivity.class);
		intent.putExtra("layout", "frag4");
		view.startActivity(intent);
	}


}
