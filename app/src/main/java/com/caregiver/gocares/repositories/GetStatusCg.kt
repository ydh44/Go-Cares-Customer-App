package com.caregiver.gocares.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.caregiver.gocares.webservices.ApiServices
import com.caregiver.gocares.webservices.RetrofitServices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetStatusCg {
	fun GetStatusCg(context : Context, idCg: String) : MutableLiveData<Boolean>{
		val statusCg = MutableLiveData<Boolean>()
		var apiServices = RetrofitServices.getRetrofitServices().create<ApiServices>(ApiServices::class.java)
		apiServices.getstatus(idCg).enqueue(object : Callback<ResponseBody> {
			override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
				if (response.code() == 200) {
					statusCg.postValue(true)
					Toast.makeText(context, "Caregiver sedang dalam pesanan. Jika telah melakukan transaksi, hubungi admin", Toast.LENGTH_LONG).show()
				} else if (response.code() == 401) {
					statusCg.postValue(false)
				}
			}

			override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

			}
		})
		return statusCg;
	}
}