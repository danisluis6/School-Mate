package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnoucement;
import com.example.enclaveit.schoolmateapp.adapter.AdapterAnnounceConfer;
import com.example.enclaveit.schoolmateapp.bean.Announcement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentConference extends Fragment {

    private ListView listOfConfer;
    private List<Announcement> listItems;

    private AdapterAnnounceConfer adapterAnnouncement;
    private ActivityAnnoucement mainActivity;

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
        View view = inflater.inflate(R.layout.fragment_conference, container, false);
        listOfConfer = (ListView)view.findViewById(R.id.listOfConfer);

        listItems = new ArrayList<>();
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;
        listItems.add(new Announcement("Tuổi trẻ Trung cấp Dược với biển đảo quê hương","Nhằm khơi dậy niềm tự hào dân tộc, tự hào về truyền thống 84 năm xây dựng, cống hiến và trưởng thành của Đoàn TNCS Hồ Chí Minh trong mỗi cán bộ, đoàn viên, thanh niên."));;

        adapterAnnouncement = new AdapterAnnounceConfer(mainActivity,listItems);
        listOfConfer.setAdapter(adapterAnnouncement);

        return view;
    }
}
