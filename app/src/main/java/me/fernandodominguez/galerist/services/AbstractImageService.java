package me.fernandodominguez.galerist.services;

import android.os.Handler;

/**
 * Created by fernando on 07/12/15.
 */
public abstract class AbstractImageService implements ImageService{
    protected Handler handler;

    public AbstractImageService(Handler handler) {
        this.handler = handler;
    }
}
