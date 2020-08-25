package com.example.escort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CGAdapter extends RecyclerView.Adapter<CGAdapter.ViewHolder> {
    List<CG> CGData = new ArrayList<>();
    Context context;

    public CGAdapter(Context context){
        this.context = context;

    }
    public void setCGData(List<CG>CGData) {
        this.CGData = CGData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout=R.layout.home_card;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CG cg =CGData.get(position);
        holder.cgDeatials(cg);

    }

    @Override
    public int getItemCount() {
        return CGData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cgnameEt, citynameEt, cgumurEt, cgratingEt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cgnameEt = itemView.findViewById(R.id.CgnameEt);
            citynameEt = itemView.findViewById(R.id.CitynameEt);
            cgumurEt = itemView.findViewById(R.id.CgumurEt);
            cgratingEt = itemView.findViewById(R.id.CgratingEt);
        }
        public void cgDeatials(CG cg) {
            String cgname = cg.getCgname();
            String cityname = cg.getCityname();
            String cgumur = cg.getCgumur();
            String cgrating = cg.getCgrating();
            cgnameEt.setText(cgname);
            citynameEt.setText(cityname);
            cgumurEt.setText(cgumur);
            cgratingEt.setText(cgrating);
        }
    }
}


