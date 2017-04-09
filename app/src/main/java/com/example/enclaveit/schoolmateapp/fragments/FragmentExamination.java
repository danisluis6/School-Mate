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
    private List<Announcement> arrayAnnouncementSchoolExams = new ArrayList<>();

    private AdapterAnnounceTest adapter;
    private List<String> listHeader;
    private HashMap<String,List<Announcement>> listData;

    public FragmentExamination(List<Announcement> agreeschoolexam) {
        this.arrayAnnouncementSchoolExams = agreeschoolexam;
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
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        intiComponents(view);

        announcementUtils = new AnnouncementUtils(mainActivity,arrayAnnouncementSchoolExams){
            @Override
            public void getListSubject(List<Subject> output) {
                super.getListSubject(output);
                /** Intialize data for ListHeader */
                initListHeader(output);
            }
        };
        return view;

    }

    private void initListHeader(List<Subject> output) {
        for(int index = 0; index < output.size(); index++){
            if(output.get(index).getSubjectName().equals("Classroom Activities") || output.get(index).getSubjectName().equals("Vacation") || output.get(index).getSubjectName().equals("Vacation") || output.get(index).getSubjectName().equals("Assembly")){
                /** TODO */
            }else{
                listHeader.add(output.get(index).getSubjectName());
            }
        }
        prepareListData();
        adapter = new AdapterAnnounceTest(mainActivity,listHeader,listData);
        listOfExam.setAdapter(adapter);
    }

    protected void prepareListData(){
        // Adding Art
        List<Announcement> announcementList = new ArrayList<Announcement>();
        announcementList = arrayAnnouncementSchoolExams;

        listData.put(listHeader.get(0), announcementList);
        listData.put(listHeader.get(1), announcementList);
        listData.put(listHeader.get(2), announcementList);
        listData.put(listHeader.get(3), announcementList);
        listData.put(listHeader.get(4), announcementList);
        listData.put(listHeader.get(5), announcementList);
        listData.put(listHeader.get(6), announcementList);
        listData.put(listHeader.get(7), announcementList);
        listData.put(listHeader.get(8), announcementList);
        listData.put(listHeader.get(9), announcementList);
        listData.put(listHeader.get(10), announcementList);
        listData.put(listHeader.get(11), announcementList);
        listData.put(listHeader.get(12), announcementList);
        listData.put(listHeader.get(13), announcementList);
    }

    private void intiComponents(View view) {
        listOfExam = (ExpandableListView) view.findViewById(R.id.listOfTest);
        listHeader = new ArrayList<String>();
        listData = new HashMap<String, List<Announcement>>();
    }
}