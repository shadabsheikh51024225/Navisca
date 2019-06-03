package com.example.shadabsheikh.pain_tracker;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.Config;
import json.HttpCall;
import util.NotificationUtils;

public class ServiceRegister extends AppCompatActivity {

    EditText vin,make,model,year,name,email,tel, password;
    Button Register;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences pref;
    private static final String TAG = MainActivity.class.getSimpleName();
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_register);
        vin = (EditText)findViewById(R.id.vin_et);
        make = (EditText)findViewById(R.id.make_et);
        model = (EditText)findViewById(R.id.model_et);
        year = (EditText)findViewById(R.id.model_et);
        name = (EditText)findViewById(R.id.name_et);
        email = (EditText)findViewById(R.id.email_et);
        tel = (EditText)findViewById(R.id.tel_et);
        password = (EditText)findViewById(R.id.pas_et);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };
        displayFirebaseRegId();
        Register = (Button)findViewById(R.id.btn_register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Reg_class().execute();
            }
        });
    }
    private void displayFirebaseRegId() {
        pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        token = regId;
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Toast.makeText(this,"Fireid"+regId,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Register iD not Received yet.",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    final class Reg_class extends AsyncTask<String, Integer, String> {
        String Cookies,success;
        ProgressDialog progressDialog;
        Boolean flag = false;
        BufferedReader reader;


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ServiceRegister.this);
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... arg0) {
            try {


                HttpCall fc = new HttpCall();
                JSONObject param = new JSONObject();

               // List<NameValuePair> params = new ArrayList<NameValuePair>();
                param.put("name", name.getText().toString());
                param.put("username", name.getText().toString());
                param.put("email", email.getText().toString());
                param.put("password", password.getText().toString());
                param.put("password2", password.getText().toString());
                param.put("phone", tel.getText().toString());
                param.put("vin", vin.getText().toString());
                param.put("gcm", token);
//                params.add(regdata);
                int i=0;
                String json = (fc.postsString("http://138.197.164.213/api/users/register",param).toString());
                if(json == "ERROR")
                {
                    success = "false";
                }
               // String data = json.get("_id").toString();
                pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("_id", token);
                editor.commit();

            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (Exception e2)
            {
                e2.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(success != "false") {
                Toast.makeText(getApplicationContext(), "Successful..", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finishActivity(0);
                finish();
            }

        }


    }

}
