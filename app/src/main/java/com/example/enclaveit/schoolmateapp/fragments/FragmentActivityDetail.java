package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class FragmentActivityDetail extends Fragment{

    private ActivityAnnouncement mainActivity;
    private Announcement announcement;
    private TextView actTitle, actContent, actDate;
    private AnnouncementUtils announcementUtils;

    public FragmentActivityDetail(Announcement announcement) {
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
        View view = inflater.inflate(R.layout.fragment_activitydetail, container, false);
        initComponents(view);
        pushAnnouncementOnWidget();
        return view;
    }

    private void pushAnnouncementOnWidget() {
        actTitle.setText(announcement.getAnnouncementTitle());
        actContent.setText(announcementUtils.getPositionContent(announcement.getAnnouncementContent(),0));
        actDate.setText(announcementUtils.convertTimeToVN(announcement.getAnnouncementDate())+" "+announcementUtils.convertDateToVN(announcement.getAnnouncementDate()));
    }

    private void initComponents(View view) {
        actTitle = (TextView)view.findViewById(R.id.acttitle);
        actContent = (TextView)view.findViewById(R.id.actcontent);
        actDate = (TextView)view.findViewById(R.id.actdate);
        announcementUtils = new AnnouncementUtils();
    }
}