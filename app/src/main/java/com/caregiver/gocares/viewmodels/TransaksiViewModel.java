package com.caregiver.gocares.viewmodels;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.caregiver.gocares.repositories.GetStatusCg;
import com.caregiver.gocares.utils.CekConnection;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.views.activity.TransaksiActivity;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class TransaksiViewModel extends ViewModel {

	TransaksiActivity view;
	Handler handler;
	CekConnection cekConnection;
	public MutableLiveData<Boolean> conn = new MutableLiveData<>();
	public MutableLiveData<Integer> status = new MutableLiveData<>();
	int i;

	public TransaksiViewModel(TransaksiActivity transaksiActivity){

		this.view = transaksiActivity;
		cekConnection = new CekConnection(view, true);
		status.postValue(0);
		i = 0;

		cekConn();

	}

	public void cekConn(){
		cekConnection.status.observe(view, new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if(aBoolean){
					status.observe(view, s ->
							getStatus());
				}else {
					conn.postValue(false);
				}
			}
		});
	}

	public void getStatus(){
		new GetStatusCg().GetStatusCg(view, view.idCg).observe(view, aBoolean -> {
			if(aBoolean){
				status.removeObservers(view);
				SessionLog.GetHomeMessage(view);
				view.finish();
			}else {
				if (view.isFinishing()) {
					status.removeObservers(view);
				}else {
					handler = new Handler();
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							status.postValue(i++);
							Log.d(TAG, "run: ");
						}
					};handler.postDelayed(runnable, 10000);
				}
			}
		});
	}
}
