package com.example.higherorlower;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableConfig;
import com.iterable.iterableapi.IterableHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class StartActivity extends AppCompatActivity {

    private final String iter_devkey = "349dcc9373c74c6699c5d1204a271695";
    private final String user_email = "removed for github upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        start();

        IterableConfig config = new IterableConfig.Builder()
                .setPushIntegrationName("myPushIntegration")
                .build();
        IterableApi.initialize(this, iter_devkey, config);
    }

    private void start() {
        Button start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               IterableApi.getInstance().setEmail(user_email);
                IterableApi.getInstance().registerForPush();
                Toast.makeText(StartActivity.this, user_email + " has been registered with Iterable", Toast.LENGTH_SHORT).show();
            }
        });

        Button update = findViewById(R.id.updateLogin);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject dataFields = new JSONObject();
                try {
                    dataFields.put("firstName", "Joe");
                    dataFields.put("isRegisteredUser", "true");
                    dataFields.put("SA_User_Test_Key", "completed");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                IterableApi.getInstance().updateUser(dataFields);
                Toast.makeText(StartActivity.this, "User Update Sent", Toast.LENGTH_SHORT).show();
            }
        });

        Button sendEvent = findViewById(R.id.sendEventButton);
        sendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = "mobileSATestEvent";
                String secret_code = "Code_123";

                JSONObject dataFields = new JSONObject();
                try {
                    dataFields.put("platform", "Android");
                    dataFields.put("url", " “https://iterable.com/sa-test/Joe");
                    dataFields.put("isTestEvent", "true");
                    dataFields.put("secret_code_key", secret_code);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                IterableApi.getInstance().track(eventName,dataFields);
                Toast.makeText(StartActivity.this, eventName + " sent to Iterable", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void inAppNotif(){
        IterableHelper.IterableActionHandler iterableActionHandler = new IterableHelper.IterableActionHandler() {
            @Override
            public void execute(String data) {
                Log.i("ITERABLE NOTIF", data);
            }

            public void onFailure(String reason, JSONObject data){
                Log.e("ITERABLE NOTIF", "failure due to: " + reason);
                Log.e("ITERABLE NOTIF", data.toString());
            }

        };

        IterableApi.getInstance().spawnInAppNotification(StartActivity.this, iterableActionHandler);
//        IterableApi.getInstance().getInAppMessages(1,iterableActionHandler);


    }

    public void checkforNotif(View v){
        inAppNotif();
    }
}

