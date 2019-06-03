package com.example.shadabsheikh.pain_tracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.Config;

public class Splash extends AppCompatActivity {
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = this.getSharedPreferences(Config.SHARED_PREF, 0);
        new CountDownTimer(4000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                String id = pref.getString("_id", "-").toString();
//                if (id != "-") {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
               // }
//                }else {
//                    Intent i = new Intent(getApplicationContext(), Register.class);
//                    startActivity(i);
//                    finish();
//                }

            }
        }.start();
    }
}
