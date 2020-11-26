package com.caregiver.gocares.webservices

import com.caregiver.gocares.models.CgResponse
import com.caregiver.gocares.models.LoginUserResponse
import com.caregiver.gocares.models.User
import com.google.gson.JsonElement
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiServices {

	@FormUrlEncoded
	@POST("register")
	fun reg(
					@Field("email") email: String,
					@Field("name") nama: String,
					@Field("age") umur: String,
					@Field("gender") gender: String,
					@Field("phone") telepon: String,
					@Field("address") alamat: String,
					@Field("password") passsword1: String,
					@Field("c_password") password2: String
	):Call<ResponseBody>

	@FormUrlEncoded
	@POST("login")
	fun login(
					@Field("email") email: String,
					@Field("password") passsword: String
	):Call<LoginUserResponse>

	@POST("details")
	fun getCust(
					@Header("Authorization") bearer: String?
	): Call<User>

	@FormUrlEncoded
	@POST("uptoken")
	fun upToken(
					@Field("id") id : String,
					@Field("token") token : String
	): Call<ResponseBody>

	@GET("esccort")
	fun getCg(): Call<List<CgResponse>>


}