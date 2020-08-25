package com.example.escort;

public class FragmentPesanan {
    private String cgpesananname, tanggal, tanggalpesanan, waitconfirm;

    public FragmentPesanan(String cgpesananname, String tanggal, String tanggalpesanan, String waitconfirm) {
        this.cgpesananname = cgpesananname;
        this.tanggal = tanggal;
        this.tanggalpesanan = tanggalpesanan;
        this.waitconfirm = waitconfirm;
    }
    public String getCgpesananname(){
        return cgpesananname;
    }
    public  String getTanggal() { return tanggal;}
    public String getTanggalpesanan(){
        return tanggalpesanan;
    }
    public  String getWaitconfirm(){ return  waitconfirm; }

}
