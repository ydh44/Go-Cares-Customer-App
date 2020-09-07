package com.example.escort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageprevActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepreview);

        ImageView preview;

        preview = findViewById(R.id.cv2);

        Intent i = getIntent();
        Bundle imgs = i.getBundleExtra("img");

        Bitmap imgg = (Bitmap) imgs.get("data");

        preview.setImageBitmap(imgg);
    }
}