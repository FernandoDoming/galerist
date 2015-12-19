package me.fernandodominguez.galerist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import me.fernandodominguez.galerist.R;
import me.fernandodominguez.pixels.FiveHundredPxClient;
import me.fernandodominguez.pixels.models.Image;
import me.fernandodominguez.pixels.models.PhotoResponse;
import me.fernandodominguez.pixels.models.PhotosResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ImageActivity extends AppCompatActivity {

    ImageView headerImage;
    TextView imageTitle;
    TextView imageAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Image image = (Image) intent.getSerializableExtra("image");
        headerImage = (ImageView) findViewById(R.id.big_image);
        imageTitle  = (TextView) findViewById(R.id.image_title);
        imageAuthor = (TextView) findViewById(R.id.image_author);

        FiveHundredPxClient client = new FiveHundredPxClient("KdNLCVlyyYzXUajMBm11VE38FZG7TBJjed1HRJCN");
        client.getBigImage(image.getId(), new Callback<PhotoResponse>() {
            @Override
            public void success(PhotoResponse photoResponse, Response response) {
                Image img = photoResponse.getPhoto();
                Ion.with(headerImage)
                        //.placeholder(R.drawable.download)
                        .error(R.drawable.broken_link)
                        .load(img.getImageUrl());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("[ImageActivity]","Error loading the image");
            }
        });

        imageTitle.setText(image.getName());
        //imageAuthor.setText(image.getUser_id());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
