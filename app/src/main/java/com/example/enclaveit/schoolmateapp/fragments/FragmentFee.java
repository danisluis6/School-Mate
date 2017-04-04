package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnoucement;
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
    private List<Announcement> listItems = new ArrayList<>();

    private AdapterAnnounceFee adapterAnnouncement;
    private ActivityAnnoucement mainActivity;
    private String TAG = FragmentFee.this.getTag();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ActivityAnnoucement){
            this.mainActivity = (ActivityAnnoucement)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_fee, container, false);
        listOfFee = (ListView)view.findViewById(R.id.listOfFee);

//        listItems = new ArrayList<>();
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;
//        listItems.add(new Announcement("Thông báo học phí kỳ 2: 2016-2017","Nhà trường triển khai thu học phí kỳ 2 năm học 2017 - 2018"));;

        adapterAnnouncement = new AdapterAnnounceFee(mainActivity,listItems);
        listOfFee.setAdapter(adapterAnnouncement);

        // Set event of ListView
        listOfFee.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announcement announcement = (Announcement) parent.getItemAtPosition(position);
                AlertDialogFee.onCreateDialog(mainActivity,announcement.getAnnouncementTitle(),"Your messages",mainActivity).show();
            }
        });
        return view;
    }
}
