package com.example.escort;

import androidx.annotation.NonNull;

public class CGdata {
    public String nama, umur, kota, gender, rating, gaji;


    public CGdata(@NonNull String nama, String umur ,String gender, String kota, String gaji,String rating) {
        this.nama = nama;
        this.gender = gender;
        this.kota = kota;
        this.umur = umur;
        this.gaji = gaji;
        this.rating = rating;
    }

    public String getNama(){ return nama; }
    public  String getUmur() { return umur;}
    public String getKota(){
        return kota;
    }
    public  String getRating(){ return  rating; }
    public String getGaji(){ return gaji; }
    public String getGender(){ return gender; }
}