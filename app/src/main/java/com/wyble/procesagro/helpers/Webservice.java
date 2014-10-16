package com.wyble.procesagro.helpers;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by david on 9/7/14.
 */
public class Webservice {

    private String URL;

    public Webservice(String URL) {
        this.URL = URL;
    }

    public JSONArray parseJsonText(String jsonText) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public String getJsonText() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(this.URL);
        String jsonText = null;

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                jsonText = builder.toString();
            } else {
                Log.e(Webservice.class.getName(), "Failed to connect!");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonText;
    }

}
