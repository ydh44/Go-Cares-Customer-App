package com.example.escort;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CGdata {
    public String urlgambar, nama, umur, kota, gender, keahlian, rating, gaji;


    public CGdata(@NonNull String urlgambar, String nama, String umur , String gender, String keahlian, String kota, String gaji, String rating) {
        this.urlgambar = urlgambar;
        this.nama = nama;
        this.gender = gender;
        this.kota = kota;
        this.umur = umur;
        this.gaji = gaji;
        this.rating = rating;
        this.keahlian = keahlian;
    }

    public  String getUrlgambar(){return  urlgambar;}
    public String getNama(){ return nama; }
    public  String getUmur() { return umur;}
    public String getKota(){ return kota; }
    public String getKeahlian(){ return keahlian; }
    public  String getRating(){ return  rating; }
    public String getGaji(){ return gaji; }
    public String getGender(){ return gender; }
}