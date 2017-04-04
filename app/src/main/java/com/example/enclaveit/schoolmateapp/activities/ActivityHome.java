package com.example.enclaveit.schoolmateapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.adapter.AdapterHomeList;
import com.example.enclaveit.schoolmateapp.bean.Category;
import com.example.enclaveit.schoolmateapp.config.Config;
import com.example.enclaveit.schoolmateapp.libraries.ActivityBase;
import com.example.enclaveit.schoolmateapp.libraries.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends ActivityBase{

    private ListView listView;
    private AdapterHomeList adapter;
    private List<Category> categoryList;
    private Context context;

    private static final String TAG = ActivityHome.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = ActivityHome.this;

        listView = (ListView)this.findViewById(R.id.list);
        categoryList = new ArrayList<>();

        categoryList.add(new Category(R.drawable.main_timetable,"Timetable"));
        categoryList.add(new Category(R.drawable.main_announcement,"Announcement"));
        categoryList.add(new Category(R.drawable.main_bustracking,"Bus Tracking"));
        categoryList.add(new Category(R.drawable.main_chatting,"Chatting"));
        categoryList.add(new Category(R.drawable.main_homework,"Home Work"));
        categoryList.add(new Category(R.drawable.main_report,"Report"));

        adapter = new AdapterHomeList(ActivityHome.this,categoryList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Category category = (Category) parent.getItemAtPosition(position);

            switch (category.getResId()){
                case R.drawable.main_timetable:
                    startActivity(new Intent(ActivityHome.this,ActivityTimeTable.class));
                    break;
                case R.drawable.main_announcement:
                    startActivity(new Intent(ActivityHome.this,ActivityAnnoucement.class));
                    break;
                case R.drawable.main_bustracking:
                    startActivity(new Intent(ActivityHome.this,ActivityBusTracking.class));
                    break;
                case R.drawable.main_chatting:
                    startActivity(new Intent(ActivityHome.this,ActivityChat.class));
                    break;
                case R.drawable.main_homework:
                    startActivity(new Intent(ActivityHome.this,ActivityAnnoucement.class));
                    break;
                case R.drawable.main_report:
                    startActivity(new Intent(ActivityHome.this,ActivityReport.class));
                    break;
            };
            }
        });

        /**
         * Receive notification from BroandCast use BroadCastReceiver
         */
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    displayFirebaseRegId();
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ActivityHome.this, "BroadcastReceiver is null", Toast.LENGTH_SHORT).show();
                }
            }
        };
        displayFirebaseRegId();

    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        String regId = pref.getString(getString(R.string.FCM_TOKEN), null);
        Log.d("TOKEN",String.valueOf(regId));
        if (!TextUtils.isEmpty(regId)) {
        }else {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
