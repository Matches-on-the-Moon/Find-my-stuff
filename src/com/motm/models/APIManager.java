package com.motm.models;

import android.net.Uri;
import android.os.AsyncTask;
import com.motm.helpers.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

public class APIManager
{
    private String baseURL;

    public JSONObject get(String method, List<NameValuePair> pairs)
    {
        Uri.Builder builder = new Uri.Builder();
        // set params
        if(pairs != null){
            for(NameValuePair pair : pairs){
                builder.appendQueryParameter(pair.getName(), pair.getValue());
            }
        }
        builder.path(method);
        
        HttpGet get = new HttpGet(baseURL + builder.toString());
        
        
        SyncResponse syncResponse = new SyncResponse();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = syncResponse.execute(get).get();
        }
        catch (Exception e) {
            Logger.d("APIManager put failed: " + Exception.class + ": " + e.getMessage());
        }

        return jsonObject;
    }

    public JSONObject post(String method, List<NameValuePair> pairs)
    {
        HttpPost post = new HttpPost(baseURL + method);
        // entity from pairs
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(pairs);
        }
        catch (UnsupportedEncodingException e) {
            Logger.d("APIManager post failed: " + e.getMessage());
            return new JSONObject();
        }
        post.setEntity(entity);

        SyncResponse syncResponse = new SyncResponse();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = syncResponse.execute(post).get();
        }
        catch (Exception e) {
            Logger.d("APIManager post failed: " + Exception.class + ": " + e.getMessage());
        }

        return jsonObject;
    }

    public JSONObject put(String method, List<NameValuePair> pairs)
    {
        HttpPut put = new HttpPut(baseURL + method);

        // entity from pairs
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(pairs);
        }
        catch (UnsupportedEncodingException e) {
            Logger.d("APIManager put failed: " + e.getMessage());
            return new JSONObject();
        }
        put.setEntity(entity);

        SyncResponse syncResponse = new SyncResponse();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = syncResponse.execute(put).get();
        }
        catch (Exception e) {
            Logger.d("APIManager put failed: " + Exception.class + ": " + e.getMessage());
        }

        return jsonObject;
    }

    public JSONObject delete(String method, List<NameValuePair> pairs)
    {
        HttpDelete delete = new HttpDelete(baseURL + method);
        // set params
        if(pairs != null){
            for(NameValuePair pair : pairs){
                delete.getParams().setParameter(pair.getName(), pair.getValue());
            }
        }
        
        SyncResponse syncResponse = new SyncResponse();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = syncResponse.execute(delete).get();
        }
        catch (Exception e) {
            Logger.d("APIManager put failed: " + Exception.class + ": " + e.getMessage());
        }

        return jsonObject;
    }

    // only even number of params, please
    public List<NameValuePair> createParams(String... keyValues)
    {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        String key = null;
        String value = null;
        for(String str : keyValues){
            if(key == null){
                // set key first
                key = str;
            } else if(value == null){
                // set value second
                value = str;
            } else {
                // both key value was set, add to hashmap
                pairs.add(new BasicNameValuePair(key, value));

                // reset
                key = str;
                value = null;
            }
        }
        // last one
        pairs.add(new BasicNameValuePair(key, value));

        return pairs;
    }

    public void setBaseURL(String baseURL)
    {
        this.baseURL = baseURL;
    }

    // network must run in a seperate thread, use AsyncTask
    // example call: syncResponse.execure(...).get() to make sync
    private class SyncResponse extends AsyncTask<HttpRequestBase, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(HttpRequestBase... request)
        {
            DefaultHttpClient client = new DefaultHttpClient();
            JSONObject jsonObject = null;

            try {
                HttpResponse response = client.execute(request[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String json = reader.readLine();
                JSONTokener tokener = new JSONTokener(json);
                jsonObject = new JSONObject(tokener);
            }
            catch (Exception e) {
                Logger.d("APIManager doInBackground failed: " + Exception.class + ": " + e.getMessage());
            }

            return jsonObject;
        }
    }
}
