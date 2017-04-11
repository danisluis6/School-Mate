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

public class FragmentFeeDetail extends Fragment{

    private ActivityAnnouncement mainActivity;
    private Announcement announcement;
    private TextView feeTitle, feeContent, feeGrade, feeDate;
    private AnnouncementUtils announcementUtils;

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
        feeTitle.setText(announcement.getAnnouncementTitle());
        feeContent.setText(announcementUtils.getPositionContent(announcement.getAnnouncementContent(),0));
        feeGrade.setText(announcementUtils.getAnnouncementContentClass(announcementUtils.getPositionContent(announcement.getAnnouncementContent(),2)));
        feeDate.setText(announcementUtils.convertTimeToVN(announcement.getAnnouncementDate())+" "+announcementUtils.convertDateToVN(announcement.getAnnouncementDate()));
    }

    private void initComponents(View view) {
        feeTitle = (TextView)view.findViewById(R.id.feetitle);
        feeContent = (TextView)view.findViewById(R.id.feecontent);
        feeGrade = (TextView)view.findViewById(R.id.feegrade);
        feeDate = (TextView)view.findViewById(R.id.feedate);
        announcementUtils = new AnnouncementUtils();
    }
}