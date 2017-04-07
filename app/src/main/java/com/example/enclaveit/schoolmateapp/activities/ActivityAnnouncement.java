package com.example.enclaveit.schoolmateapp.activities;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.asynctasks.AsyncTaskAnnouncement;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.config.ConfigLog;
import com.example.enclaveit.schoolmateapp.config.ConfigURL;
import com.example.enclaveit.schoolmateapp.fragments.FragmentActivity;
import com.example.enclaveit.schoolmateapp.fragments.FragmentConference;
import com.example.enclaveit.schoolmateapp.fragments.FragmentExamination;
import com.example.enclaveit.schoolmateapp.fragments.FragmentFee;
import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementUtils;
import com.example.enclaveit.schoolmateapp.libraries.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityAnnouncement extends AppCompatActivity implements AsyncTaskAnnouncement.AsyncResponse{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AnnouncementUtils announcementUtils;

    private AsyncTaskAnnouncement announcementAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annoucement);
        initComponents();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initComponents() {
        /** toolbar **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Announcement");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** ViewPaper */
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        /** AsyncTask To get Json from website */
        announcementAsyncTask = new AsyncTaskAnnouncement(ActivityAnnouncement.this);
        announcementAsyncTask.execute(ConfigURL.urlAnnouncements);

        /** Initialize object for interface AsyncTask  **/
        announcementAsyncTask.delegate = this;
    }

    @Override
    public void processFinish(List<Announcement> output) {
        try{
            /** Initialize object announcement */
            announcementUtils = new AnnouncementUtils(ActivityAnnouncement.this,output);

            /** Running ViewPager **/
            setupViewPager(viewPager,announcementUtils);
        }catch (Exception ex){
            Log.d("ActivityAnnouncement", ConfigLog.ErrorGetRootListAnnouncement);
        }
    }

    /**
     * Loading libraries.ViewPagerAdapter
     * ArrayList<String>
     * @param viewPager
     * @param announcementUtils
     */
    private void setupViewPager(ViewPager viewPager, AnnouncementUtils announcementUtils) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        String[] typeofAnnouncements = getResources().getStringArray(R.array.typeofannouncements);
        adapter.addFragment(new FragmentFee(announcementUtils.getArrayAnnouncement("agreeschoolfee")),typeofAnnouncements[0]);
        adapter.addFragment(new FragmentExamination(),typeofAnnouncements[1]);
        adapter.addFragment(new FragmentConference(),typeofAnnouncements[2]);
        adapter.addFragment(new FragmentActivity(),typeofAnnouncements[3]);
        viewPager.setAdapter(adapter);
    }

    /** Load menu of Toolbar **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anoucement, menu);
        return true;
    }

    /** Return ActivityHome **/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /** Check permission for Android with version >= 6.0 **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(ActivityAnnouncement.this, "Permission denied on this device!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}