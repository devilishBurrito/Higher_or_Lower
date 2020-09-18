package com.example.higherorlower;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableHelper;
import com.iterable.iterableapi.IterableInAppManager;
import com.iterable.iterableapi.IterableInAppMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class IterActivity extends Activity {

    Button update,sendEvent,render;

    static String TAG = "Iterable-SDK";


    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_iter);

        String iter_email = getString(R.string.iter_email);

        update = findViewById(R.id.iter_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject obj = new JSONObject();
                try {
                    obj.put("firstName","Joe");
                    obj.put("isRegisteredUser",true);
                    obj.put("SA_User_Test_Key","completed");
                    Log.i(TAG,obj.toString());
                    IterableApi.getInstance().updateUser(obj);
                    Toast.makeText(getApplicationContext(),"Profile Updated", Toast.LENGTH_SHORT).show();
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error with Profile JSON",Toast.LENGTH_SHORT).show();
                }
            }
        });

        sendEvent = findViewById(R.id.iter_button2);
        sendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject event = new JSONObject();
                try {
                    event.put("platform", "Android");
                    event.put("isTestEvent", true);
                    event.put("url", "https://iterable.com/sa-test/Joe");
                    event.put("secret_code_key", "Code_123");
                    Log.i(TAG,event.toString());
                    IterableApi.getInstance().track("mobileSATestEvent",event);
                    Toast.makeText(getApplicationContext(),"Event Sent",Toast.LENGTH_SHORT).show();
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error with Event JSON",Toast.LENGTH_SHORT).show();
                }
            }
        });

        render = findViewById(R.id.iter_button3);
        render.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IterableInAppManager inAppManager = IterableApi.getInstance().getInAppManager();
                List<IterableInAppMessage> messages = inAppManager.getMessages();
                Log.i(TAG, messages.toString());
            }
        });


    }
}
