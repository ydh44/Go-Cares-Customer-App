package com.caregiver.gocares.viewmodels;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.caregiver.gocares.models.RegUser;
import com.caregiver.gocares.repositories.AuthRepository;
import com.caregiver.gocares.utils.CekConnection;
import com.caregiver.gocares.utils.LoadingDialog;
import com.caregiver.gocares.views.activity.RegActivity;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

public class RegViewModel extends ViewModel {

	public MutableLiveData<String> email = new MutableLiveData<>();
	public MutableLiveData<String> nama = new MutableLiveData<>();
	public MutableLiveData<String> umur = new MutableLiveData<>();
	public MutableLiveData<String> gender = new MutableLiveData<>();
	public MutableLiveData<String> telepon = new MutableLiveData<>();
	public MutableLiveData<String> alamat = new MutableLiveData<>();
	public MutableLiveData<String> password1 = new MutableLiveData<>();
	public MutableLiveData<String> password2 = new MutableLiveData<>();
	public MutableLiveData<String> response = new MutableLiveData<>();
	public MutableLiveData<Boolean> busy = new MutableLiveData<>();
	final RegActivity view;
	final CekConnection cekConnection;
	final CekConnection cekConnection2;

	public RegViewModel(RegActivity regActivity){
		this.view = regActivity;
		busy.postValue(false);
		cekConnection = new CekConnection(view, true);
		cekConnection2 = new CekConnection(view, false);
		cekConn();
	}

	public void cekConn(){
		cekConnection.status.observe(view, aBoolean -> {
			if(aBoolean){
				view.connection(true);
			}else{
				view.connection(false);
				busy.postValue(false);
			}
		});
	}

	public void onRegClick(){
		RegUser regUser = new RegUser();
		regUser.setEmail(email.getValue());
		regUser.setNama(nama.getValue());
		regUser.setUmur(umur.getValue());
		if(gender.getValue() != null){
			if(gender.getValue().equals("Laki - laki")){
				regUser.setGender("L");
			}else if(gender.getValue().equals("Perempuan")){
				regUser.setGender("P");
			}else{
				regUser.setGender(null);
			}
		}
		regUser.setTelepon(telepon.getValue());
		regUser.setAlamat(alamat.getValue());
		regUser.setPassword1(password1.getValue());
		regUser.setPassword2(password2.getValue());
		if(validation(regUser)){
			UIUtil.hideKeyboard(view);
			if (cekConnection2.justCheck()) {
				busy.postValue(true);
				response = new AuthRepository().userReg(regUser);
				response.observe(view, s -> {
					busy.postValue(false);
					if(s.equals("200")){
						view.reg();
					}else if(s.equals("500")){
						view.failure("wrong");
					}else {
						view.failure("conn");
					}
				});

			}else {
				view.failure("conn");
			}
		}
	}

	public Boolean validation(RegUser regUser) {
		boolean valid = true;

		if(regUser.getEmail() == null|| !Patterns.EMAIL_ADDRESS.matcher(regUser.getEmail()).matches()){
			view.failure("email");
			valid = false;
		}

		if(regUser.getNama() == null){
			view.failure("nama");
			valid = false;
		}

		if(regUser.getUmur() == null){
			view.failure("umur");
			valid = false;
		}

		if(regUser.getGender() == null){
			view.failure("gender");
			valid = false;
		}

		if(regUser.getTelepon() == null || !Patterns.PHONE.matcher(regUser.getTelepon()).matches()){
			view.failure("telepon");
			valid = false;
		}else if(regUser.getTelepon().length()<= 10 || regUser.getTelepon().length() > 13 || !regUser.getTelepon().startsWith("0")){
			view.failure("telepon");
			valid = false;
		}

		if(regUser.getAlamat() == null){
			view.failure("alamat");
			valid = false;
		}

		if(regUser.getPassword1() == null){
			view.failure("password1");
			valid = false;
		}

		if(regUser.getPassword2() == null){
			view.failure("password2");
			valid = false;
		}

		if(regUser.getPassword1() != null && regUser.getPassword2() != null){
			if(!regUser.getPassword1().equals(regUser.getPassword2())) {
				view.failure("password3");
				valid = false;
			}
		}

		return valid;
	}

}
