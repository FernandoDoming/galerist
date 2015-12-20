package me.fernandodominguez.galerist.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ImageViewFuture;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.fernandodominguez.galerist.R;
import me.fernandodominguez.galerist.helpers.StringsHelper;
import me.fernandodominguez.pixels.FiveHundredPxClient;
import me.fernandodominguez.pixels.models.Image;
import me.fernandodominguez.pixels.models.PhotoResponse;
import me.fernandodominguez.pixels.models.PhotosResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ImageActivity extends AppCompatActivity {

    Context context;

    Image image;
    Bitmap imageBitmap;

    ImageView headerImage;
    TextView imageTitle;
    TextView imageAuthor;
    LinearLayout detailsContainer;

    ImageView vibrantSwatch;
    ImageView vibrantLightSwatch;
    ImageView vibrantDarkSwatch;
    ImageView mutedSwatch;
    ImageView mutedLightSwatch;
    ImageView mutedDarkSwatch;

    Palette.PaletteAsyncListener paletteListener = new Palette.PaletteAsyncListener() {
        public void onGenerated(Palette palette) {
            int defaultColor = 0x000000;

            vibrantSwatch.setBackgroundColor(palette.getVibrantColor(defaultColor));
            vibrantLightSwatch.setBackgroundColor(palette.getLightVibrantColor(defaultColor));
            vibrantDarkSwatch.setBackgroundColor(palette.getDarkVibrantColor(defaultColor));
            mutedSwatch.setBackgroundColor(palette.getMutedColor(defaultColor));
            mutedLightSwatch.setBackgroundColor(palette.getLightMutedColor(defaultColor));
            mutedDarkSwatch.setBackgroundColor(palette.getDarkMutedColor(defaultColor));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();
        image       = (Image) intent.getSerializableExtra("image");
        headerImage = (ImageView) findViewById(R.id.big_image);
        imageTitle  = (TextView) findViewById(R.id.image_title);
        imageAuthor = (TextView) findViewById(R.id.image_author);

        vibrantSwatch      = (ImageView) findViewById(R.id.swatch_vibrant);
        vibrantLightSwatch = (ImageView) findViewById(R.id.swatch_vibrant_light);
        vibrantDarkSwatch  = (ImageView) findViewById(R.id.swatch_vibrant_dark);
        mutedSwatch        = (ImageView) findViewById(R.id.swatch_muted);
        mutedLightSwatch   = (ImageView) findViewById(R.id.swatch_muted_light);
        mutedDarkSwatch    = (ImageView) findViewById(R.id.swatch_muted_dark);

        FiveHundredPxClient client = new FiveHundredPxClient("KdNLCVlyyYzXUajMBm11VE38FZG7TBJjed1HRJCN");
        client.getBigImage(image.getId(), new Callback<PhotoResponse>() {
            @Override
            public void success(PhotoResponse photoResponse, Response response) {
                Image img = photoResponse.getPhoto();

                /*
                ImageViewFuture bitmap = Ion.with(context)
                        .load(img.getImageUrl())
                        .withBitmap()
                        //.placeholder(R.drawable.placeholder_image)
                        //.error(R.drawable.broken_link)
                        .intoImageView(headerImage);
                */

                Future<Bitmap> bmp = Ion.with(context)
                        .load(img.getImageUrl())
                        .asBitmap()
                        .setCallback(new FutureCallback<Bitmap>() {
                            @Override
                            public void onCompleted(Exception e, Bitmap result) {
                                headerImage.setImageBitmap(result);
                                Palette.from(result).generate(paletteListener);
                            }
                        });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("[ImageActivity]","Error loading the image");
            }
        });

        imageTitle.setText(image.getName());
        //imageAuthor.setText(image.getUser_id());

        populateDetails();

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

    private void populateDetails() {

        List<String> printableProperties = Arrays.asList("width", "height" ,
                "category", "rating", "votesCount", "favoritesCount");

        for (String property : printableProperties) {
            detailsContainer = (LinearLayout) findViewById(R.id.image_details);
            View row = LayoutInflater.from(this).inflate(R.layout.image_details_row, null);

            Class imageClass = Image.class;
            try {
                Method getter = imageClass.getMethod("get" + StringsHelper.capitalize(property));
                Object value = getter.invoke(image, new Object[]{});

                TextView propertyName  = (TextView) row.findViewById(R.id.property_name);
                TextView propertyValue = (TextView) row.findViewById(R.id.property_value);
                propertyName.setText(property.toUpperCase());
                propertyValue.setText(value.toString());

                detailsContainer.addView(row);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
