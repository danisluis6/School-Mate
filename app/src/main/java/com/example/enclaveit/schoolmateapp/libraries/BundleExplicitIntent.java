package com.example.enclaveit.schoolmateapp.libraries;

import android.content.Intent;
import android.os.Bundle;

import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.bean.Subject;

/**
 * Created by enclaveit on 07/04/2017.
 */

public class BundleExplicitIntent {
    public Intent getAnnouncement(Intent intent, Announcement announcement){
        Bundle bundle = new Bundle();
        bundle.putString("ID",String.valueOf(announcement.getAnnouncementID()));
        bundle.putString("Title",String.valueOf(announcement.getAnnouncementTitle()));
        bundle.putString("Description",String.valueOf(announcement.getAnnouncementDescription()));
        bundle.putString("Content",String.valueOf(announcement.getAnnouncementContent()));
        bundle.putString("Date",String.valueOf(announcement.getAnnouncementDate()));
        bundle.putString("UserID",String.valueOf(announcement.getAnnouncementUserID()));
        intent.putExtra("Announcement",bundle);
        return intent;
    }
    public Intent getSubject(Intent intent, Subject subject){
        Bundle bundle = new Bundle();
        bundle.putString("subjectID",String.valueOf(subject.getSubjectID()));
        bundle.putString("subjectName",String.valueOf(subject.getSubjectName()));
        intent.putExtra("Subject",bundle);
        return intent;
    }
}
