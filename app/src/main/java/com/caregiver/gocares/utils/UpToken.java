package com.caregiver.gocares.utils;

import android.content.Context;
import android.media.session.MediaSession;

import androidx.lifecycle.MutableLiveData;

import com.caregiver.gocares.webservices.ApiServices;
import com.caregiver.gocares.webservices.RetrofitServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpToken {
	public MutableLiveData<Boolean> user = new MutableLiveData<>();
	public UpToken(Context context){
		if(!SessionLog.GetFcmUploaded(context)){
			ApiServices apiServices = RetrofitServices.Companion.getRetrofitServices().create(ApiServices.class);
			apiServices.upToken(SessionLog.GetId(context), SessionLog.GetFcm(context)).enqueue(new Callback<ResponseBody>() {
				@Override
				public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
					if(response.code() == 200){
						SessionLog.SaveFcmUploaded(context, true);
						user.postValue(true);
					}else {
						user.postValue(false);
					}
				}

				@Override
				public void onFailure(Call<ResponseBody> call, Throwable t) {
					user.postValue(null);
				}
			});
		}
	}
}
