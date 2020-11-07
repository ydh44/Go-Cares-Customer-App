package com.caregiver.gocares;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
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
    @BindView(R.id.btnNext) ImageButton next;
    @OnClick(R.id.btninfo)
    void bout(){
        Intent i = new Intent(DetailCGActivity.this, TentangActivity.class);
        int i1 = 2;
        i.putExtra("id", i1);
        startActivity(i);
    }
    ImageButton back;
    ImageView gambarcg;
    ProgressBar progressBar;
    TextView namaTv, kotaTv, genderTv, keahlianTv, cgstatusTv;
    String id;
    String cgstatus, cgstatuses;

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
        cgstatus = i.getStringExtra("cgstatus");

        Log.d("TAG", "onCreate: " + id);

        namaTv = findViewById(R.id.nama);
        kotaTv = findViewById(R.id.kota);
        genderTv = findViewById(R.id.gender);
        keahlianTv = findViewById(R.id.keahlian);
        back = findViewById(R.id.btnBack);
        gambarcg = findViewById(R.id.imageView);
        cgstatusTv = findViewById(R.id.statuscg);
        progressBar = findViewById(R.id.progress);

        ConstraintLayout.LayoutParams paramsa = (ConstraintLayout.LayoutParams) kotaTv.getLayoutParams();
        ConstraintLayout.LayoutParams paramsb = (ConstraintLayout.LayoutParams) keahlianTv.getLayoutParams();

        assert kota != null;
        if(kota.length() >= 32 && kota.length() < 64){
            paramsa.matchConstraintPercentHeight = (float) 0.075f;
            kotaTv.setLayoutParams(paramsa);
        }else if(kota.length() >= 64 && kota.length() < 95){
            paramsa.matchConstraintPercentHeight = (float) 0.11f;
            kotaTv.setLayoutParams(paramsa);
        }else if(kota.length() >= 95){
            paramsa.matchConstraintPercentHeight = (float) 0.145f;
            kotaTv.setLayoutParams(paramsa);
        }

        assert keahlian != null;
        if(keahlian.length() >= 32 && keahlian.length() < 64){
            paramsb.matchConstraintPercentHeight = (float) 0.075f;
            keahlianTv.setLayoutParams(paramsb);
        }else if(keahlian.length() >= 64 && keahlian.length() < 95){
            paramsb.matchConstraintPercentHeight = (float) 0.11f;
            keahlianTv.setLayoutParams(paramsb);
        }else if(keahlian.length() >= 95 && keahlian.length() < 125){
            paramsb.matchConstraintPercentHeight = (float) 0.145f;
            keahlianTv.setLayoutParams(paramsb);
        }else if(keahlian.length() >= 125) {
            paramsb.matchConstraintPercentHeight = (float) 0.175f;
            keahlianTv.setLayoutParams(paramsb);
        }

        //setText,setGambar
        namaTv.setText(nama);
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
    public void onResume() {
        super.onResume();
        getCgStatus();
    }
    public void getCgStatus(){
        APIinterface apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<ResponseBody> call = apIinterface.getstatus(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    cgstatuses = "unavailable";
                }else if (response.code() == 401){
                    cgstatuses = "available";
                }else {
                    cgstatuses = "error";
                }
                validate();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                cgstatuses = "error";
                validate();
            }
        });
    }
    public void validate(){
        progressBar.setVisibility(View.GONE);
        cgstatusTv.setVisibility(View.VISIBLE);
        if(!cgstatuses.equals(cgstatus)){
            if (cgstatuses.equals("unavailable")){
                cgstatusTv.setText("Tidak Tersedia");
                cgstatusTv.setTextColor(getResources().getColor(R.color.colorRed));
            }else if(cgstatuses.equals("available")){
                cgstatusTv.setText("Tersedia");
                cgstatusTv.setTextColor(getResources().getColor(R.color.colorGreen));
                next.setVisibility(View.VISIBLE);
            }else{
                finish();
            }
            MainActivity.reload = "reload";
        }else {
            if (cgstatus.equals("unavailable")){
                cgstatusTv.setText("Tidak Tersedia");
                cgstatusTv.setTextColor(getResources().getColor(R.color.colorRed));
            }else if(cgstatus.equals("available")){
                cgstatusTv.setText("Tersedia");
                cgstatusTv.setTextColor(getResources().getColor(R.color.colorGreen));
                next.setVisibility(View.VISIBLE);
            }
        }
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
            cv.setVisibility(View.INVISIBLE);
        }else {
            prgbar.setVisibility(View.INVISIBLE);
            cv.setVisibility(View.VISIBLE);
        }
    }

}
