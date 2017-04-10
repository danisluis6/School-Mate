package com.example.enclaveit.schoolmateapp.libraries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by enclaveit on 07/04/2017.
 */

public class AnnouncementMethods{

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
}
