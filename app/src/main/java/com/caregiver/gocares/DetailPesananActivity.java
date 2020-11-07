package com.caregiver.gocares;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesananActivity extends AppCompatActivity {

    @BindView(R.id.profilemg)
    ImageView profileimg;

    @BindView(R.id.nama)
    TextView nama;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.date)
    TextView tanggal;
    @BindView(R.id.paketdur)
    TextView paket;
    @BindView(R.id.angkadur)
    TextView durasi;
    @BindView(R.id.telepon)
    TextView telepon;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.deskripsi)
    TextView deskripsi;
    @BindView(R.id.gaji)
    TextView gaji;
    @BindView(R.id.txt)
    TextView detail;
    @BindView(R.id.tulisans)
    TextView infos;

    @OnClick(R.id.btnBack)
        void back(){
        finish();
    }
    @OnClick(R.id.btnNext)
            void pindah(){
        Intent i = new Intent(this, PembayaranActivity.class);
        i.putExtra("idcg", Rid_cg);
        i.putExtra("id", Sid_pesan);
        i.putExtra("total", Sgaji);
        startActivity(i);
        finish();
    }
    @BindView(R.id.btnNext) ImageButton bayar;
    @OnClick(R.id.btnlansia)
    void dlansia(){
        Detaillansia();
    }
    @OnClick(R.id.btnprofile)
    void profile(){
       profil();
    }
    @OnClick(R.id.btnnama)
    void profile2(){
        profil();
    }
    APIinterface apIinterface;

    String Surlgambar, Sstatus, Sid_pesan, Snamacg, Stglpesan, Spaket, Sdurasi, Sdeskripsi, Stelepon, Salamat, Sgaji;

    String Rid_cg, Rid_lansia, Rnamalansia, Rnamacg, Rumurcg, Rumurlansia, Rgenderlansia, Rgendercg, Rhobilansia, Rsakitlansia, Rkeahliancg, Rgajicg, Ralamatcg, Rratingcg, Rcgstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpesanan);
        ButterKnife.bind(this);

        Intent i = getIntent();
        Surlgambar = i.getStringExtra("urlgambar");
        Sstatus = i.getStringExtra("status");
        Sid_pesan = i.getStringExtra("id_pesan");
        Snamacg = i.getStringExtra("namacg");
        Stglpesan = i.getStringExtra("tglpesan");
        Spaket = i.getStringExtra("paket");
        Sdurasi = i.getStringExtra("durasi");
        Sdeskripsi = i.getStringExtra("deskripsi");
        Stelepon = i.getStringExtra("telepon");
        Salamat = i.getStringExtra("alamat");
        Sgaji = i.getStringExtra("gaji");

        check();

        Log.d("TAG", "onCreate: " + Sid_pesan);

        SpannableString content = new SpannableString("Detail Lansia");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        detail.setText(content);

        Picasso.get().load(Surlgambar).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(profileimg);
        if(Sstatus.equals("belum")){
            status.setText("Menunggu Pembayaran");
        }else if (Sstatus.equals("menunggu")){
            status.setText("Menunggu Konfirmasi");
        }else if(Sstatus.equals("dikonfirmasi")){
            status.setText("Telah Dikonfirmasi");
            infos.setVisibility(View.VISIBLE);
        }else if(Sstatus.equals("merawat")){
            status.setText("Sedang Merawat");
        }else if(Sstatus.equals("diterima")){
            status.setText("Pesanan Selesai");
        }else if(Sstatus.equals("ditolak")){
            status.setText("Pesanan Gagal");
        }
        nama.setText(Snamacg);
        tanggal.setText(Stglpesan);
        durasi.setText(Sdurasi);
        if(Spaket.equals("harian")){
            paket.setText("Hari");
        }else if (Spaket.equals("bulanan")){
            paket.setText("Bulan");
        }
        telepon.setText(Stelepon);
        alamat.setText(Salamat);
        deskripsi.setText(Sdeskripsi);
        Locale localeid = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeid);

        double totals = Double.parseDouble(Sgaji);

        gaji.setText(format.format((double) totals));

        ConstraintLayout.LayoutParams paramsa = (ConstraintLayout.LayoutParams) alamat.getLayoutParams();
        ConstraintLayout.LayoutParams paramsb = (ConstraintLayout.LayoutParams) deskripsi.getLayoutParams();

        if(Salamat.length() >= 32 && Salamat.length() < 64){
            paramsa.matchConstraintPercentHeight = (float) 0.075f;
            alamat.setLayoutParams(paramsa);
        }else if(Salamat.length() >= 64 && Salamat.length() < 95){
            paramsa.matchConstraintPercentHeight = (float) 0.11f;
            alamat.setLayoutParams(paramsa);
        }else if(Salamat.length() >= 95){
            paramsa.matchConstraintPercentHeight = (float) 0.145f;
            alamat.setLayoutParams(paramsa);
        }

        if(Sdeskripsi.length() >= 32 && Sdeskripsi.length() < 64){
            paramsb.matchConstraintPercentHeight = (float) 0.075f;
            deskripsi.setLayoutParams(paramsb);
        }else if(Sdeskripsi.length() >= 64 && Sdeskripsi.length() < 95){
            paramsb.matchConstraintPercentHeight = (float) 0.11f;
            deskripsi.setLayoutParams(paramsb);
        }else if(Sdeskripsi.length() >= 95 && Sdeskripsi.length() < 125){
            paramsb.matchConstraintPercentHeight = (float) 0.135f;
            deskripsi.setLayoutParams(paramsb);
        }else if(Sdeskripsi.length() >= 125) {
            paramsb.matchConstraintPercentHeight = (float) 0.175f;
            deskripsi.setLayoutParams(paramsb);
        }
    }

    public void check(){
        loading(true);
        apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<ResponseBody> call = apIinterface.getpesanan(Sid_pesan);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String a = jsonObject.getString("success");
                        JSONArray jsonArray = new JSONArray(a);
                        int i = 0;
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Rid_lansia = jsonObject2.getString("lansia_id");
                        Rid_cg = jsonObject2.getString("esccort_id");
                        Rnamacg = jsonObject2.getString("esccort_name");
                        Rnamalansia = jsonObject2.getString("lansia_name");
                        Rumurcg = jsonObject2.getString("age");
                        Rumurlansia = jsonObject2.getString("umur");
                        Rgendercg = jsonObject2.getString("esccort_gender");
                        Rgenderlansia = jsonObject2.getString("lansia_gender");
                        Rhobilansia = jsonObject2.getString("hobi");
                        Rsakitlansia = jsonObject2.getString("riwayat");
                        Rkeahliancg = jsonObject2.getString("keahlian");
                        Rgajicg = jsonObject2.getString("salary");
                        Ralamatcg = jsonObject2.getString("address");
                        Rratingcg = jsonObject2.getString("rating");

                        Log.d("TAG", "onResponse: " + jsonObject2);
                        getCgStatus();


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        finish();
                    }
                }else{
                    Log.d("TAG", "onResponse2: " + response.code());
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailPesananActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                Log.d("s", "onFailure: " + t);
                finish();
            }
        });
    }
    public void getCgStatus(){
        loading(true);
        APIinterface apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<ResponseBody> call = apIinterface.getstatus(Rid_cg);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TAG", "onResponse: " + response.code() + Rid_cg);
                if(response.code() == 200){
                    Rcgstatus = "unavailable";
                }else if (response.code() == 401){
                    Rcgstatus = "available";
                }else {
                    finish();
                }
                validate();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                finish();
            }
        });
    }
    public void validate(){
        loading(false);
        if(Sstatus.equals("belum")){
            if(Rcgstatus.equals("unavailable")) {
                infos.setVisibility(View.VISIBLE);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) infos.getLayoutParams();
                params.matchConstraintPercentHeight = (float) 0.07;
                infos.setLayoutParams(params);
                infos.setText("CareGiver ini sedang dalam pesanan lain.");
            }else if (Rcgstatus.equals("available")){
                bayar.setVisibility(View.VISIBLE);
            }
        }
    }
    public void loading(Boolean status){
        RelativeLayout prgbar;
        TextView titleTv;
        CardView cv;
        prgbar = findViewById(R.id.progressbar);
        titleTv = findViewById(R.id.tit);
        cv = findViewById(R.id.cv2);
        if(status){
            prgbar.setVisibility(View.VISIBLE);
            cv.setVisibility(View.INVISIBLE);
        }else {
            prgbar.setVisibility(View.INVISIBLE);
            cv.setVisibility(View.VISIBLE);
        }
    }
    public void Detaillansia(){
        Intent i = new Intent(DetailPesananActivity.this, DetailLansiaActivity.class);
        i.putExtra("nama", Rnamalansia);
        i.putExtra("umur", Rumurlansia);
        i.putExtra("gender",Rgenderlansia);
        i.putExtra("hobi", Rhobilansia);
        i.putExtra("riwayat", Rsakitlansia);
        startActivity(i);
    }
    public void profil(){
        Intent i = new Intent(DetailPesananActivity.this, DetailCGActivity.class);
        i.putExtra("id", Rid_cg);
        i.putExtra("urlgambar", Surlgambar);
        i.putExtra("nama" , Snamacg);
        i.putExtra("umur", Rumurcg);
        i.putExtra("kota", Ralamatcg);
        i.putExtra("keahlian", Rkeahliancg);
        i.putExtra("rating", Rratingcg);
        i.putExtra("gender", Rgendercg);
        i.putExtra("gaji", Rgajicg);
        i.putExtra("cgstatus", Rcgstatus);
        startActivity(i);
        finish();
    }
}