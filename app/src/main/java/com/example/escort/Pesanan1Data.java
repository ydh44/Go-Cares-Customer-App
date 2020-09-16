package com.example.escort;

import androidx.annotation.NonNull;

public class Pesanan1Data {
    public String  photo, status, id_pesan, id_cg, id_lansia, namacg, tglpesan, paket, durasi,  deskripsi, telepon, alamat, gaji;

    public Pesanan1Data(@NonNull  String photo, String status, String id_pesanan, String id_cg, String id_lansia, String namacg, String tglpesan, String paket, String durasi, String deskripsi, String telepon, String alamat, String gaji) {
        this.photo = photo;
        this.id_pesan = id_pesanan;
        this.id_cg = id_cg;
        this.id_lansia = id_lansia;
        this.paket = paket;
        this.durasi = durasi;
        this.alamat = alamat;
        this.telepon = telepon;
        this.namacg = namacg;
        this.tglpesan = tglpesan;
        this.deskripsi = deskripsi;
        this.gaji = gaji;
        this.status = status;
    }
    public String getPhoto() {
        return photo;
    }

    public String getStatus() {
        return status;
    }

    public String getId_pesan() {
        return id_pesan;
    }


    public String getId_cg() {
        return id_cg;
    }

    public String getId_lansia() {
        return id_lansia;
    }

    public String getNamacg() {
        return namacg;
    }

    public String getTglpesan() {
        return tglpesan;
    }

    public String getPaket() {
        return paket;
    }

    public String getDurasi() {
        return durasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTelepon() {
        return telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getGaji() {
        return gaji;
    }
}
