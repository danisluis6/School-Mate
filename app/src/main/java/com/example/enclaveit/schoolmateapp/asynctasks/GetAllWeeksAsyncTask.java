package com.example.enclaveit.schoolmateapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.example.enclaveit.schoolmateapp.activities.ActivityTimeTable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by PHA on 20/03/2017.
 */

public class GetAllWeeksAsyncTask extends AsyncTask<String, Void, String> {

    private HttpClient client = new DefaultHttpClient();
    private HttpGet httpGet ;
    private HttpResponse response;
    private HttpEntity httpEntity;
    private String[] listWeeks;
    Context context;

    public GetAllWeeksAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        httpGet = new HttpGet(url);
        String content ="";
        try{
            response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode= statusLine.getStatusCode();
            if(statusCode == 200){
                httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while((line = br.readLine()) != null){
                    stringBuilder.append(line);
                }
                content = stringBuilder.toString();
                return content;
            }else{
                Log.i("Message", "Fail to get data from server");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONArray arrayWeeks = new JSONArray((new JSONObject(result).getString("weeksOfYear")));
            Log.i("Message", "Length of arrayWeeks: " + arrayWeeks.length());
            listWeeks = new String[arrayWeeks.length()];
            for (int i = 0; i < arrayWeeks.length(); i++) {
                listWeeks[i] = arrayWeeks.getJSONObject(i).getString("weekOfYear");
            }
            ActivityTimeTable timeTableActivity = (ActivityTimeTable) context;
                timeTableActivity.initForWeekly(listWeeks);
        } catch (JSONException e) {
                e.printStackTrace();
        }
    }

}
