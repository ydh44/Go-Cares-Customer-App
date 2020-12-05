package com.caregiver.gocares.repositories

import androidx.lifecycle.MutableLiveData
import com.caregiver.gocares.models.Checkout
import com.caregiver.gocares.models.Pesanan
import com.caregiver.gocares.models.PesananResponse
import com.caregiver.gocares.models.PesananResponse2
import com.caregiver.gocares.webservices.ApiServices
import com.caregiver.gocares.webservices.RetrofitServices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiRepository {
	fun pesan(checkout : Checkout) : MutableLiveData<PesananResponse2>{
		var pesanned = MutableLiveData<PesananResponse2>()
		var apiServices = RetrofitServices.getRetrofitServices().create<ApiServices>(ApiServices::class.java)

		apiServices.pesan(
						checkout.nama,
						checkout.umur,
						checkout.gender,
						checkout.hobi,
						checkout.riwayat,
						checkout.paket,
						checkout.durasi,
						checkout.alamat,
						checkout.telepon,
						checkout.aktivitas,
						checkout.user_id,
						checkout.cg_id,
						checkout.lansia_id,
						checkout.lansiauses
		).enqueue(object  : Callback<PesananResponse2>{
			override fun onResponse(call: Call<PesananResponse2>, response: Response<PesananResponse2>) {
				 if(response.code() == 200){
				 	pesanned.postValue(response.body())
				 }else{
				 	return
				 }
			}

			override fun onFailure(call: Call<PesananResponse2>, t: Throwable) {
				return
			}
		})
		return pesanned
	}
}