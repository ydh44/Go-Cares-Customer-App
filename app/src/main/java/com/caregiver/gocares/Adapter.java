package com.caregiver.gocares;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    protected LayoutInflater layoutInflater;
    List<CGdata> Data = new ArrayList<>();
    Context contexts;


    Adapter(Context context, List<CGdata>Data){
        contexts = context;
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
        viewHolder.DataDetail(cgdata, viewHolder);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle,textDescription, text3, text4;
        ImageView gambarcg;
        HashMap<String, String> hm;
        RelativeLayout bg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pindah(v);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Vibrator vibrator = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(80);
                    pindah(v);
                    return false;
                }
            });
            gambarcg = itemView.findViewById(R.id.imageView);
            textTitle = itemView.findViewById(R.id.CgNameEt);
            textDescription = itemView.findViewById(R.id.CgCityNameEt);
            text3 = itemView.findViewById(R.id.CgUmurEt);
            text4 = itemView.findViewById(R.id.CgRattingEt);
            bg = itemView.findViewById(R.id.bg);
        }

        protected void pindah(View v){
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
            i.putExtra("cgstatus",hm.get("cgstatus"));
            v.getContext().startActivity(i);
        }

        protected HashMap<String, String> DataDetail( @NonNull  CGdata cgdata, ViewHolder viewHolder) {
            String id = cgdata.getId();
            String urlgambar = cgdata.getUrlgambar();
            String nama = cgdata.getNama();
            String gaji = cgdata.getGaji();
            String gender = cgdata.getGender();
            String umur = cgdata.getUmur();
            String kota = cgdata.getKota();
            String keahlian = cgdata.getKeahlian();
            String rating = cgdata.getRating();
            String cgstatus = cgdata.getCgstatus();
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
            hm.put("cgstatus", cgstatus);

            Log.d("TAG", "DataDetail: " +urlgambar);
            Picasso.get().load(urlgambar).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(gambarcg);
            textTitle.setText(nama);
            text3.setText(keahlian);
            if(gender.equalsIgnoreCase("L")){
                textDescription.setText("Laki - laki");
            }else if(gender.equalsIgnoreCase("P")){
                textDescription.setText("Perempuan");
            }else{
                textDescription.setText("-");
            }

            if(cgstatus.equals("available")){
                text4.setText("Tersedia");
                bg.setBackgroundResource(R.drawable.backbluebig);
                viewHolder.text4.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.white));
            }else {
                text4.setText("Tidak Tersedia");
                if(cgstatus.equals("unavailable")){
                    bg.setBackgroundResource(R.drawable.backbluebig1);
                    viewHolder.text4.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.white));
                }
            }

            return hm;
        }
    }

}
