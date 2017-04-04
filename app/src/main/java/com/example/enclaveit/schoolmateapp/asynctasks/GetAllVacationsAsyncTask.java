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

public class GetAllVacationsAsyncTask extends AsyncTask<String, Void, String> {
    HttpClient client = new DefaultHttpClient();
    HttpResponse response;
    HttpGet httpGet ;
    HttpEntity httpEntity;
    JSONObject[] listVacations;
    Context context;

    public GetAllVacationsAsyncTask(Context context){
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
    protected void onPostExecute(String result){
        try {
            JSONArray arrayVacations = new JSONArray((new JSONObject(result).getString("vacations")));
            listVacations = new JSONObject[arrayVacations.length()];
            for (int i = 0; i < arrayVacations.length(); i++) {
                listVacations[i] = arrayVacations.getJSONObject(i);
            }
            ActivityTimeTable timeTableActivity = (ActivityTimeTable) context;
                timeTableActivity.initListVacationJSONObject(listVacations);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
