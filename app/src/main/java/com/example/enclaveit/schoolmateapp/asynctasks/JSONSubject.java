package com.example.enclaveit.schoolmateapp.asynctasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.bean.Subject;
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

public class JSONSubject extends AsyncTask<String, Void, String>{

    private ActivityAnnouncement context;
    private HttpGet httpGet ;
    private HttpClient client = new DefaultHttpClient();
    private HttpResponse response;
    private HttpEntity httpEntity;
    private JSONArray jsonArrayResultDetails;
    private String message;

    List<JSONObject> objectList = new ArrayList<>();
    List<Subject> listSubjects = new ArrayList<>();

    public interface AsyncResponse {
        void getListSubject(List<Subject> output);
    }

    public AsyncResponse delegateSubject = null;

    public JSONSubject(ActivityAnnouncement context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
                Log.i("JSONSubject", ConfigLog.ErrorGetJSON);
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
            jsonArrayResultDetails = new JSONArray(data.getString("subjects"));
            Subject subject = new Subject();
            for (int i = 0; i < jsonArrayResultDetails.length(); i++) {
                subject.setSubjectID(Integer.parseInt(jsonArrayResultDetails.getJSONObject(i).getString("subjectID")));
                subject.setSubjectName(String.valueOf(jsonArrayResultDetails.getJSONObject(i).getString("subjectName")));
                objectList.add(jsonArrayResultDetails.getJSONObject(i));
            }

            for(int j = 0; j < objectList.size(); j++){
                listSubjects.add(new Subject(
                    Integer.parseInt(objectList.get(j).getString("subjectID")),
                    String.valueOf(objectList.get(j).getString("subjectName"))
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegateSubject.getListSubject(listSubjects);
    }
}
