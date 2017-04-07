package com.example.enclaveit.schoolmateapp.libraries;

import android.content.Context;
import android.util.Log;

import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.bean.Announcement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuongluis on 4/4/17.
 */

public class AnnouncementUtils {

    private List<Announcement> arrayAnnouncement = new ArrayList<>();
    private ActivityAnnouncement context;
    private static final String TAG = AnnouncementUtils.class.getSimpleName();

    public AnnouncementUtils(ActivityAnnouncement context, List<Announcement> arrayAnnouncement){
        this.context = context;
        this.arrayAnnouncement = arrayAnnouncement;
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

    public void showArrayListNotification(){

    }
}
