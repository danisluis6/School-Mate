package com.example.enclaveit.schoolmateapp.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.asynctasks.GetAnnouncements;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.fragments.FragmentActivity;
import com.example.enclaveit.schoolmateapp.fragments.FragmentConference;
import com.example.enclaveit.schoolmateapp.fragments.FragmentExamination;
import com.example.enclaveit.schoolmateapp.fragments.FragmentFee;
import com.example.enclaveit.schoolmateapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityAnnoucement extends AppCompatActivity implements GetAnnouncements.AsyncResponse{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GetAnnouncements announcementAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annoucement);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

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
        announcementAsyncTask = new GetAnnouncements(ActivityAnnoucement.this);
        announcementAsyncTask.execute("https://lorenceuniversity.000webhostapp.com/");

        /** **/
        announcementAsyncTask.delegate = this;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentFee(), "Học Phí");
        adapter.addFragment(new FragmentExamination(), "Thi Cử");
        adapter.addFragment(new FragmentConference(), "Họp Hành");
        adapter.addFragment(new FragmentActivity(), "Hoạt Động");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void processFinish(List<Announcement> output) {
        Log.d("TAG","Announcements: "+output.size());
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(ActivityAnnoucement.this, "Permission denied on this device!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anoucement, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
