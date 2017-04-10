package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.alert.AlertDialogFee;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementUsing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by enclaveit on 10/04/2017.
 */

public class FragmentFeeDetail extends Fragment implements AnnouncementUsing {

    private ActivityAnnouncement mainActivity;
    private Announcement announcement;
    private Button ok,cancle;
    private TextView title, description, grade, timeannounce;

    public FragmentFeeDetail(Announcement announcement) {
        this.announcement = announcement;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ActivityAnnouncement){
            this.mainActivity = (ActivityAnnouncement) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_feedetail, container, false);
        initComponents(view);
        pushAnnouncementOnWidget();
        return view;
    }

    private void pushAnnouncementOnWidget() {
        title.setText(announcement.getAnnouncementTitle());
        description.setText(getPositionContent(announcement.getAnnouncementContent(),0));
        grade.setText(getAnnouncementContentClass(new AlertDialogFee().getPositionContent(announcement.getAnnouncementContent(),2)));
        timeannounce.setText(convertTimeToVN(announcement.getAnnouncementDate())+" "+new AlertDialogFee().convertDateToVN(announcement.getAnnouncementDate()));
    }

    private void initComponents(View view) {
        ok = (Button)view.findViewById(R.id.ok);
        cancle = (Button)view.findViewById(R.id.cancle);
        title = (TextView)view.findViewById(R.id.title);
        description = (TextView)view.findViewById(R.id.description);
        grade = (TextView)view.findViewById(R.id.grade);
        timeannounce = (TextView)view.findViewById(R.id.timeannounce);
    }

    @Override
    public String getPositionContent(String temp, int index) {
        return temp.split("----")[index];
    }

    @Override
    public String getAnnouncementContentClass(String temp) {
        if(temp.equals("grade6")){
            return "Grade 6";
        }else if (temp.equals("grade7")){
            return "Grade 7";
        }else if (temp.equals("grade8")){
            return "Grade 8";
        }else if (temp.equals("grade9")){
            return "Grade 9";
        }
        return null;
    }

    @Override
    public String convertTimeToVN(String temp) {
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(temp);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            String hour = new String(String.valueOf(cal.get(cal.HOUR))).length()==2 ? ""+cal.get(cal.HOUR) : "0"+cal.get(cal.HOUR);
            String minute = new String(String.valueOf(cal.get(cal.MINUTE))).length()==2 ? ""+cal.get(cal.MINUTE) : "0"+cal.get(cal.MINUTE);
            String second = new String(String.valueOf(cal.get(cal.SECOND))).length()==2 ? ""+cal.get(cal.SECOND) : "0"+cal.get(cal.SECOND);
            return hour+":"+minute+":"+second;
        }catch(Exception e){
            //this generic but you can control another types of exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertDateToVN(String temp) {
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(temp);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            String date = new String(String.valueOf(cal.get(cal.DATE))).length()==2 ? ""+cal.get(cal.HOUR) : "0"+cal.get(cal.HOUR);
            String month = new String(String.valueOf(cal.get(cal.MONTH))).length()==2 ? ""+cal.get(cal.MONTH) : "0"+cal.get(cal.MONTH);
            String year = new String(String.valueOf(cal.get(cal.YEAR))).length()==2 ? ""+cal.get(cal.YEAR) : "0"+cal.get(cal.YEAR);
            return date+"/"+String.valueOf(Integer.parseInt(month)+1)+"/"+year;
        }catch(Exception e){
            //this generic but you can control another types of exception
            e.printStackTrace();
        }
        return null;
    }
}