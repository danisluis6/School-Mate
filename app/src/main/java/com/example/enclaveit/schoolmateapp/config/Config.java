package com.example.enclaveit.schoolmateapp.config;

/**
 * Created by enclaveit on 15/03/2017.
 * Setting Constant for application
 * - Log.d(TAG,"...");
 */

public class Config {
    // global topic to receive app wide push notification
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
}
