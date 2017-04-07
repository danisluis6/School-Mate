package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.adapter.AdapterAnnounceTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentExamination extends Fragment {

    private ActivityAnnouncement mainActivity;
    private ExpandableListView listOfTest;

    private AdapterAnnounceTest adapter;
    private List<String> listHeader;
    private HashMap<String,List<String>> listData;

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
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        // Get the Object Widget ListView
        listOfTest = (ExpandableListView) view.findViewById(R.id.listOfTest);

        // Prepare List Data, Using global variable
        prepareListData();
        // set Adapter
        adapter = new AdapterAnnounceTest(mainActivity,listHeader,listData);
        listOfTest.setAdapter(adapter);

        // not using event

        return view;

    }

    protected void prepareListData(){
        listHeader = new ArrayList<String>();
        listData = new HashMap<String, List<String>>();

        // Adding child data
        listHeader.add("Art");
        listHeader.add("Biology");
        listHeader.add("Chemistry");
        listHeader.add("Civic Education");
        listHeader.add("English");
        listHeader.add("Geography");
        listHeader.add("History");
        listHeader.add("Informatics");
        listHeader.add("Literature");
        listHeader.add("Mathematics");
        listHeader.add("Music");
        listHeader.add("Physical Education");
        listHeader.add("Physics");
        listHeader.add("Science");


        // Adding Art
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");

        listData.put(listHeader.get(0), top250);
        listData.put(listHeader.get(1), top250);
        listData.put(listHeader.get(2), top250);
        listData.put(listHeader.get(3), top250);
        listData.put(listHeader.get(4), top250);
        listData.put(listHeader.get(5), top250);
        listData.put(listHeader.get(6), top250);
        listData.put(listHeader.get(7), top250);
        listData.put(listHeader.get(8), top250);
        listData.put(listHeader.get(9), top250);
        listData.put(listHeader.get(10), top250);
        listData.put(listHeader.get(11), top250);
        listData.put(listHeader.get(12), top250);
        listData.put(listHeader.get(13), top250);
    }
}
