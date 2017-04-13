package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementUtils;

/**
 * Created by enclaveit on 10/04/2017.
 */

public class FragmentConferenceDetail extends Fragment{

    private ActivityAnnouncement mainActivity;
    private Announcement announcement;
    private TextView confTitle, confContent, confGrade, confDate;
    private AnnouncementUtils announcementUtils;

    public FragmentConferenceDetail(Announcement announcement) {
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
        View view = inflater.inflate(R.layout.fragment_conferencedetail, container, false);
        initComponents(view);
        pushAnnouncementOnWidget();
        return view;
    }

    private void pushAnnouncementOnWidget() {
        confTitle.setText(announcement.getAnnouncementTitle());
        confContent.setText(announcementUtils.getPositionContent(announcement.getAnnouncementContent(),0));
        confGrade.setText(announcementUtils.getAnnouncementContentClass(announcementUtils.getPositionContent(announcement.getAnnouncementContent(),2)));
        confDate.setText(announcementUtils.convertTimeToVN(announcement.getAnnouncementDate())+" "+announcementUtils.convertDateToVN(announcement.getAnnouncementDate()));
    }

    private void initComponents(View view) {
        confTitle = (TextView)view.findViewById(R.id.conftitle);
        confContent = (TextView)view.findViewById(R.id.confcontent);
        confGrade = (TextView)view.findViewById(R.id.confgrade);
        confDate = (TextView)view.findViewById(R.id.confdate);
        announcementUtils = new AnnouncementUtils();
    }
}