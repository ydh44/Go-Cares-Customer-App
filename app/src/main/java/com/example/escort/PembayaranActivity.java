package com.example.escort;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class PembayaranActivity extends AppCompatActivity {
    private static final int PERMISIION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 0;
    private static final int IMAGE_PICK_CODE = 1;

    String currentPhotoPath, id, total;

    TextView totalTv;
    ImageButton back;
    Button TambahkanGambar, AmbilFoto;
    Uri gambar_uri = null;
    Boolean pil;
    ImageView test;
    Uri photoURI;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran);

        final int PERMISSION_CODE = 1000;
        TambahkanGambar = findViewById(R.id.btnGalerry);
        AmbilFoto = findViewById(R.id.btnCamera);
        test = findViewById(R.id.test);
        totalTv = findViewById(R.id.total);
        back = findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PembayaranActivity.this, PesananActivity.class);
                startActivity(i);
                finish();
            }

        });

        Intent i = getIntent();
        id = i.getStringExtra("id");
        total = i.getStringExtra("total");

        totalTv.setText("Rp."+total);

        if(gambar_uri != null){
            test.setImageURI(gambar_uri);
        }

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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE_CODE);
            }

        }
    }
    File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir/* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
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

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    String uri = photoURI.toString();
                    Intent i = new Intent(PembayaranActivity.this, ImageprevActivity.class);
                    i.putExtra("image", uri);
                    i.putExtra("id", id);
                    startActivity(i);
                    finish();
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    final Uri imageUri = data.getData();
                    assert imageUri != null;
                    String uri = imageUri.toString();
                    Intent i = new Intent(PembayaranActivity.this, ImageprevActivity.class);
                    i.putExtra("image", uri);
                    i.putExtra("id", id);
                    startActivity(i);
                    finish();
                }
                break;

            default:
                break;
        }
    }

}
