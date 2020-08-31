package com.example.escort;

import androidx.annotation.NonNull;

public class PesananData {
    public String namacg, tglpesan, tglmulai, tglselesai, deskripsi, gaji;


    public PesananData(@NonNull String namacg, String tglpesan, String tglmulai, String tglselesai, String deskripsi, String gaji) {
        this.namacg = namacg;
        this.tglpesan = tglpesan;
        this.tglmulai = tglmulai;
        this.tglselesai = tglselesai;
        this.deskripsi = deskripsi;
        this.gaji = gaji;
    }

    public String getNamacg(){ return namacg; }
    public  String getTglmulai() { return tglmulai;}
    public String getTglselesai(){
        return tglselesai;
    }
    public  String getTglpesan(){ return  tglpesan; }
    public String getDeskripsi(){ return deskripsi; }
    public String getGaji(){ return gaji; }
}
