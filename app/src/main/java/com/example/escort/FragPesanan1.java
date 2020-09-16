package com.example.escort;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragPesanan1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragPesanan1 extends Fragment implements Callback<JsonElement> {

    RecyclerView recyclerView;
    Pesanan1Adapter adapter;
    List<Pesanan1Data> items;
    Boolean err;
    APIinterface apIinterface;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String mParam1;
    public String mParam2;

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

    // /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pesanan_frag1, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);


        apIinterface = APIClient.GetClient().create(APIinterface.class);
        String idUserLogin;
        Call<JsonElement> call = apIinterface.getstatus_belum(
                idUserLogin = SessionLog.GetId(getContext())
        );
        Log.d("TAG", "onResponse: " );
        call.enqueue(FragPesanan1.this);

        return view;
    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.d(TAG, "onResponse: " + response.code());
        if (response.isSuccessful()){
            List<Pesanan1Data> data = null;
            try {
                JSONObject jsonObject = new JSONObject(response.body().toString());
                String b = jsonObject.getString("success");
                JSONArray jsonArray = new JSONArray(b);
                Log.d("TAG", "onResponse: " + b);
                data = new ArrayList<>();
                for (int i = 0 ; i < jsonArray.length(); i++){
                    JSONObject a = jsonArray.getJSONObject(i);
                    data.add(new Pesanan1Data(
                            "http://40.88.4.113/esccortPhotos/" +  a.getString("photo"),
                            a.getString("status"),
                            a.getString("idtrans"),
                            a.getString("idesccort"),
                            a.getString("lansia_id"),
                            a.getString("name"),
                            a.getString("order_time"),
                            a.getString("paket"),
                            a.getString("durasi"),
                            a.getString("deskripsi_kerja"),
                            a.getString("phone"),
                            a.getString("address"),
                            a.getString("total_bayar")
                    ));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new Pesanan1Adapter(getContext(), data);
                recyclerView.setAdapter(adapter);
                err = true;
                recyclerView.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
                err = false;
                recyclerView.setVisibility(View.GONE);
            }
        }else{
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}