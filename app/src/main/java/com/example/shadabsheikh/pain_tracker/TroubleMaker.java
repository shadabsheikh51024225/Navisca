package com.example.shadabsheikh.pain_tracker;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import Adapters.ListAdapter;

public class TroubleMaker extends AppCompatActivity {
    ListView lv;
    ArrayList<HashMap<String, String>> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_maker);
        lv = (ListView)findViewById(R.id.lv_trouble_maker);
        new read(false,null).execute();
    }
    final class read extends AsyncTask<String, Integer, String> {
        String Cookies,success;
        ProgressDialog progressDialog;
        Boolean flag = false;
        BufferedReader reader;

        public read(Boolean flag, BufferedReader reader) {
            this.flag = flag;
            this.reader = reader;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(TroubleMaker.this);
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... arg0) {


            try {
                if(!flag) {
                    reader = new BufferedReader(
                            new InputStreamReader(getApplicationContext().getAssets().open("xml.json")));
                }
                // do reading, usually loop until end of file reading
                String line;

                HashMap<String, String> hm;
                mData =  new ArrayList< HashMap<String,String>>();
                JSONObject json;

                while ((line = reader.readLine()) != null) {
                    json = new JSONObject(line);
                    hm = new HashMap<String, String>();
                    hm.put("name", json.getString("name"));
                    hm.put("value", json.getString("value"));
                    hm.put("time", json.getString("timestamp"));
                    mData.add(hm);
                    Log.wtf("here", mData.size()+"--"+json.getString("name"));
                }



            } catch(IOException e){
                //log the exception
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            lv.setAdapter(new ListAdapter(TroubleMaker.this,mData));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[values.length-1]);
            progressDialog.setTitle(values.toString());
        }
    }
}
