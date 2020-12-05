package com.caregiver.gocares.views.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.CheckoutFragmentBinding;
import com.caregiver.gocares.models.LansiaResponse;
import com.caregiver.gocares.utils.CekForm;
import com.caregiver.gocares.utils.LoadingDialog;
import com.caregiver.gocares.utils.ParseData;
import com.caregiver.gocares.viewmodels.CheckoutViewModel;
import com.caregiver.gocares.views.activity.TransaksiActivity;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CheckoutFragment extends Fragment {

	public CheckoutViewModel viewModel;
	public CheckoutFragmentBinding binding;
	public LansiaResponse lansia;
	ParseData parseData;
	public String idCg;
	LoadingDialog loadingDialog;

	public static CheckoutFragment newInstance() {
		return new CheckoutFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		viewModel = new CheckoutViewModel(this);
		binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.checkout_fragment, null, false);
		View view = this.binding.getRoot();
		binding.setCheckoutModel(viewModel);
		parseData = new ParseData();
		assert getArguments() != null;
		idCg =  getArguments().getString("id_cg");
		loadingDialog = new LoadingDialog(requireActivity());

		List<String> pilgender = new ArrayList<>();
		pilgender.add("Laki - laki");
		pilgender.add("Perempuan");
		ArrayAdapter<String> adapgender = new ArrayAdapter<>(
						getActivity(),
						R.layout.dropdown_item,
						pilgender
		);
		binding.etGender.setAdapter(adapgender);

		List<String> pilpaket = new ArrayList<>();
		pilpaket.add("Harian");
		pilpaket.add("Bulanan");
		ArrayAdapter<String> adappaket = new ArrayAdapter<>(
						getActivity(),
						R.layout.dropdown_item,
						pilpaket
		);
		binding.etPaket.setAdapter(adappaket);

		viewModel.paket.observe(requireActivity(), s -> {
			binding.etDurasi.setText("" , false);
				List<String> pildurasi1 = new ArrayList<>();
				pildurasi1.clear();
				if(s.equals("Harian")){
					for(int i = 1; i <= 30; i++){
						pildurasi1.add(i + " Hari");
					}
				}else if(s.equals("Bulanan")){
					for(int i = 1; i <= 12; i++){
						pildurasi1.add(i + " Bulan");
					}
				}
				ArrayAdapter<String> adapdurasi1 = new ArrayAdapter<>(
								getActivity(),
								R.layout.dropdown_item,
								pildurasi1
				);
				binding.etDurasi.setAdapter(adapdurasi1);

		});

		binding.checkbox.setOnClickListener(view1 -> {
			if(!binding.checkbox.isChecked()){
				binding.etNama.setText(null);
				binding.etUmur.setText(null);
				binding.etGender.setText(null, false);
				binding.etHobi.setText(null);
				binding.etRiwayat.setText(null);
			}
		});

		binding.checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
			if(b){
				setLansiaValue(lansia);
				viewModel.onChangeListener();
			}
		});
		lansiaListener();
		resultListener();
		busy();

		new CekForm(getActivity(), binding.etNama, binding.ilNama, false);
		new CekForm(getActivity() , binding.etUmur, binding.ilUmur, false);
		new CekForm(getActivity(), binding.etGender, binding.ilGender, false);
		new CekForm(getActivity(), binding.etHobi, binding.ilHobi, false);
		new CekForm(getActivity(), binding.etRiwayat, binding.ilRiwayat, false);
		new CekForm(getActivity(), binding.etPaket, binding.ilPaket, false);
		new CekForm(getActivity(), binding.etDurasi, binding.ilDurasi, false);
		new CekForm(getActivity(), binding.etAktivitas, binding.ilAktivitas, false);
		new CekForm(getActivity() , binding.etTelepon, binding.ilTelepon, false);
		new CekForm(getActivity(), binding.etAlamat, binding.ilAlamat, false);

		return view;
	}

	private void resultListener() {
		viewModel.pesananId.observe(requireActivity(), id -> {
			Log.d(TAG, "onChangedwwes pesens: " + id);
			if( id != null){
				Log.d(TAG, "onChangedwwes pesens: ");
				viewModel.jumlahBayar.observe(requireActivity(), jumlah -> {
					if(jumlah != null){
						Log.d(TAG, "onChangedwwes pesen: ");
						((TransaksiActivity) requireActivity()).bayar(id,idCg, jumlah);
					}
				});
			}
		});
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
	private void lansiaListener() {
		viewModel.lansiaCust.observe(requireActivity(), lansiaResponse -> {
			if(lansiaResponse != null){
				lansia = lansiaResponse;
				binding.checkbox.setChecked(true);
				binding.lansiaData.setVisibility(View.VISIBLE);
			}
		});

		viewModel.dataLansia.observe(requireActivity(), aBoolean -> binding.checkbox.setChecked(aBoolean));


	}

	public void failure(String error)
	{
		switch (error){
			case  "nama":
				binding.ilNama.setErrorEnabled(true);
				binding.ilNama.setError("Masukan nama lansia");
				break;
			case "umur":
				binding.ilUmur.setErrorEnabled(true);
				binding.ilUmur.setError("Masukan umur lansia");
				break;
			case "gender" :
				binding.ilGender.setErrorEnabled(true);
				binding.ilGender.setError("Masukan gender lansia");
				break;
			case "hobi" :
				binding.ilHobi.setErrorEnabled(true);
				binding.ilHobi.setError("Masukan hobi atau kebiasaan lansia");
				break;
			case "riwayat" :
				binding.ilRiwayat.setErrorEnabled(true);
				binding.ilRiwayat.setError("Masukan riwayat penyakit lansia");
				break;
			case "paket" :
				binding.ilPaket.setErrorEnabled(true);
				binding.ilPaket.setError("Pilih jenis paket");
				break;
			case "durasi" :
				binding.ilDurasi.setErrorEnabled(true);
				binding.ilDurasi.setError("Tentukan durasi pemesanan");
				break;
			case "aktivitas" :
				binding.ilAktivitas.setErrorEnabled(true);
				binding.ilAktivitas.setError("Masukan aktivitas keseharian lansia");
				break;
			case  "alamat":
				binding.ilAlamat.setErrorEnabled(true);
				binding.ilAlamat.setError("Masukan alamat");
				break;
			case "telepon":
				binding.ilTelepon.setErrorEnabled(true);
				binding.ilTelepon.setError("Masukan telepon yang valid, diawali 0 ");
				break;
		}
	}

	public Boolean getCheckState(){
		boolean checked;
		checked = binding.checkbox.isChecked();
		return checked;
	}

	public void setLansiaValue(LansiaResponse lansia){
		binding.etNama.setText(lansia.getLansia().getNama());
		binding.etUmur.setText(lansia.getLansia().getUmur());
		binding.etGender.setText(parseData.parseGender(lansia.getLansia().getGender()), false);
		binding.etHobi.setText(lansia.getLansia().getHobi());
		binding.etRiwayat.setText(lansia.getLansia().getRiwayat());
	}

	public void busy(){

		final SkeletonScreen[] skeleton = new SkeletonScreen[1];
		viewModel.busy.observe(requireActivity(), integer -> {
			if(integer == 1){
				loadingDialog.dismissLoading();
			}else if(integer == 2){
				loadingDialog.startLoading();
			}else if(integer == 3){
				Log.d(TAG, "busy: ");
				skeleton[0] = Skeleton.bind(binding.dataLansia)
								.load(R.layout.checkout_fragment_load)
								.show();
			}else if(integer == 0){
				skeleton[0].hide();
			}
		});
	}


}