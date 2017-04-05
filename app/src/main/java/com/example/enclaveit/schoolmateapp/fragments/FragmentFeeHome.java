package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.adapter.AdapterAnnounceFee;
import com.example.enclaveit.schoolmateapp.bean.Announcement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentFeeHome extends Fragment{

    private ListView listOfFee;
    private AdapterAnnounceFee adapterAnnounceFee;
    private List<Announcement> arrayAnnouncementSchoolFees = new ArrayList<>();

    private AdapterAnnounceFee adapterAnnouncement;
    private ActivityAnnouncement mainActivity;

    public FragmentFeeHome(List<Announcement> agreeschoolfee, AdapterAnnounceFee adapterAnnounceFee, ListView listOfFee) {
        this.listOfFee = listOfFee;
        this.adapterAnnounceFee = adapterAnnounceFee;
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
        adapterAnnouncement = new AdapterAnnounceFee(mainActivity,arrayAnnouncementSchoolFees);
        listOfFee.setAdapter(adapterAnnouncement);
        return view;
    }
}
