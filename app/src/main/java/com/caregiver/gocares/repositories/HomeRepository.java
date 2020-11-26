package com.caregiver.gocares.repositories;

import android.content.Context;
import android.util.Log;

import com.caregiver.gocares.APIinterface;
import com.caregiver.gocares.utils.DisposableManager;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.webservices.ApiServices;
import com.caregiver.gocares.webservices.RetrofitServices;

import java.util.Observable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeRepository {
	ApiServices apiServices =  RetrofitServices.Companion.getRetrofitServices().create(ApiServices.class);
	APIinterface apIinterface;
	public void HomeRepository(Context context){
		apIinterface = RetrofitServices.Companion.getRetrofitServices().create(APIinterface.class);

	}
}
