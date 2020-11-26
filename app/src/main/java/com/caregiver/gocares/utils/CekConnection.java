package com.caregiver.gocares.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.MutableLiveData;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CekConnection {
	Context context;
	public MutableLiveData<Boolean> status = new MutableLiveData<>();
	CompositeDisposable compositeDisposable  = new CompositeDisposable();

	public CekConnection(Context context, Boolean run) {
		this.context = context;
		if(run){
			Disposable disposable = ReactiveNetwork
							.observeNetworkConnectivity(context)
							.flatMapSingle(connectivity -> ReactiveNetwork.checkInternetConnectivity())
							.subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(isConnected -> status.postValue(isConnected));
			compositeDisposable.add(disposable);
		}
	}


	public boolean justCheck(){
		ConnectivityManager cm =
						(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null &&
						activeNetwork.isConnectedOrConnecting();
	}


}
