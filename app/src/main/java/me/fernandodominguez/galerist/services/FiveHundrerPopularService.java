package me.fernandodominguez.galerist.services;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import me.fernandodominguez.pixels.FiveHundredPxClient;
import me.fernandodominguez.pixels.models.PhotosResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by fernando on 07/12/15.
 */
public class FiveHundrerPopularService extends FiveHundredService {

    public FiveHundrerPopularService(Handler handler) {
        super(handler);
    }

    @Override
    public void request() {

        service.getImages(new Callback<PhotosResponse>() {
            @Override
            public void success(PhotosResponse photosResponse, Response response) {
                // Send a message to the caller
                Message message = new Message();
                message.obj = photosResponse;
                handler.sendMessage(message);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(ServicesManager.class.getName(), "Request failed");
            }
        });
    }
}
