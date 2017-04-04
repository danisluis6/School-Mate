package com.example.enclaveit.schoolmateapp.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by enclaveit on 22/03/2017.
 */

public class FetchUrlAsyncTask extends AsyncTask<String, Void, String> {
    private final String MESSAGE = "FetchUrlAsyncTask";
    GoogleMap gMap;
    public FetchUrlAsyncTask(GoogleMap googleMap){
        this.gMap = googleMap;
    }

    @Override
    protected String doInBackground(String... url) {
            /*To store data from web service*/
        String data = "";
        try{
            data = downloadFromUrl(url[0]);
            //Log.i(MESSAGE, "Data from Async Task : "+data.toString());
        }catch (Exception e){
            Log.d(MESSAGE, "Error when download data from web service: "+e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
            /*Parse data*/
        ParserTask parserTask = new ParserTask(gMap);
        //Invokes the thread for parsing the JSON data
        parserTask.execute(result);
    }

    /*Download json data from url*/
    private String downloadFromUrl(String strUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try{
            URL url = new URL(strUrl);
            /*Open connection*/
            connection = (HttpURLConnection) url.openConnection();
            /*Connect to url*/
            connection.connect();

            /*Read data from url*/
            inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer strBuffer = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null ){
                strBuffer.append(line);
            }
            data = strBuffer.toString();
            //Log.i(MESSAGE, "Data from DownloadFromURL : "+data);
            br.close();
        }catch (Exception e){
            //Log.i(MESSAGE, "DownloadFromURL : "+e.toString());
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            connection.disconnect();
        }
        return data;
    }
}
