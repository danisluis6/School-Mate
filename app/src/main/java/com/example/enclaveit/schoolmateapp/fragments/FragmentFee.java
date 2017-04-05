package com.example.enclaveit.schoolmateapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.adapter.AdapterAnnounceFee;
import com.example.enclaveit.schoolmateapp.alert.AlertDialogFee;
import com.example.enclaveit.schoolmateapp.bean.Announcement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentFee extends Fragment{

    private ListView listOfFee;
    private List<Announcement> arrayAnnouncementSchoolFees = new ArrayList<>();

    private AdapterAnnounceFee adapterAnnouncement;
    private ActivityAnnouncement mainActivity;
    private String TAG = FragmentFee.this.getTag();

    public FragmentFee(List<Announcement> agreeschoolfee) {
        this.arrayAnnouncementSchoolFees = agreeschoolfee;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ActivityAnnouncement){
            this.mainActivity = (ActivityAnnouncement)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_fee, container, false);
        listOfFee = (ListView)view.findViewById(R.id.listOfFee);

//        adapterAnnouncement = new AdapterAnnounceFee(mainActivity,arrayAnnouncementSchoolFees);
//        listOfFee.setAdapter(adapterAnnouncement);

        setFragment(new FragmentFeeHome(arrayAnnouncementSchoolFees,adapterAnnouncement,listOfFee));

        // Set event of ListView
        listOfFee.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announcement announcement = (Announcement) parent.getItemAtPosition(position);
                AlertDialogFee.onCreateDialog(mainActivity,announcement).show();
                setFragment(new FragmentConference());
            }
        });
        return view;
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction t = mainActivity.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_container, fragment);
        t.commit();
    }
}
