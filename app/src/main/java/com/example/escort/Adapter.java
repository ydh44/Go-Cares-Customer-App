package com.example.escort;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        HashMap<String, String> hm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),DetailCGActivity.class);
                    i.putExtra("nama",hm.get("nama"));
                    i.putExtra("umur",hm.get("umur"));
                    i.putExtra("gender",hm.get("gender"));
                    i.putExtra("gaji",hm.get("gaji"));
                    i.putExtra("kota",hm.get("kota"));
                    i.putExtra("rating",hm.get("rating"));
                    v.getContext().startActivity(i);
                }
            });
            textTitle = itemView.findViewById(R.id.CgNameEt);
            textDescription = itemView.findViewById(R.id.CgCityNameEt);
            text3 = itemView.findViewById(R.id.CgUmurEt);
            text4 = itemView.findViewById(R.id.CgRattingEt);
        }

        protected HashMap<String, String> DataDetail( @NonNull  CGdata cgdata) {
            String nama = cgdata.getNama();
            String gaji = cgdata.getGaji();
            String gender = cgdata.getGender();
            String umur = cgdata.getUmur();
            String kota = cgdata.getKota();
            String rating = cgdata.getRating();
            hm = new HashMap<>();
            hm.put("nama", nama);
            hm.put("umur", umur);
            hm.put("gender", gender);
            hm.put("gaji", gaji);
            hm.put("kota", kota);
            hm.put("rating", rating);
            textTitle.setText(nama);
            textDescription.setText(kota);
            text3.setText(umur);
            text4.setText(rating);
            return hm;
        }
    }

}
