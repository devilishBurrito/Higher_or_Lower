package com.example.higherorlower;

import android.app.Application;
import android.os.Bundle;

import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableConfig;
import com.iterable.iterableapi.IterableInAppHandler;
import com.iterable.iterableapi.IterableInAppMessage;


public class AppliClass extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        class MyInAppHandler implements IterableInAppHandler {
            @Override
            public InAppResponse onNewInApp(IterableInAppMessage message) {
                    return InAppResponse.SHOW;
            }
        }

        IterableConfig config = new IterableConfig.Builder()
                .setInAppHandler(new MyInAppHandler())
                .build();
        IterableApi.initialize(getApplicationContext(), getString(R.string.iter_apikey), config);
        IterableApi.getInstance().setEmail(getString(R.string.iter_email));
        IterableApi.getInstance().registerForPush();
    }

}
