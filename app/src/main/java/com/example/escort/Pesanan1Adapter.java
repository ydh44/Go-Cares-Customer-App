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
                    i.putExtra("namacg",hm.get("namacg"));
                    i.putExtra("tglpesan",hm.get("tglpesan"));
                    i.putExtra("deskripsi",hm.get("gender"));
                    i.putExtra("gaji",hm.get("gaji"));
                    i.putExtra("status",hm.get("status"));
                    v.getContext().startActivity(i);
                }
            });
            gambarcg = itemView.findViewById(R.id.imageView);
            namacgview =itemView.findViewById(R.id.CgNameEt);
            txt = itemView.findViewById(R.id.CgCityNameEt);
            tglpesanview = itemView.findViewById(R.id.CgUmurEt);

        }

        protected HashMap<String, String> DataDetail2( @NonNull  Pesanan1Data pesananData) {
            String namacg = pesananData.getNamacg();
            String tglpesan = pesananData.getTglpesan();
            String deskripsi = pesananData.getDeskripsi();
            String gaji = pesananData.getGaji();
            String status = pesananData.getStatus();
            String urlgambar = pesananData.getPhoto();
            hm = new HashMap<>();
            hm.put("namacg", namacg);
            hm.put("tglpesan", tglpesan);
            hm.put("deskripsi", deskripsi);
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
