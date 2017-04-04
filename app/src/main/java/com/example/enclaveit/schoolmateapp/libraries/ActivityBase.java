package com.example.enclaveit.schoolmateapp.libraries;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.fragments.FragmentHome;


public class ActivityBase extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private View navHeader;
    private ImageButton btnChangeChild;
    private Toolbar my_toolbar;


    private static int navCurrentItemIndex = 0;

    /*tags used to attach the fragments*/
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_CHILDREN = "children";
    private static final String TAG_NOTIFICATION = "notifications";
    private static final String TAG_SETTINGS = "settings";


    private static String CURRENT_TAG = TAG_HOME;
    private static boolean IS_MAIN_ACTIVITY = false;


    private String activityTitles[];

    private boolean shouldLoadHomeFragmentOnBackPress = false;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null); // xxx
        FrameLayout activityContainer = (FrameLayout) drawerLayout.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(drawerLayout);
        onCreateDrawer();

    }


    protected void onCreateDrawer() {
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        my_toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        mHandler = new Handler();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        /*Navigation View Header*/
        navHeader = navigationView.getHeaderView(0);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        setUpNavigationView();


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /*Set Toolbar Title*/
    public void setToolbarTitle(String string) {
        getSupportActionBar().setTitle(activityTitles[navCurrentItemIndex]);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navCurrentItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        //Check if user is not at Main ActivityDemo
                        //then move to ActivityHome
                        if (!IS_MAIN_ACTIVITY) {
                            shouldLoadHomeFragmentOnBackPress = true;
                            onBackPressed();
                        }
                        break;
                    case R.id.nav_profile:
                        navCurrentItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_children:
                        navCurrentItemIndex = 2;
                        CURRENT_TAG = TAG_CHILDREN;
                        break;
                    case R.id.nav_notifications:
                        navCurrentItemIndex = 3;
                        CURRENT_TAG = TAG_NOTIFICATION;
                        break;
                    case R.id.nav_settings:
                        navCurrentItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        // TODO -startActivity(new Intent(ActivityHome.this, AboutUsActivity.class));
                        // drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        // TODO -startActivity(new Intent(ActivityHome.this, PrivacyPolicyActivity.class));
                        // drawer.closeDrawers();
                        return true;
                    default:
                        navCurrentItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                }
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });
    }

    /*Returns respected fragment that user selected from navigation menu */
    public void loadHomeFragment() {
        //selecting appropriate nav item menu
        selectNavMenu();

        //set Toolbar Title
        setToolbarTitle(getResources().getString(R.string.bus_tracking));
        /* If user select the current navigation menu again, don't do anything
         *  just close navigation drawer
         */
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout.closeDrawers();
            return;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        final Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        // Closing drawer on item click
        drawerLayout.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navCurrentItemIndex).setChecked(true);
    }

    private Fragment getFragment() {
        switch (navCurrentItemIndex) {
            case 0:
                FragmentHome homeFragment = new FragmentHome();
                return homeFragment;
//            case 1: //profile
//                return null;
//            case 2: // children
//                ChildrenFragment childrenFragment = new ChildrenFragment();
//                return childrenFragment;
//            case 3: // notification
//                NotificationFragment notificationFragment = new NotificationFragment();
//                return notificationFragment;
//            case 4: //setting
//                SettingFragment settingFragment = new SettingFragment();
//                return settingFragment;
            default:
                return new FragmentHome();
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) && !shouldLoadHomeFragmentOnBackPress) {
            drawerLayout.closeDrawers();
            return;
        }
        if (navCurrentItemIndex != 0) {
            navCurrentItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btnChangeChild:
//                break;
        }
    }
}
