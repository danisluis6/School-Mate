package com.example.enclaveit.schoolmateapp.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by enclaveit on 17/03/2017.
 */

public class TimeTableAsyncTask extends AsyncTask<String, String, String> {
    private static final String MESSAGE = "Time Table AsyncTask";
    /*Required initialization*/
    private HttpClient client = new DefaultHttpClient();
    private HttpGet httpGet ;
    private HttpResponse response;
    private HttpEntity httpEntity;
    private Context context;
    private ProgressDialog progressDialog;
    String content = "";
    JSONArray jsonArrayTimeTables, jsonArrayLessons, jsonArrayTitleOfDay;

    public TimeTableAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Log.i(MESSAGE, "On PreExecute ");
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i(MESSAGE, "On doInBackground");
        String url = params[0];
        url = url.replace(" ", "%20");  //Replace " " to "%20" is url correctly
        Log.i(MESSAGE, "URL: "+url);
        //get Data
        httpGet = new HttpGet(url);
        try{
            response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode= statusLine.getStatusCode();
            if(statusCode == 200){
                Log.i(MESSAGE, "Get data success");
                httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while((line = br.readLine()) != null){
                    stringBuilder.append(line);
                }
                content = stringBuilder.toString();
            }else{
                Log.i(MESSAGE, "Fail to get data from server");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return content; /*Execute onPostExecute next*/
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i(MESSAGE, "On PostExecute");
        String message = "Error";
        if(!content.equals("")) {
        /*Convert data to JSON*/
            try {
                JSONObject data = new JSONObject(result);
                //Log.i(MESSAGE, "JSON OBJECT: "+data.getString("timetables"));
                jsonArrayTimeTables = new JSONArray(data.getString("timetables"));
                jsonArrayLessons = new JSONArray(data.getString("lessons"));
                jsonArrayTitleOfDay = new JSONArray(data.getString("titleOfDay"));
                Log.i(MESSAGE, "JSON TimeTables Length = " + jsonArrayTimeTables.length());
                Log.i(MESSAGE, "JSON Lessons Length = " + jsonArrayLessons.length());
                message = "Complete";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Intent sendResult = new Intent("com.example.enclaveit.schoolmateapp.CUSTOM_INTENT");
        sendResult.putExtra("result", message);
        context.sendBroadcast(sendResult);
        progressDialog.dismiss();
    }

    public JSONArray getJSONArrayTimeTables(){
        return jsonArrayTimeTables;
    }

    public JSONArray getJSONArrayLessons(){
        return jsonArrayLessons;
    }
    public JSONArray getJSONArrayTitleOfDay(){
        return jsonArrayTitleOfDay;
    }
}
