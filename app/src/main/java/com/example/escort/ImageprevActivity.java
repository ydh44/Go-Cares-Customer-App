package com.example.escort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageprevActivity extends AppCompatActivity {

    APIinterface apIinterface;
    Button upload;
    ImageView preview;
    RelativeLayout prgbar;
    @OnClick(R.id.btnBack)
            void pindahs(){
        finish();
    }

    String id, path, idcg;

    Uri img;

    File file;
    ConstraintLayout constraintLayout;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepreview);
        ButterKnife.bind(this);

        upload = findViewById(R.id.btnUp);
        preview = findViewById(R.id.cv2);
        prgbar = findViewById(R.id.progressbar);
        constraintLayout = findViewById(R.id.cons);

        Intent i = getIntent();
        String imgs = i.getStringExtra("image");
        path = i.getStringExtra("path");
        id = i.getStringExtra("id");
        idcg = i.getStringExtra("idcg");
        img = Uri.parse(imgs);

        if(path!=null){
            OutputStream os;
            File filesDir = getApplicationContext().getFilesDir();
            file = new File(filesDir, Math.random() + ".jpg");
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.flush();
                os.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            OutputStream os;
            File filesDir = getApplicationContext().getFilesDir();
            file = new File(filesDir, Math.random() + ".jpg");
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
                os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.flush();
                os.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("TAG", "onCreate: " + file + id);

        Glide.with(this).load(img).into(preview);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCgStatus();
            }
        });

    }
    public void getCgStatus(){
        prgbar.setVisibility(View.VISIBLE);
        APIinterface apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<ResponseBody> call = apIinterface.getstatus(idcg);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Toast.makeText(ImageprevActivity.this, "CareGiver dalam pesanan lain, jika Anda sudah melakukan pembayaran, silahkan hubungi admin di halaman kontak.", Toast.LENGTH_LONG).show();
                    finish();
                }else if (response.code() == 401){
                    up();
                }else {
                    Toast.makeText(ImageprevActivity.this, "CareGiver dalam pesanan lain, jika Anda sudah melakukan pembayaran, silahkan hubungi admin di halaman kontak.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                finish();
            }
        });
    }
    public void up(){
        AndroidNetworking.upload("http://40.88.4.113/api/uploadbukti")
                .addMultipartFile("bukti_foto",file)
                .addMultipartParameter("id", id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onResponse: " + response.toString());
                        Intent i = new Intent(ImageprevActivity.this, PesananActivity.class);
                        startActivity(i);
                        finish();
                        prgbar.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("TAG", "onResponse: " + error);
                        prgbar.setVisibility(View.INVISIBLE);
                        // handle error
                    }
                });
    }
    private File savebitmap(Bitmap bmp) {
        OutputStream outStream = null;
        // String temp = null;
        File filesDir = getFilesDir();
        File files = new File(filesDir, Math.random() + ".jpg");
        try {
            outStream = new FileOutputStream(files);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return files;
    }
}