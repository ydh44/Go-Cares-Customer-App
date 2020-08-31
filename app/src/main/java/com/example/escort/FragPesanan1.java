package com.example.escort;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragPesanan1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragPesanan1 extends Fragment {

    RecyclerView recyclerView;
    Pesanan1Adapter adapter;
    List<Pesanan1Data> items;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragPesanan1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragPesanan1Activity.
     */
    // TODO: Rename and change types and number of parameters
    public static FragPesanan1 newInstance(String param1, String param2) {

        FragPesanan1 fragment = new FragPesanan1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pesanan_frag1, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        items = new ArrayList<>();
        items.add(new Pesanan1Data("cg1", "2 Maret 2019" , "test" , "2jt", "belum bayar"));
        items.add(new Pesanan1Data("cg2", "5 Maret 2019" , "test" , "4jt", "belum bayar"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Pesanan1Adapter(getContext(), items);
        recyclerView.setAdapter(adapter);
        return view;
    }
}