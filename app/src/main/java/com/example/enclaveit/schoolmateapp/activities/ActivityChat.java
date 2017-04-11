package com.example.enclaveit.schoolmateapp.activities;

import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.fragments.FragmentCall;
import com.example.enclaveit.schoolmateapp.libraries.ViewPagerAdapter;

public class ActivityChat extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        initComponents();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#83AEA5")));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Announcement");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    public void setupViewPager(ViewPager upViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        String[] typeofFeatures = getResources().getStringArray(R.array.typeoffeatures);
        adapter.addFragment(new FragmentCall(),typeofFeatures[0]);
        adapter.addFragment(new FragmentCall(),typeofFeatures[1]);
        adapter.addFragment(new FragmentCall(),typeofFeatures[2]);
        viewPager.setAdapter(adapter);
    }

    /** Load menu of Toolbar **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chatting, menu);
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
                    Toast.makeText(ActivityChat.this, "Permission denied on this device!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
