package json;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;


/**
 * Created by SYNC Technologies on 17-08-2015.
 */
public class HttpCall {
    static String TAG;
    String response;
    URL url;

    public HttpCall() {
    }

    JSONObject temp1;

    public String post(String endpoint, String type, JSONObject params) throws IOException
    {
        String Responce="";
        try {

        url = new URL(endpoint);
    //            Log.v("httpCall", "Posting '" + body + "' to " + url);
        if (type == "POST") {
            HttpClient httpClient = new ContentEncodingHttpClient();
            try {
//
                HttpPost request = new HttpPost(endpoint);
                StringEntity ps = new StringEntity(params.toString());
                ps.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                request.setEntity(ps);
                HttpResponse response = httpClient.execute(request);
                if(response != null) {

                    int statuscode = response.getStatusLine().getStatusCode();

                    if(statuscode == HttpStatus.SC_OK) {
                        String strResponse = EntityUtils.toString(response.getEntity());
                        Log.d("responce..",strResponse);
                        Responce = strResponse;
                    }
                }
    //           String str =   httpclient.execute(httpost, responseHandler).toString();
            } catch (Exception e) {

            }
        } else {//for GET method

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(endpoint);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
                Log.d("Thisssssssss", text);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
            if (text != null) {
                response = text;
                return text;
            }
        }
        }catch (Exception e)
        {

        }
                    return Responce;

    }
    public String postsString(String url, JSONObject param){
        HttpClient httpClient = new DefaultHttpClient();
        String res=null;
        try {
            Log.d("url =.................", url);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new ByteArrayEntity(param.toString().getBytes("UTF8")));
            Log.d("PArams = ..............", param.toString());

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            res = getASCIIContentFromEntity(entity);
            Log.d("Http Post Response:", res.toString() + "");

            if (!res.contains("success"))//Encoding POST data
            {
                res = "ERROR";
            }


            //  Log.d("Http Post Response:", response.toString());

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
        return res;
    }
    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }
   }
