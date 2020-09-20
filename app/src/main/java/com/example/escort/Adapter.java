package com.example.escort;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<CGdata> Data = new ArrayList<>();


    Adapter(Context context, List<CGdata>Data){
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CGdata cgdata = Data.get(i);
        viewHolder.DataDetail(cgdata);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle,textDescription, text3, text4;
        ImageView gambarcg;
        HashMap<String, String> hm;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),DetailCGActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id",hm.get("id"));
                    i.putExtra("urlgambar",hm.get("urlgambar"));
                    i.putExtra("nama",hm.get("nama"));
                    i.putExtra("umur",hm.get("umur"));
                    i.putExtra("gender",hm.get("gender"));
                    i.putExtra("keahlian" , hm.get("keahlian"));
                    i.putExtra("gaji",hm.get("gaji"));
                    i.putExtra("kota",hm.get("kota"));
                    i.putExtra("rating",hm.get("rating"));
                    v.getContext().startActivity(i);
                }
            });
            gambarcg = itemView.findViewById(R.id.imageView);
            textTitle = itemView.findViewById(R.id.CgNameEt);
            textDescription = itemView.findViewById(R.id.CgCityNameEt);
            text3 = itemView.findViewById(R.id.CgUmurEt);
            text4 = itemView.findViewById(R.id.CgRattingEt);
        }

        protected HashMap<String, String> DataDetail( @NonNull  CGdata cgdata) {
            String id = cgdata.getId();
            String urlgambar = cgdata.getUrlgambar();
            String nama = cgdata.getNama();
            String gaji = cgdata.getGaji();
            String gender = cgdata.getGender();
            String umur = cgdata.getUmur();
            String kota = cgdata.getKota();
            String keahlian = cgdata.getKeahlian();
            String rating = cgdata.getRating();
            hm = new HashMap<>();
            hm.put("id", id);
            hm.put("urlgambar", urlgambar);
            hm.put("nama", nama);
            hm.put("umur", umur);
            hm.put("gender", gender);
            hm.put("gaji", gaji);
            hm.put("kota", kota);
            hm.put("keahlian", keahlian);
            hm.put("rating", rating);

            Log.d("TAG", "DataDetail: " +urlgambar);
            Picasso.get().load(urlgambar).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(gambarcg);
            textTitle.setText(nama);
            if(gender.equals("L")){
                textDescription.setText("Laki - laki");
            }else if(gender.equals("P")){
                textDescription.setText("Perempuan");
            }else{
                textDescription.setText("-");
            }
            text3.setText(umur + " Tahun");

            return hm;
        }
    }

}
