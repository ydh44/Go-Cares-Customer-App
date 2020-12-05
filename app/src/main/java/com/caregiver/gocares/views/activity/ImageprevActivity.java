package com.caregiver.gocares.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.caregiver.gocares.R;
import com.caregiver.gocares.repositories.GetStatusCg;
import com.caregiver.gocares.utils.SessionLog;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageprevActivity extends AppCompatActivity {

    Button upload;
    ImageView preview;
    RelativeLayout prgbar;

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

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        Log.d("TAG", "onCrsad: " + file + id);

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

        new GetStatusCg().GetStatusCg(this, idcg).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    up();
                }else {
                    SessionLog.SaveHomeMessage(ImageprevActivity.this, true);
                    finish();
                }
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
                        SessionLog.SaveHomeRefresh(ImageprevActivity.this, true);
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