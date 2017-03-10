package com.example.pedro.jsonimg;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadImage extends AppCompatActivity {

    @BindView(R.id.ivFirst)
    ImageView ivFirst;

    @BindView(R.id.ivSecond)
    ImageView ivSecond;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        ButterKnife.bind(this);

        String imgURL = "http://gdgjampa.com/wp-content/uploads/2016/12/gdg-logo-300.png";

        context = getApplicationContext();

        //Picasso
        Picasso.with(context)
                .load(imgURL)
                .placeholder(R.drawable.placeholder)
                .into(ivFirst);

        //Glide
        Glide.with(context)
                .load(imgURL)
                .into(ivSecond);

    }


}
