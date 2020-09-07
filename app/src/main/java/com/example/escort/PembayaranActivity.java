package com.example.escort;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PembayaranActivity extends AppCompatActivity {
    private static final int PERMISIION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1002;

    Button TambahkanGambar, AmbilFoto;
    Uri gambar_uri;
    Boolean pil;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran);

        final int PERMISSION_CODE = 1000;
        TambahkanGambar = findViewById(R.id.btnGalerry);
        AmbilFoto = findViewById(R.id.btnCamera);

        //btn click
        AmbilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if system os is >= marshmallow , request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ) {
                        //permission not enabled , request it
                        String[] permission = new String[]{Manifest.permission.CAMERA};
                        //show popup to request permission
                        requestPermissions(permission, PERMISSION_CODE);
                        pil = true;

                    } else {
                        //permission already granted
                        openCamera();

                    }
                } else {
                    //system os < marshmallow
                    openCamera();
                }

            }
        });
        TambahkanGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it
                        String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permission, PERMISSION_CODE);
                        pil = false;
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();

                }

                }
        });

    }

    private void pickImageFromGallery() {
        // intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Gambar Baru");
        values.put(MediaStore.Images.Media.TITLE, "Dari Kamera");
        gambar_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //intent camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, gambar_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }



    //handling permission result

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //this method called , when user presses Allow or Deny from permission request popup
        switch (requestCode) {
            case PERMISIION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    if(pil){
                        openCamera();
                    }else {
                        pickImageFromGallery();
                    }
                    //permission form popup was granted
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
