package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;

/**
 * Created by enclaveit on 10/04/2017.
 */

public class FragmentFeeHome extends Fragment {

    private ActivityAnnouncement mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ActivityAnnouncement){
            this.mainActivity = (ActivityAnnouncement) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_feedetail, container, false);
        return view;
    }
}
