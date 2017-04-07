package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.adapter.AdapterAnnounceTest;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.bean.Subject;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentExamination extends Fragment{

    private ActivityAnnouncement mainActivity;
    private ExpandableListView listOfExam;
    private AnnouncementUtils announcementUtils;
    private List<Announcement> arrayAnnouncementSchoolFees = new ArrayList<>();

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
        intiComponents(view);

//        announcementUtils = new AnnouncementUtils(mainActivity,arrayAnnouncementSchoolFees){
//            @Override
//            public void getListSubject(List<Subject> output) {
//                super.getListSubject(output);
//                /** Intialize data for ListHeader */
//                initListHeader(output);
//                /** */
//            }
//        };

        prepareListData();

        adapter = new AdapterAnnounceTest(mainActivity,listHeader,listData);
        listOfExam.setAdapter(adapter);
        return view;

    }

    private void initListHeader(List<Subject> output) {
        for(int index = 0; index < output.size(); index++){
            if(output.get(index).getSubjectName().equals("Classroom Activities") || output.get(index).getSubjectName().equals("Vacation") || output.get(index).getSubjectName().equals("Vacation") || output.get(index).getSubjectName().equals("Assembly")){
                /** TODO */
                break;
            }else{
                listHeader.add(output.get(index).getSubjectName());
            }
        }
    }

    protected void prepareListData(){
        // Add Header
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

    private void intiComponents(View view) {
        listOfExam = (ExpandableListView) view.findViewById(R.id.listOfTest);
        listHeader = new ArrayList<String>();
        listData = new HashMap<String, List<String>>();
    }
}