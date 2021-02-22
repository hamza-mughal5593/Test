package com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
ImageView detail_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detail_img= findViewById(R.id.detail_img);

        Intent intent = getIntent();
        String id = intent.getStringExtra("image_urls");

        Glide
                .with(this)
                .load(id)
                .centerCrop()

                .into(detail_img);

    }
}