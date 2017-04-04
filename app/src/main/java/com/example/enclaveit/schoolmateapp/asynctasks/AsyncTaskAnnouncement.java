package com.example.enclaveit.schoolmateapp.asynctasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.enclaveit.schoolmateapp.activities.ActivityAnnoucement;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.config.ConfigLog;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 04/04/2017.
 */

public class AsyncTaskAnnouncement extends AsyncTask<String, Void, String>{

    private ProgressDialog pDialog;
    private ActivityAnnoucement context;
    private HttpGet httpGet ;
    private HttpClient client = new DefaultHttpClient();
    private HttpResponse response;
    private HttpEntity httpEntity;
    private JSONArray jsonArrayResultDetails;
    private String message;

    List<JSONObject> objectList = new ArrayList<>();
    List<Announcement> listAnnouncements = new ArrayList<>();

    public interface AsyncResponse {
        void processFinish(List<Announcement> output);
    }

    public AsyncResponse delegate = null;

    public AsyncTaskAnnouncement(ActivityAnnoucement context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        httpGet = new HttpGet(url);
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
                message = stringBuilder.toString();
                return message;
            }else{
                Log.i("AsyncTaskAnnouncement", ConfigLog.ErrorGetJSON);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String result) {
        /*Convert JSON to ArrayList<Object> */
        try {
            JSONObject data = new JSONObject(result);
            jsonArrayResultDetails = new JSONArray(data.getString("announcements"));
            Announcement announcement = new Announcement();
            for (int i = 0; i < jsonArrayResultDetails.length(); i++) {
                announcement.setAnnouncementID(Integer.parseInt(jsonArrayResultDetails.getJSONObject(i).getString("announcementID")));
                announcement.setAnnouncementTitle(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("announcementTitle")));
                announcement.setAnnouncementDescription(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("announcementDescription")));
                announcement.setAnnouncementContent(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("announcementContent")));
                announcement.setAnnouncementContent(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("announcementDateCreated")));
                announcement.setAnnouncementUserID(Integer.parseInt(jsonArrayResultDetails.getJSONObject(i).getString("userID")));
                objectList.add(jsonArrayResultDetails.getJSONObject(i));
            }

            for(int j = 0; j < objectList.size(); j++){
                listAnnouncements.add(new Announcement(
                    Integer.parseInt(objectList.get(j).getString("announcementID")),
                    String.valueOf(objectList.get(j).getString("announcementTitle")),
                    String.valueOf(objectList.get(j).getString("announcementDescription")),
                    String.valueOf(objectList.get(j).getString("announcementContent")),
                    String.valueOf(objectList.get(j).getString("announcementDateCreated")),
                    Integer.parseInt(objectList.get(j).getString("userID"))
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.processFinish(listAnnouncements);
        pDialog.dismiss();
    }
}
