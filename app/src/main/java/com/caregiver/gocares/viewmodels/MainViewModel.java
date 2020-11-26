package com.caregiver.gocares.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.caregiver.gocares.utils.GetLocation;
import com.caregiver.gocares.utils.GetUser;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.utils.ShorterName;
import com.caregiver.gocares.utils.UpToken;
import com.caregiver.gocares.views.activity.MainActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static android.content.ContentValues.TAG;

public class MainViewModel extends ViewModel
{
	public ObservableField<String> nama = new ObservableField<>();
	String kota;
	MainActivity view;

	public MainViewModel(MainActivity mainActivity){
		this.view= mainActivity;
		String nama = new ShorterName(SessionLog.GetUserName(view)).getShort();
		if(nama != null){
			this.nama.set(nama);
		}else {
			this.nama.set("Selamat Datang !");
		}
		new GetUser(view, SessionLog.GetToken(view)).user.observe(view, aBoolean -> {
			if(aBoolean){
				new UpToken(view);
			}else {
				SessionLog.Delete(view);
				view.finish();
			}
		});


		Log.d(TAG, "MainViewModel: " + SessionLog.GetFcm(view));
	}

}
