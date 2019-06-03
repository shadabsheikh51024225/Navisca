package com.example.shadabsheikh.pain_tracker;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.Config;
import json.HttpCall;
import pojo.RegisterData;
import util.NotificationUtils;

import static java.security.AccessController.getContext;

public class Register extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences pref;
    TextView textView2,username,password,phone,email,vehicle;
    String data,user,pass,phn,emailid,veh,token;
    RegisterData regdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textView2 = (TextView) findViewById(R.id.textView2);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        vehicle = (TextView) findViewById(R.id.vehicle);
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
                    textView2.setText(message);
                }
            }
        };
        displayFirebaseRegId();
    }
    private void displayFirebaseRegId() {
        pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        token = regId;
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            textView2.setText("Firebase Reg Id: " + regId);
        else
            textView2.setText("Firebase Reg Id is not received yet!");
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

    //>---------------------->>
    public void reg(View v) {
        regdata = new RegisterData();

        regdata.setUser(user = username.getText().toString());
        regdata.setPhn(phn = phone.getText().toString());
        regdata.setEmailid(emailid = email.getText().toString());
        regdata.setPass(pass = password.getText().toString());
        regdata.setVeh(veh = vehicle.getText().toString());
                new Reg_class().execute();
    }
    final class Reg_class extends AsyncTask<String, Integer, String> {
        String Cookies,success;
        ProgressDialog progressDialog;
        Boolean flag = false;
        BufferedReader reader;


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Register.this);
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

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                param.put("username", user);
                param.put("email", emailid);
                param.put("password", pass);;
                param.put("phone", phn);
                param.put("vin", veh);
                param.put("gcm", token);
//                params.add(regdata);
                int i=0;
                JSONObject json = new JSONObject(fc.postsString("http://138.197.164.213/api/users/register",param).toString());
                data = json.get("_id").toString();
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
            Toast.makeText(getApplicationContext(),"Successful..",Toast.LENGTH_LONG).show();
            Intent i = new Intent(Register.this,MainActivity.class);
            startActivity(i);
            Register.this.finishActivity(0);
            Register.this.finish();

        }


    }
    public void cag(View v)
    {
        Intent i = new Intent(Register.this,MainActivity.class);
        i.putExtra("cag",true);
        startActivity(i);
        Register.this.finish();
    }

}