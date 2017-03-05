package com.example.pedro.jsonimg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class LoadImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        String imgURL = "http://gdgjampa.com/wp-content/uploads/2016/12/gdg-logo-300.png";

        //Picasso
        ImageView ivFirst = (ImageView) findViewById(R.id.ivFirst);
        Picasso.with(ivFirst.getContext())
                .load(imgURL)
                .placeholder(R.drawable.placeholder)
                .into(ivFirst);

        //Glide
        ImageView ivSecond = (ImageView) findViewById(R.id.ivSecond);
        Glide.with(ivSecond.getContext())
                .load(imgURL)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(ivSecond);
    }


}
