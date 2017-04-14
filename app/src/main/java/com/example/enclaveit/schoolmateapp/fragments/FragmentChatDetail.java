package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityChat;
import com.example.enclaveit.schoolmateapp.bean.Teacher;

/**
 * Created by enclaveit on 10/04/2017.
 */

public class FragmentChatDetail extends Fragment{

    private ActivityChat mainActivity;
    private Teacher teacher;
    private TextView txt;

    public FragmentChatDetail(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ActivityChat){
            this.mainActivity = (ActivityChat) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chatdetail, container, false);
        /** TODO */
        txt = (TextView)view.findViewById(R.id.lorence);
        return view;
    }
}