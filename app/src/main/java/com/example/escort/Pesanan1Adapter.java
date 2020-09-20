package com.example.escort;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pesanan1Adapter extends RecyclerView.Adapter<Pesanan1Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<Pesanan1Data> Data = new ArrayList<>();




    Pesanan1Adapter(Context context, List<Pesanan1Data>Data){
        this.layoutInflater = LayoutInflater.from(context);
        this.Data = Data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.home_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder1, int i) {


        Pesanan1Data pesananData = Data.get(i);
        viewHolder1.DataDetail2(pesananData);



    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView namacgview,tglpesanview,txt;
        ImageView gambarcg;
        HashMap<String, String> hm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),DetailPesananActivity.class);
                    i.putExtra("urlgambar",hm.get("urlgambar"));
                    i.putExtra("status",hm.get("status"));
                    i.putExtra("id_pesan",hm.get("id_pesan"));
                    i.putExtra("namacg",hm.get("namacg"));
                    i.putExtra("tglpesan",hm.get("tglpesan"));
                    i.putExtra("paket",hm.get("paket"));
                    i.putExtra("durasi",hm.get("durasi"));
                    i.putExtra("deskripsi",hm.get("deskripsi"));
                    i.putExtra("telepon",hm.get("telepon"));
                    i.putExtra("alamat", hm.get("alamat"));
                    i.putExtra("gaji",hm.get("gaji"));
                    v.getContext().startActivity(i);
                }
            });
            gambarcg = itemView.findViewById(R.id.imageView);
            namacgview =itemView.findViewById(R.id.CgNameEt);
            txt = itemView.findViewById(R.id.CgCityNameEt);
            tglpesanview = itemView.findViewById(R.id.CgUmurEt);

        }

        protected HashMap<String, String> DataDetail2( @NonNull  Pesanan1Data pesananData) {
            String urlgambar = pesananData.getPhoto();
            String status = pesananData.getStatus();
            String id_pesan = pesananData.getId_pesan();
            String namacg = pesananData.getNamacg();
            String tglpesan = pesananData.getTglpesan();
            String paket =pesananData.getPaket();
            String durasi = pesananData.getDurasi();
            String deskripsi = pesananData.getDeskripsi();
            String telepon = pesananData.getTelepon();
            String alamat = pesananData.getAlamat();
            String gaji = pesananData.getGaji();


            hm = new HashMap<>();
            hm.put("urlgambar", urlgambar);
            hm.put("status", status);
            hm.put("id_pesan", id_pesan);
            hm.put("namacg", namacg);
            hm.put("tglpesan", tglpesan);
            hm.put("paket", paket);
            hm.put("durasi", durasi);
            hm.put("deskripsi", deskripsi);
            hm.put("telepon", telepon);
            hm.put("alamat",alamat);
            hm.put("gaji", gaji);
            namacgview.setText(namacg);
            tglpesanview.setText(tglpesan);
            txt.setText("Tanggal Pesanan");
            Log.d("TAG", "DataDetail: " +urlgambar);
            Picasso.get().load(urlgambar).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(gambarcg);
            return hm;
        }
    }

}
