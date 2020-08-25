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

public class FragmentPesananAdapter extends RecyclerView.Adapter<FragmentPesananAdapter.ViewHolder> {
    List<FragmentPesanan> fragmentpesanansData = new ArrayList<>();
    Context context;

    public FragmentPesananAdapter(Context context){
        this.context = context;

    }
    public void setFragmentpesananData(List<FragmentPesanan>fragmentpesanansData) {
        this.fragmentpesanansData = fragmentpesanansData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout=R.layout.fragpesanan_card;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FragmentPesanan fragmentPesanan =fragmentpesanansData.get(position);
        holder.fragmentpesananDeatials(fragmentPesanan);

    }

    @Override
    public int getItemCount() {
        return fragmentpesanansData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cgnamepesananEt, tanggalEt, tanggalpesananEt, waitconfirmEt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cgnamepesananEt = itemView.findViewById(R.id.cgnamepesananEt);
            tanggalEt = itemView.findViewById(R.id.tanggalEt);
            tanggalpesananEt = itemView.findViewById(R.id.tanggalpesananEt);
            waitconfirmEt = itemView.findViewById(R.id.konfirmEt);
        }
        public void fragmentpesananDeatials(FragmentPesanan fragmentPesanan) {
            String cgnamepesanan = fragmentPesanan.getCgpesananname();
            String tanggal = fragmentPesanan.getTanggal();
            String tanggalpesanan =  fragmentPesanan.getTanggalpesanan();
            String  waitconfirm = fragmentPesanan.getWaitconfirm();
            cgnamepesananEt.setText(cgnamepesanan);
            tanggalEt.setText(tanggal);
            tanggalpesananEt.setText(tanggalpesanan);
            waitconfirmEt.setText(waitconfirm);

        }
    }
}

