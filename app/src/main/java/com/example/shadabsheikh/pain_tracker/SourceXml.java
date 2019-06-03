package com.example.shadabsheikh.pain_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import Adapters.ListAdapter;
import json.HttpCall;

public class SourceXml extends AppCompatActivity {
    ListView lv;
    ArrayList<String> name;
    ArrayList<String> value;
    ArrayList<String> time;
    HashMap<String,String> hm;
    ArrayList<HashMap<String, String>> mData;
    BufferedReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_xml);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Source XML");
        lv = (ListView)findViewById(R.id.lv_reading);
        hm = new HashMap<String, String>();
        mData =  new ArrayList< HashMap<String,String>>();

        new read(false,null).execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> hm = new HashMap<String, String>();
                hm = mData.get(i);
                new SendData(hm).execute();
            }
        });


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
            progressDialog = new ProgressDialog(SourceXml.this);
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
            lv.setAdapter(new ListAdapter(getApplicationContext(),mData));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[values.length-1]);
            progressDialog.setTitle(values.toString());
        }
    }

    final class SendData extends AsyncTask<String, String, String> {
        String Cookies,success;
        ProgressDialog progressDialog;
        HashMap<String,String> rpm;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SourceXml.this);
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }

        public SendData(HashMap<String,String> rpm) {
            this.rpm = rpm;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                HttpCall fc = new HttpCall();
                JSONObject param = new JSONObject();

                param.put("rpm", rpm.get("value"));
                param.put("data", rpm.get("name"));
                int i=0;
                JSONObject json = new JSONObject(fc.postsString("http://10.51.210.95:8080/",param).toString());
                success = json.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }

    public void openFile() {

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        startActivityForResult(Intent.createChooser(chooseFile, "Choose a file"), 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            Uri content_describer = data.getData();
            //get the path
            Log.d("Path???", content_describer.getPath());

            BufferedReader reader = null;
            try {
                // open the user-picked file for reading:
                InputStream in = getContentResolver().openInputStream(content_describer);
                // now read the content:
                reader = new BufferedReader(new InputStreamReader(in));

                new read(true,reader).execute();
            } catch (IOException e) {
                Log.wtf("Activity Result",e.toString());
            }
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.sourcexml, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openfile) {
//            Intent i = new Intent(this,Register.class);
//            startActivity(i);
//            this.finishActivity(0);
//            this.finish();
            openFile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
