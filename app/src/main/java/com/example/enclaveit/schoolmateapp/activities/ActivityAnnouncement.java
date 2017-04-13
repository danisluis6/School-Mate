package com.example.enclaveit.schoolmateapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.asynctasks.JSONAnnouncement;
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

import java.util.List;

public class ActivityAnnouncement extends AppCompatActivity implements JSONAnnouncement.AsyncResponse{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AnnouncementUtils announcementUtils;
    private JSONAnnouncement jsonAnnouncement;
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;

    /** Check permission for Android with version >= 6.0 **/
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (isGranted) {
            startApplication();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
        }
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_CODE);
    }

    public void startApplication() {
        initComponents();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorStatusBar));

        setContentView(R.layout.activity_annoucement);
        if (checkPermissions()) {
            startApplication();
        } else {
            setPermissions();
        }
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#A6E8F0")));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == tabLayout.getSelectedTabPosition()){
                    tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition() != tabLayout.getSelectedTabPosition()){
                    tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#A6E8F0")));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initComponents() {
        /** toolbar **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Announcement");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        jsonAnnouncement = new JSONAnnouncement(ActivityAnnouncement.this);
        jsonAnnouncement.execute(ConfigURL.urlAnnouncements);
        jsonAnnouncement.delegateAnnouncement = this;
    }

    @Override
    public void getListAnnouncement(List<Announcement> output) {
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
        adapter.addFragment(new FragmentExamination(announcementUtils.getArrayAnnouncement("agreeschoolexam")),typeofAnnouncements[1]);
        adapter.addFragment(new FragmentConference(announcementUtils.getArrayAnnouncement("agreeschoolconference")),typeofAnnouncements[2]);
        adapter.addFragment(new FragmentActivity(announcementUtils.getArrayAnnouncement("agreeschoolactivity")),typeofAnnouncements[3]);
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
}
