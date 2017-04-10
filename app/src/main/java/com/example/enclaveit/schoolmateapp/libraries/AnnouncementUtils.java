package com.example.enclaveit.schoolmateapp.libraries;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.asynctasks.JSONSubject;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.bean.Subject;
import com.example.enclaveit.schoolmateapp.config.ConfigURL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vuongluis on 4/4/17.
 */

public class AnnouncementUtils implements JSONSubject.AsyncResponse{

    private List<Announcement> arrayAnnouncement = new ArrayList<>();
    private ActivityAnnouncement context;
    private JSONSubject jsonSubject;

    public AnnouncementUtils(){
        // default
    }

    public AnnouncementUtils(ActivityAnnouncement context, List<Announcement> arrayAnnouncement){
        this.context = context;
        this.arrayAnnouncement = arrayAnnouncement;

        jsonSubject = new JSONSubject(this.context);
        jsonSubject.execute(ConfigURL.urlSubjects);
        jsonSubject.delegateSubject = this;
    }

    /** Analysis arrayAnnouncement **/
    public List<Announcement> getArrayAnnouncement(String typeofannouncement){

        List<Announcement> temp = new ArrayList<>();
        if(typeofannouncement.equals("agreeschoolfee")){
            /** Get fee */
            temp = getSchoolFeesList(this.arrayAnnouncement);
        }else if(typeofannouncement.equals("agreeschoolexam")){
            /** Get exam */
            temp = getExaminationList(this.arrayAnnouncement);
        }else if(typeofannouncement.equals("agreeschoolconference")){
            /** Get conference */
            temp = getConferenceList(this.arrayAnnouncement);
        }else if (typeofannouncement.equals("agreeschoolactivity")){
            /** Get activities */
            temp = getActivitiesList(this.arrayAnnouncement);
        }
        return temp;
    }

    /** Get List<Announcement> */
    private List<Announcement> getSchoolFeesList(List<Announcement> temp){
        List<Announcement> result = new ArrayList<>();
        for(int index = 0; index < this.arrayAnnouncement.size(); index++){
            String[] resultOfSplit = this.arrayAnnouncement.get(index).getAnnouncementContent().split("----");
            if(resultOfSplit[1].equals("agreeschoolfee")){
                result.add(this.arrayAnnouncement.get(index));
            }
        }
        return result;
    }

    /** Get List<Announcement> */
    private List<Announcement> getExaminationList(List<Announcement> temp){
        List<Announcement> result = new ArrayList<>();
        for(int index = 0; index < this.arrayAnnouncement.size(); index++){
            String[] resultOfSplit = this.arrayAnnouncement.get(index).getAnnouncementContent().split("----");
            if(resultOfSplit[1].equals("agreeschoolexam")){
                result.add(this.arrayAnnouncement.get(index));
            }
        }
        return result;
    }

    /** Get List<Announcement> */
    private List<Announcement> getConferenceList(List<Announcement> temp){
        List<Announcement> result = new ArrayList<>();
        for(int index = 0; index < this.arrayAnnouncement.size(); index++){
            String[] resultOfSplit = this.arrayAnnouncement.get(index).getAnnouncementContent().split("----");
            if(resultOfSplit[1].equals("agreeschoolconference")){
                result.add(this.arrayAnnouncement.get(index));
            }
        }
        return result;
    }

    /** Get List<Announcement> */
    private List<Announcement> getActivitiesList(List<Announcement> temp){
        List<Announcement> result = new ArrayList<>();
        for(int index = 0; index < this.arrayAnnouncement.size(); index++){
            String[] resultOfSplit = this.arrayAnnouncement.get(index).getAnnouncementContent().split("----");
            if(resultOfSplit[1].equals("agreeschoolactivity")){
                result.add(this.arrayAnnouncement.get(index));
            }
        }
        return result;
    }

    /** This process is school fees  */
    public int typeOfAnnouncement(int choice){
        int result = 1;
        switch (choice){
            case 1:
                result = 1;
                break;
            case 2:
                result = 2;
                break;
            case 3:
                result = 4;
                break;
            case 4:
                result = 4;
                break;
        }
        return result;
    }

    @Override
    public void getListSubject(List<Subject> output) {
        /** get Data from AsyncTask **/
    }

    public String getPositionContent(String temp,int index){
        return temp.split("----")[index];
    }

    /** Get Class **/
    public String getAnnouncementContentClass(String temp){
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

    /** Process time **/
    public String convertTimeToVN(String temp){
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

    /** Process date */
    public String convertDateToVN(String temp){
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

    /** Process type of examination */
    public int getTypeOfIconExam(String temp){
        /**
         * 'a' => '15 Minutes',
         * 'b' => '30 Minutes',
         * 'c' => '45 Minutes',
         * 'd' => 'Midterm',
         * 'e' => 'Final examination'
         */
        int result = 0;
        int[] typeofExams = {
            R.drawable.anouce_icon_15, R.drawable.anouce_icon_30, R.drawable.anouce_icon_45, R.drawable.anouce_icon_midterm, R.drawable.anouce_icon_final
        };
        switch (temp){
            case "a":
                result = typeofExams[0];
                break;
            case "b":
                result = typeofExams[1];
                break;
            case "c":
                result = typeofExams[2];
                break;
            case "d":
                result = typeofExams[3];
                break;
            case "e":
                result = typeofExams[4];
                break;
        }
        return result;
    }

}
