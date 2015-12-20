package me.fernandodominguez.pixels;

import me.fernandodominguez.pixels.models.PhotoResponse;
import me.fernandodominguez.pixels.models.PhotosResponse;
import retrofit.http.GET;
import retrofit.*;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Fernando Dominguez on 06/12/15.
 */
public class FiveHundredPxClient {

    /* Where to contact the HTTP API */
    private static final String WEB_SERVICE_BASE_URL = "https://api.500px.com/v1/";

    private String apiKey;
    private final FiveHundredPx service;

    /* Constructor */

    public FiveHundredPxClient(final String apiKey){

        this.apiKey = apiKey;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WEB_SERVICE_BASE_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    public void intercept(RequestFacade request) {
                        request.addQueryParam("consumer_key", apiKey);
                    }
                })
                .build();

        service = restAdapter.create(FiveHundredPx.class);
    }

    private interface FiveHundredPx {
        @GET("/photos")
        PhotosResponse getImages();

        @GET("/photos")
        void getImages(@Query("feature") String feature, @Query("rpp") Integer rpp,
                       @Query("image_size") Integer imageSize, Callback<PhotosResponse> callback);

        @GET("/photos/{id}")
        PhotosResponse getImage(@Path("id") int id);

        @GET("/photos/{id}")
        void getImage(@Path("id") int id, Callback<PhotoResponse> callback);

        @GET("/photos/{id}")
        PhotosResponse getImage(@Path("id") int id, @Query("image_size") int size);

        @GET("/photos/{id}")
        void getImage(@Path("id") int id, @Query("image_size") int size, Callback<PhotoResponse> callback);

        @GET("/photos/{id}")
        void getImage(@Path("id") int id, @Query("image_size") int size, @Query("tags") int tags, Callback<PhotoResponse> callback);
    }

    /**/
    public void getImage(int id, Callback<PhotoResponse> callback){
        service.getImage(id, callback);
    }

    public void getBigImage(int id, Callback<PhotoResponse> callback){
        service.getImage(id, 4, 1, callback);
    }

    public PhotosResponse getImages(){
        return service.getImages();
    }

    public void getImages(Callback<PhotosResponse> callback){
        service.getImages(null, null, 4, callback);
    }

    public void getImages(String feature, Callback<PhotosResponse> callback){
        service.getImages(feature, null, 4, callback);
    }

    public void getImages(String feature, int rpp, Callback<PhotosResponse> callback){
        service.getImages(feature, rpp, 4, callback);
    }

    public void getImages(String feature, int rpp, int imageSize, Callback<PhotosResponse> callback){
        service.getImages(feature, rpp, imageSize, callback);
    }
}
