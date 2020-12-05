
package com.caregiver.gocares.viewmodels;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.caregiver.gocares.models.User;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.models.LoginUser;
import com.caregiver.gocares.models.LoginUserResponse;
import com.caregiver.gocares.repositories.AuthRepository;
import com.caregiver.gocares.utils.CekConnection;
import com.caregiver.gocares.utils.LoadingDialog;
import com.caregiver.gocares.views.activity.LoginActivity;
import com.google.gson.JsonArray;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {
	public MutableLiveData<String> email = new MutableLiveData<>();
	public MutableLiveData<String> password = new MutableLiveData<>();
	public MutableLiveData<Boolean> busy = new MutableLiveData<>();
	public MutableLiveData<LoginUserResponse> response = new MutableLiveData<>();
	final LoginActivity view;
	final CekConnection cekConnection;
	final CekConnection cekConnection2;

	public LoginViewModel(LoginActivity loginActivity) {
		this.view = loginActivity;
		busy.postValue(false);
		cekConnection2 = new CekConnection(view, false);
		cekConnection = new CekConnection(view, true);
		checkConn();
	}

	public void checkConn() {
		cekConnection.status.observe(view, new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (aBoolean) {
					view.connection(true);
				} else {
					view.connection(false);
					busy.postValue(false);
				}
			}
		});
	}

	public void onLoginClick() {
		LoginUser loginUser = new LoginUser();
		loginUser.setEmail(email.getValue());
		loginUser.setPassword(password.getValue());
		if (validation(loginUser)) {
			UIUtil.hideKeyboard(view);
			if (cekConnection2.justCheck()) {
				busy.postValue(true);
				response = new AuthRepository().userLogin(loginUser);
				response.observe(view, loginUserResponse -> {
					busy.postValue(false);
					if (loginUserResponse != null) {
						if (loginUserResponse.getSuccess() != null) {
							view.in(loginUserResponse.getSuccess().getToken());

						} else {
							view.failure("wrong");
						}

					} else {
						view.failure("wrong");
					}
				});
			} else {
				view.failure("conn");
			}
		}
	}

	public boolean validation(LoginUser loginUser) {
		boolean valid = true;
		String email = loginUser.getEmail();
		String password = loginUser.getPassword();

		if (email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			view.failure("email");
			valid = false;
		}
		if (password == null) {
			view.failure("password");
			valid = false;
		}
		return valid;
	}
}
