package com.caregiver.gocares;

import androidx.annotation.NonNull;

public class CGdata {
    public String id, urlgambar, nama, umur, kota, gender, keahlian, rating, gaji, cgstatus;


    public CGdata(@NonNull String id, String urlgambar, String nama, String umur , String gender, String keahlian, String kota, String gaji, String rating, String cgstatus) {
        this.id = id;
        this.urlgambar = urlgambar;
        this.nama = nama;
        this.gender = gender;
        this.kota = kota;
        this.umur = umur;
        this.gaji = gaji;
        this.rating = rating;
        this.keahlian = keahlian;
        this.cgstatus = cgstatus;
    }

    public String getId(){ return id;}
    public  String getUrlgambar(){return  urlgambar;}
    public String getNama(){ return nama; }
    public  String getUmur() { return umur;}
    public String getKota(){ return kota; }
    public String getKeahlian(){ return keahlian; }
    public  String getRating(){ return  rating; }
    public String getGaji(){ return gaji; }
    public String getGender(){ return gender; }
    public String getCgstatus(){ return cgstatus; }
}