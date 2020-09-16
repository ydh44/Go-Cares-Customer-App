package com.example.escort;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCGActivity extends AppCompatActivity {
    APIinterface apIinterface;
    @OnClick(R.id.btnNext)
            void pesans(){
        pesan();
    }
    ImageButton back;
    ImageView gambarcg;
    Guideline hor1, hor2, hor4, hor5;
    TextView namaTv, umurTv, kotaTv, genderTv, keahlianTv,  gajiTv, ratingTv;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailcg);
        ButterKnife.bind(this);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        String urlgambar = i.getStringExtra("urlgambar");
        String nama = i.getStringExtra("nama");
        String umur = i.getStringExtra("umur");
        String kota = i.getStringExtra("kota");
        String keahlian = i.getStringExtra("keahlian");
        String rating = i.getStringExtra("rating");
        String gender = i.getStringExtra("gender");
        String gaji = i.getStringExtra("gaji");

        namaTv = findViewById(R.id.nama);
        kotaTv = findViewById(R.id.kota);
        umurTv = findViewById(R.id.umur);
        genderTv = findViewById(R.id.gender);
        gajiTv = findViewById(R.id.gaji);
        keahlianTv = findViewById(R.id.keahlian);
        back = findViewById(R.id.btnBack);
        gambarcg = findViewById(R.id.imageView);
        hor1 = findViewById(R.id.cdhor18);
        hor2 = findViewById(R.id.cdhor11);
        hor4 = findViewById(R.id.cdhor15);
        hor5 = findViewById(R.id.cdhor19);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) hor1.getLayoutParams();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) hor2.getLayoutParams();
        ConstraintLayout.LayoutParams params4 = (ConstraintLayout.LayoutParams) hor4.getLayoutParams();
        ConstraintLayout.LayoutParams params5 = (ConstraintLayout.LayoutParams) hor5.getLayoutParams();

        //guideline
        assert kota != null;
        if(kota.length() >= 30){
            params5.guidePercent = 0.625f;
            params4.guidePercent = 0.65f;
            hor5.setLayoutParams(params5);
            hor4.setLayoutParams(params4);
            assert keahlian != null;
            if(keahlian.length() >= 30){
                params.guidePercent = 0.725f;
                params2.guidePercent = 0.75f;
                hor1.setLayoutParams(params);
                hor2.setLayoutParams(params2);
            }else {
                params.guidePercent = 0.69f;
                hor1.setLayoutParams(params);
            }
        }else{
            assert keahlian != null;
            if(keahlian.length() >= 30){
                params.guidePercent = 0.69f;
                hor1.setLayoutParams(params);
            }else {
                params2.guidePercent = 0.68f;
                hor2.setLayoutParams(params2);
            }
        }

        //setText,setGambar
        namaTv.setText(nama);
        umurTv.setText(umur + " Tahun");
        assert gender != null;
        if(gender.equals("L")){
            genderTv.setText("Laki-laki");
        }else if(gender.equals("P")){
            genderTv.setText("Perempuan");
        }else {
            genderTv.setText("-");
        }
        keahlianTv.setText(keahlian);
        kotaTv.setText(kota);

        Picasso.get().load(urlgambar).placeholder(R.drawable.loadingfoto).error(R.drawable.profilecg).into(gambarcg);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
    }
    public void pesan(){
        loading(true);
        apIinterface = APIClient.GetClient().create(APIinterface.class);
        String idUserLogin = SessionLog.GetId(DetailCGActivity.this);
        Call<ResponseBody> call = apIinterface.loadLansia(idUserLogin);

        Log.d("TAG", "pesan: " + SessionLog.GetId(DetailCGActivity.this));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String a = jsonObject.getString("success");
                        JSONObject jsonObject2 = new JSONObject(a);
                        String lansia_id = jsonObject2.getString("id");
                        String nama = jsonObject2.getString("nama");
                        String umur = jsonObject2.getString("umur");
                        String gender = jsonObject2.getString("gender");
                        String hobi = jsonObject2.getString("hobi");
                        String sakit = jsonObject2.getString("riwayat");

                        Log.d("TAG", "onResponse: " + a);


                        Intent i = new Intent(DetailCGActivity.this, CheckoutActivity.class);
                        i.putExtra("status", "there");
                        i.putExtra("id", id);
                        i.putExtra("lansia_id", lansia_id);
                        i.putExtra("nama", nama);
                        i.putExtra("umur", umur);
                        i.putExtra("gender", gender);
                        i.putExtra("hobi", hobi);
                        i.putExtra("sakit", sakit);
                        startActivity(i);
                        loading(false);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();

                        Intent i = new Intent(DetailCGActivity.this, CheckoutActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("status", "none");
                        i.putExtra("id", id);
                        startActivity(i);
                        loading(false);
                    }
                }else{
                    Log.d("TAG", "onResponse2: " + response.code());
                    loading(false);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailCGActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                Log.d("s", "onFailure: " + t);
                loading(false);
            }
        });
    }
    public void loading(Boolean status){
        RelativeLayout prgbar;
        TextView titleTv;
        CardView cv;
        prgbar = findViewById(R.id.progressbar);
        titleTv = findViewById(R.id.tit);
        cv = findViewById(R.id.cv2);
        if(status){
            prgbar.setVisibility(View.VISIBLE);
            titleTv.setVisibility(View.GONE);
            cv.setVisibility(View.INVISIBLE);
        }else {
            prgbar.setVisibility(View.INVISIBLE);
            titleTv.setVisibility(View.VISIBLE);
            cv.setVisibility(View.VISIBLE);
        }
    }

}
