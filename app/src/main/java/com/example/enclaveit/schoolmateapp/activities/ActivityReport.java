package com.example.enclaveit.schoolmateapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.fragments.ChartReportFragment;
import com.example.enclaveit.schoolmateapp.fragments.FigureReportFragment;
import com.example.enclaveit.schoolmateapp.libraries.ActivityBase;

/**
 * Created by PHA on 07/03/2017.
 */

public class ActivityReport extends ActivityBase{
    FragmentTabHost tabHostTypeOfReport;
    TabWidget tabsType;

    @Override
    protected void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_report);
        Log.i("REPORT_ACTIVITY", "On Create Report ActivityTimeTable");
        //set Toolbar Title
        setToolbarTitle(getResources().getString(R.string.progress_report));

        tabHostTypeOfReport = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHostTypeOfReport.setup(ActivityReport.this, getSupportFragmentManager(), android.R.id.tabcontent);
            /*Add Fragment*/
        tabHostTypeOfReport.addTab(tabHostTypeOfReport.newTabSpec("Figure").setIndicator("Figure"),
                new FigureReportFragment().getClass(), null);
        tabHostTypeOfReport.addTab(tabHostTypeOfReport.newTabSpec("Chart").setIndicator("Chart"),
                new ChartReportFragment().getClass(), null);
        tabHostTypeOfReport.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.tab_Selected));
        tabsType = (TabWidget) findViewById(android.R.id.tabs);
        tabHostTypeOfReport.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int tabIndex = tabHostTypeOfReport.getCurrentTab();
                for (int i = 0; i < tabHostTypeOfReport.getTabWidget().getChildCount(); i++) {
                    tabHostTypeOfReport.getTabWidget().getChildAt(i)
                            .setBackgroundColor(getResources().getColor(R.color.tab_UnSelected)); // unselected
                }
                //Set background color for tab selected
                tabHostTypeOfReport.getTabWidget().getChildAt(tabIndex).setBackgroundColor(getResources().getColor(R.color.tab_Selected));
            }
        });

    }
}