package com.example.enclaveit.schoolmateapp.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementUsing;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementMethods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by enclaveit on 16/12/2016.
 */

public class AlertDialogFee extends AnnouncementMethods implements AnnouncementUsing {

    private static AlertDialog alertDialog;

    public static AlertDialog onCreateDialog(final Activity activity, Announcement announcement) {

        /** Create AlertDialog.Builder  */
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        /** Create Layout for AlertDiaglog.Builder **/
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.alert_annouce_fee, (ViewGroup) activity.findViewById(R.id.layout_alert_announce));
        builder.setView(layout);

        /** Create event for AlertDialog.Builder */
        Button OK = (Button)layout.findViewById(R.id.ok);
        Button Cancle = (Button)layout.findViewById(R.id.cancle);

        final TextView title = (TextView) layout.findViewById(R.id.title);
        final TextView description = (TextView) layout.findViewById(R.id.description);
        final TextView grade = (TextView) layout.findViewById(R.id.grade);
        final TextView time = (TextView) layout.findViewById(R.id.timeannounce);

        title.setText(announcement.getAnnouncementTitle());
        description.setText(new AlertDialogFee().getAnnouncementContent(announcement.getAnnouncementContent(),0));
        grade.setText(new AlertDialogFee().getAnnouncementContentClass(new AlertDialogFee().getAnnouncementContent(announcement.getAnnouncementContent(),2)));
        time.setText(new AlertDialogFee().convertTimeToVN(announcement.getAnnouncementDate())+" "+new AlertDialogFee().convertDateToVN(announcement.getAnnouncementDate()));

        // Add Event for button
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog = builder.create();
        return alertDialog;
    }

    /** Process content **/

    @Override
    public String getAnnouncementContent(String temp,int index){
        return temp.split("----")[index];
    }

    /** Get Class **/
    @Override
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
    @Override
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
    @Override
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
