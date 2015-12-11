package me.fernandodominguez.galerist.services;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 06/12/15.
 */
public class ServicesManager {
    private Handler handler;
    private List<ImageService> enabledServices = new ArrayList<>();

    public ServicesManager(Handler handler) {
        this.handler = handler;
        // TODO do in settings
        enabledServices.add(new FiveHundrerPopularService(handler));
    }

    public void request(){
        // Make requests to the enabled services
        for (ImageService service : enabledServices){
            service.request();
        }
    }
}
