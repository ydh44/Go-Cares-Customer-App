package com.caregiver.gocares.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.caregiver.gocares.models.User;
import com.caregiver.gocares.utils.CekSession;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.webservices.ApiServices;
import com.caregiver.gocares.webservices.RetrofitServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class GetUser {
	public MutableLiveData<Boolean> user = new MutableLiveData<>();
	ApiServices apiServices =  RetrofitServices.Companion.getRetrofitServices().create(ApiServices.class);
	public GetUser(Context context, String bearer){
		apiServices.getCust(bearer).enqueue(new Callback<User>() {
			@Override
			public void onResponse(Call<User> call, Response<User> response) {
				User users = response.body();
				 if(users.getSuccess().getId() != null){
				 	if(!new CekSession().getSession(context)){
				 		SessionLog.SaveToken(context, bearer);
				 		SessionLog.SaveStatus(context, true);
				 		SessionLog.SaveId(context, response.body().getSuccess().getId().toString());
				 		SessionLog.SaveUserName(context, response.body().getSuccess().getName());
					  SessionLog.SaveUserEmail(context, response.body().getSuccess().getEmail());
					  SessionLog.SaveUserAge(context, response.body().getSuccess().getAge());
					  SessionLog.SaveUserAddress(context, response.body().getSuccess().getAddress());
					  SessionLog.SaveUserGender(context, response.body().getSuccess().getGender());
					  SessionLog.SaveUserPhone(context, response.body().getSuccess().getPhone());
				  }
					 user.postValue(true);
				 }else {
				 	user.postValue(false);
				 }
			}
			@Override
			public void onFailure(Call<User> call, Throwable t) {
					user.postValue(null);
			}
		});
	}
}
