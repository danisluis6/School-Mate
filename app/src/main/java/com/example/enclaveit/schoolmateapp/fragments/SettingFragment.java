package com.example.enclaveit.schoolmateapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enclaveit.schoolmateapp.R;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savesInstanceState){
        return inflater.inflate(R.layout.setting_fragment_layout, container, false);
    }
}
