package com.caregiver.gocares.repositories

import android.net.DnsResolver
import androidx.lifecycle.MutableLiveData
import com.caregiver.gocares.webservices.ApiServices
import com.caregiver.gocares.webservices.RetrofitServices.Companion.getRetrofitServices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class GantiStatus {
	fun GantiStatus(id : String, status : String) : MutableLiveData<Boolean>{
		val changed = MutableLiveData<Boolean>()
		var apiServices = getRetrofitServices().create<ApiServices>(ApiServices::class.java)
		apiServices.updateStatus(id, status)
						.enqueue(object : retrofit2.Callback<ResponseBody>{
							override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
								if(response.code() == 200){
									changed.postValue(true)
								}else{
									changed.postValue(false)
								}
							}

							override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
								changed.postValue(false)
							}
						})
		return changed
	}

}