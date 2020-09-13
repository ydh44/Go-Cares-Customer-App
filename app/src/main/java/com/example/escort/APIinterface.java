package com.example.escort;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
}
