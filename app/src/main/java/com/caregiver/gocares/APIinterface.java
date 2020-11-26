package com.caregiver.gocares;

import com.google.gson.JsonElement;
import com.google.gson.internal.$Gson$Preconditions;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIinterface
{
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register(
            @Field("email") String email,
            @Field("name") String nama,
            @Field("age") String umur,
            @Field("gender") String gender,
            @Field("phone") String telepon,
            @Field("address") String alamat,
            @Field("password") String passsword1,
            @Field("c_password") String password2
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String passsword
    );

    @POST("details")
    Call<ResponseBody> getCust(
            @Header("Authorization") String bearer
    );

    @GET("esccort")
    Call<JsonElement> getCg(
    );

    @GET("load/{idUserLogin}")
    Call<ResponseBody> loadLansia(
            @Path("idUserLogin") String idUserLogin
    );

    @FormUrlEncoded
    @POST("pesan")
    Call<ResponseBody> pesan(
            @Field("nama") String nama,
            @Field("umur") String umur,
            @Field("gender") String kelamin,
            @Field("hobi") String hobi,
            @Field("riwayat") String sakit,
            @Field("paket") String paket,
            @Field("durasi") int durasi,
            @Field("alamat") String alamat,
            @Field("nomor_telp") String telepon,
            @Field("deskripsi_kerja") String deskripsi,
            @Field("user_id") String userid,
            @Field("esccort_id") String cgid,
            @Field("lansia_id") String lansiaid,
            @Field("lansiauses") String useslansia
    );
    @GET("loadtransaksi/{id_pesanan}")
    Call<ResponseBody> getpesanan(
            @Path("id_pesanan") String id_pesanan
    );

    @GET("status/{idUserLogin}/belum")
    Call<JsonElement>  getstatus_belum(
            @Path("idUserLogin") String idUserLogin
    );
    @GET("status/{idUserLogin}/menunggu")
    Call<JsonElement>  getstatus_menunggu(
            @Path("idUserLogin") String idUserLogin
    );
    @GET("status/{idUserLogin}/dikonfirmasi")
    Call<JsonElement>  getstatus_dikonfirmasi(
            @Path("idUserLogin") String idUserLogin
    );
    @GET("status/{idUserLogin}/merawat")
    Call<JsonElement>  getstatus_merawat(
            @Path("idUserLogin") String idUserLogin
    );
    @GET("status/{idUserLogin}/selesai")
    Call<JsonElement>  getstatus_diterima(
            @Path("idUserLogin") String idUserLogin
    );

    @FormUrlEncoded
    @POST("cg/filter")
    Call<JsonElement> getfilter(
            @Field("gender") String gender,
            @Field("age1") String age1,
            @Field("age2") String age2
    );

    @GET("getdetailcg/{idcg}")
    Call<ResponseBody> getstatus(
            @Path("idcg") String cgid
    );
}
