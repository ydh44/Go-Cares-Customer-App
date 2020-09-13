package com.example.escort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageprevActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepreview);

        ImageView preview;

        preview = findViewById(R.id.cv2);

        Intent i = getIntent();
        String imgs = i.getStringExtra("image");
        Uri img = Uri.parse(imgs);
        Log.d("TAG", "onCreate: " + img);

        Glide.with(this).load(img).into(preview);
    }
}