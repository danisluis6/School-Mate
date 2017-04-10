package com.example.enclaveit.schoolmateapp.libraries;
/**
 * Created by enclaveit on 07/04/2017.
 */

public interface AnnouncementUsing {
    public String getAnnouncementContent(String temp, int index);
    public String getAnnouncementContentClass(String temp);
    public String convertTimeToVN(String temp);
    public String convertDateToVN(String temp);
}
