package com.caregiver.gocares.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.caregiver.gocares.R;
import com.caregiver.gocares.models.Cg;
import com.caregiver.gocares.utils.Card;
import com.caregiver.gocares.views.activity.TransaksiActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CgAdapter extends RecyclerView.Adapter<CgAdapter.ViewHolder> {

    public LayoutInflater layoutInflater;
    public List<Cg> Data;
    public Context contexts;
    public ViewGroup viewGroup;


    public CgAdapter(Context context, List<Cg> cg, ViewGroup viewGroup){
        contexts = context;
        this.Data = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.Data = cg;
        this.viewGroup = viewGroup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.card , viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Cg cg = Data.get(i);
        ViewHolder viewHolder1 = viewHolder;
        viewHolder1.nama.setText(cg.getName());
        viewHolder1.gender.setText(cg.getGender());
        viewHolder1.kota.setText(cg.getAddress());
        viewHolder1.keahlian.setText(cg.getKeahlian());
        viewHolder1.rating.setText(cg.getRating());
        Picasso.get().load(cg.getPhoto()).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(viewHolder1.foto);

        viewHolder1.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexts, TransaksiActivity.class);
                intent.putExtra("id_cg", cg.getId());
                contexts.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView foto;
        TextView nama, gender, kota, keahlian, rating;
        ConstraintLayout action;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.fotocg);
            nama = itemView.findViewById(R.id.namacgTv);
            gender = itemView.findViewById(R.id.status1Tv);
            kota = itemView.findViewById(R.id.tanggal1Tv);
            keahlian = itemView.findViewById(R.id.keahlianTv);
            rating = itemView.findViewById(R.id.ratingcgTv);
            action = itemView.findViewById(R.id.actionBtn);

            LinearLayout expandableView_Cardviews = itemView.findViewById(R.id.expandableView);
            ConstraintLayout detailBtns = itemView.findViewById(R.id.detailBtn);
            TextView detailTvs = itemView.findViewById(R.id.detailTv);
            itemView.findViewById(R.id.view1).setVisibility(View.GONE);
            itemView.findViewById(R.id.view2).setVisibility(View.GONE);
            itemView.findViewById(R.id.view3).setVisibility(View.GONE);
            itemView.findViewById(R.id.view4).setVisibility(View.GONE);
            itemView.findViewById(R.id.detailpesananHead).setVisibility(View.GONE);
            itemView.findViewById(R.id.detaillansiaHead).setVisibility(View.GONE);
            itemView.findViewById(R.id.detailulasanHead).setVisibility(View.GONE);
            TextView action = itemView.findViewById(R.id.actionTv);
            action.setText("Pesan Sekarang");
            new Card(
                    null, null, null,
                    expandableView_Cardviews, null, null, null,
                    detailBtns, detailTvs, null, null, null, viewGroup
            );

        }

    }

}
