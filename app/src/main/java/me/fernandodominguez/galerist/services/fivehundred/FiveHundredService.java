package me.fernandodominguez.galerist.services.fivehundred;

import android.os.Handler;

import me.fernandodominguez.galerist.services.AbstractImageService;
import me.fernandodominguez.pixels.FiveHundredPxClient;

/**
 * Created by fernando on 07/12/15.
 */
public abstract class FiveHundredService extends AbstractImageService {

    protected FiveHundredPxClient service;

    public FiveHundredService(Handler handler) {
        super(handler);
        service = new FiveHundredPxClient("KdNLCVlyyYzXUajMBm11VE38FZG7TBJjed1HRJCN");
    }
}
