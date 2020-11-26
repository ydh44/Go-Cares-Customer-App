package com.caregiver.gocares.repositories

import androidx.lifecycle.MutableLiveData
import com.caregiver.gocares.models.LoginUser
import com.caregiver.gocares.models.LoginUserResponse
import com.caregiver.gocares.models.RegUser
import com.caregiver.gocares.webservices.ApiServices
import com.caregiver.gocares.webservices.RetrofitServices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {
	private val apiServices : ApiServices = RetrofitServices.getRetrofitServices().create(ApiServices::class.java)
	fun userLogin(loginUser: LoginUser) : MutableLiveData<LoginUserResponse> {
		val loginResponse = MutableLiveData<LoginUserResponse>()
		apiServices.login(loginUser.email , loginUser.password)
						.enqueue(object : Callback<LoginUserResponse>{
							override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
								return
							}
							override fun onResponse(call: Call<LoginUserResponse>, response: Response<LoginUserResponse>) {
								if(response.code() == 200) {
									loginResponse.postValue(response.body())
								}else{
									loginResponse.postValue(null)
								}
							}
						})
		return loginResponse
	}

	fun userReg(regUser : RegUser) : MutableLiveData<String> {
		val regResponse = MutableLiveData<String>()
		apiServices.reg(regUser.email, regUser.nama, regUser.umur, regUser.gender, regUser.telepon, regUser.alamat, regUser.password1, regUser.password2)
						.enqueue(object : Callback<ResponseBody>{
							override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
								return
							}
							override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
								regResponse.postValue(response.code().toString())
							}
						})
		return regResponse
	}
}