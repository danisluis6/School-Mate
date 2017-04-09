package com.example.enclaveit.schoolmateapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.adapter.AdapterHomeList;
import com.example.enclaveit.schoolmateapp.bean.Feature;
import com.example.enclaveit.schoolmateapp.config.Config;
import com.example.enclaveit.schoolmateapp.libraries.ActivityBase;
import com.example.enclaveit.schoolmateapp.libraries.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends ActivityBase{

    private RecyclerView recyclerView;
    private AdapterHomeList adapter;
    private List<Feature> featureList;
    private Context context;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = ActivityHome.this;

        /**-- insert old data */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        featureList = new ArrayList<>();
        adapter = new AdapterHomeList(this, featureList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareFeatures();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // Register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // Clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
          * Adding few albums for testing
          */
    private void prepareFeatures() {
        int[] covers = new int[]{
            R.drawable.feature1,
            R.drawable.feature2,
            R.drawable.feature3,
            R.drawable.feature4,
            R.drawable.feature5,
            R.drawable.feature6};

        Feature a = new Feature("Timetable", covers[0]);
        featureList.add(a);

        a = new Feature("Announcement", covers[1]);
        featureList.add(a);

        a = new Feature("Bus Tracking", covers[2]);
        featureList.add(a);

        a = new Feature("Chatting", covers[3]);
        featureList.add(a);

        a = new Feature("Home Work", covers[4]);
        featureList.add(a);

        a = new Feature("Report", covers[5]);
        featureList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
      * Converting dp to pixel
      */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
