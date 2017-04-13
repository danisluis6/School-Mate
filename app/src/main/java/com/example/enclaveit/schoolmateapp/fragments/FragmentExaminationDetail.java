package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementUtils;

/**
 * Created by enclaveit on 10/04/2017.
 */

public class FragmentExaminationDetail extends Fragment{

    private ActivityAnnouncement mainActivity;
    private Announcement announcement;
    private String header;
    private AnnouncementUtils announcementUtils;

    private ImageView examIcon;
    private TextView examTitle,examClass,examDate,examTeacher,examContent;

    public FragmentExaminationDetail(String header, Announcement announcement) {
        this.announcement = announcement;
        this.header = header;
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
        View view = inflater.inflate(R.layout.fragment_examdetail, container, false);
        initComponents(view);
        pushAnnouncementOnWidget();
        return view;
    }

    private void pushAnnouncementOnWidget() {
        /** TODO **/
        examTitle.setText(announcement.getAnnouncementTitle());
        examClass.setText("Class: "+announcementUtils.getPositionContent(announcement.getAnnouncementContent(),2));
        examDate.setText(announcementUtils.convertTimeToVN(announcement.getAnnouncementDate()+" : "+announcementUtils.convertDateToVN(announcement.getAnnouncementDate())));
        examIcon.setImageResource(announcementUtils.getTypeOfIconExam(announcementUtils.getPositionContent(announcement.getAnnouncementContent(),4)));
        examContent.setText(announcementUtils.getPositionContent(announcement.getAnnouncementContent(),0));
    }

    private void initComponents(View view) {
        examTitle = (TextView)view.findViewById(R.id.examtitle);
        examClass = (TextView)view.findViewById(R.id.examclass);
        examContent = (TextView)view.findViewById(R.id.examcontent);
        examDate = (TextView)view.findViewById(R.id.examdate);
        examTeacher = (TextView)view.findViewById(R.id.examteacher);
        examIcon = (ImageView)view.findViewById(R.id.examicon);

        announcementUtils = new AnnouncementUtils();
    }
}