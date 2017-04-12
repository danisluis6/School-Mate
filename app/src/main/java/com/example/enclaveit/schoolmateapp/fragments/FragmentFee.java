package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private FragmentFeeDetail feefragmentHome;

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

        Log.d("TAG","Running....!");

        listOfFee = (ListView) view.findViewById(R.id.listOfFee);
        adapterAnnouncement = new AdapterAnnounceFee(mainActivity, arrayAnnouncementSchoolFees);
        listOfFee.setAdapter(adapterAnnouncement);

        // Set event of ListView
        listOfFee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announcement announcement = (Announcement) parent.getItemAtPosition(position);
                if(establishFragmentsAndroid(announcement)) {
                    switchFragment(feefragmentHome, false, R.id.fragment_fee);
                }
            }
        });
        return view;
    }

    /** Initialize fragment */
    private boolean establishFragmentsAndroid(Announcement announcement) {
        boolean valid = true;
        try{
            feefragmentHome = new FragmentFeeDetail(announcement);
        }catch (Exception ex){
            valid = false;
            ex.printStackTrace();
        }
        return valid;
    }

    /** Initialize object FragmentManger to manager fragment */
    private void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        /** Animcation android */
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        if(!ft.isEmpty()){
            /** NOT TODO */
        }else{
            ft.replace(id,fragment);
        }
        ft.addToBackStack("");
        ft.commit();
    }
}
