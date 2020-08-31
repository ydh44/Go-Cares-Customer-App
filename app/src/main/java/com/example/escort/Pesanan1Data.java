package com.example.escort;

import androidx.annotation.NonNull;

public class Pesanan1Data {
    public String namacg, tglpesan, deskripsi, gaji, status;


    public Pesanan1Data(@NonNull String namacg, String tglpesan, String deskripsi, String gaji, String status) {
        this.namacg = namacg;
        this.tglpesan = tglpesan;
        this.deskripsi = deskripsi;
        this.gaji = gaji;
        this.status = status;
    }

    public String getNamacg(){ return namacg; }
    public  String getTglpesan(){ return  tglpesan; }
    public String getDeskripsi(){ return deskripsi; }
    public String getGaji(){ return gaji; }
    public String getStatus(){ return status; }
}
