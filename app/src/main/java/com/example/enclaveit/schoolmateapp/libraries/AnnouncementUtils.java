package com.example.enclaveit.schoolmateapp.libraries;

import android.content.Context;

/**
 * Created by vuongluis on 4/4/17.
 */

public class AnnouncementUtils {

    private Context context;
    private static final String TAG = AnnouncementUtils.class.getSimpleName();

    public AnnouncementUtils(Context context){
        this.context = context;
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
