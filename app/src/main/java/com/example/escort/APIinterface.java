package com.example.escort;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
            @Field("lansia_id") String lansiaid
    );

    @GET("status/{idUserLogin}/belum")
    Call<JsonElement>  getstatus_belum(
            @Path("idUserLogin") String idUserLogin
    );
}
