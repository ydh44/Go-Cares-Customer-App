package com.caregiver.gocares.webservices

import com.caregiver.gocares.models.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

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
					@Field("id") id: String,
					@Field("token") token: String
	): Call<ResponseBody>

	@GET("esccort")
	fun getCg(): Observable<CgResponse>

	@FormUrlEncoded
	@POST("allstatus")
	fun getPesanan(
					@Field("iduser") id: String
	): Observable<PesananResponse>

	@FormUrlEncoded
	@POST("updatestatus")
	fun updateStatus(
					@Field("id") id: String,
					@Field("status") status: String
	): Call<ResponseBody>

	@GET("getdetailcg/{idcg}")
	fun getstatus(
					@Path("idcg") cgid: String?
	): Call<ResponseBody>

	@GET("load/{idUserLogin}")
	fun getLansia(
					@Path("idUserLogin") idUserLogin: String?
	): Call<LansiaResponse>

	@GET("loadtransaksi/{id_pesanan}")
	fun getpesanan(
					@Path("id_pesanan") id_pesanan: String?
	): Call<PesananResponse>

	@FormUrlEncoded
	@POST("pesan")
	fun pesan(
					@Field("nama") nama: String?,
					@Field("umur") umur: String?,
					@Field("gender") kelamin: String?,
					@Field("hobi") hobi: String?,
					@Field("riwayat") sakit: String?,
					@Field("paket") paket: String?,
					@Field("durasi") durasi: String?,
					@Field("alamat") alamat: String?,
					@Field("nomor_telp") telepon: String?,
					@Field("deskripsi_kerja") deskripsi: String?,
					@Field("user_id") userid: String?,
					@Field("esccort_id") cgid: String?,
					@Field("lansia_id") lansiaid: String?,
					@Field("lansiauses") useslansia: String?
	): Call<PesananResponse2>
}