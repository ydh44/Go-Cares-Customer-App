package com.example.escort;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {
    private static final int PERMISIION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 0;
    private static final int IMAGE_PICK_CODE = 1;

    String currentPhotoPath, id, total, idcg;

    TextView totalTv, rkning;
    ImageButton back;
    Button TambahkanGambar, AmbilFoto;
    Uri gambar_uri = null;
    Boolean pil;
    Uri photoURI;
    File photoFile = null;
    ConstraintLayout constraintLayoutp, constraintLayout2;
    CardView card2;
    ProgressBar progress;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran);

        final int PERMISSION_CODE = 1000;
        TambahkanGambar = findViewById(R.id.btnGalerry);
        AmbilFoto = findViewById(R.id.btnCamera);
        totalTv = findViewById(R.id.total);
        back = findViewById(R.id.btnBack);
        rkning = findViewById(R.id.status);
        constraintLayoutp = findViewById(R.id.pembayaran);
        constraintLayout2 = findViewById(R.id.rekening);
        card2 = findViewById(R.id.card2);
        progress = findViewById(R.id.progress);

        rkning.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("rekening", rkning.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(PembayaranActivity.this, "Rekening Berhasil Disalin", Toast.LENGTH_SHORT ).show();
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(80);
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PembayaranActivity.this, PesananActivity.class);
                startActivity(i);
                finish();
            }

        });

        Intent i = getIntent();
        idcg = i.getStringExtra("idcg");
        id = i.getStringExtra("id");
        total = i.getStringExtra("total");

        getCgStatus();

        Locale localeid = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeid);

        double totals = Double.parseDouble(total);

        totalTv.setText(format.format((double) totals));


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
    public void onResume() {
        super.onResume();
        getCgStatus();
    }
    public void getCgStatus(){
        APIinterface apIinterface = APIClient.GetClient().create(APIinterface.class);
        Call<ResponseBody> call = apIinterface.getstatus(idcg);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TAG", "onResponse: "+ response.code() + idcg);
                if(response.code() == 200){
                    Toast.makeText(PembayaranActivity.this, "CareGiver dalam pesanan lain, jika Anda sudah melakukan pembayaran, silahkan hubungi admin di halaman kontak.", Toast.LENGTH_LONG).show();
                    finish();
                    Log.d("TAG", "onResponse1: ");
                }else if (response.code() == 401){
                   constraintLayout2.setVisibility(View.VISIBLE);
                   card2.setVisibility(View.VISIBLE);
                   progress.setVisibility(View.GONE);
                    Log.d("TAG", "onResponse2: ");
                }else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                finish();
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
                    i.putExtra("idcg", idcg);
                    i.putExtra("id", id);
                    i.putExtra("path", photoFile.getPath());
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
                    i.putExtra("idcg", idcg);
                    startActivity(i);
                    finish();
                }
                break;

            default:
                break;
        }
    }

}
