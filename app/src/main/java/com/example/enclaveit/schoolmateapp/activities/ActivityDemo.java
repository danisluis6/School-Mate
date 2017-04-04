package com.example.enclaveit.schoolmateapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.adapter.AdapterDemoList;
import com.example.enclaveit.schoolmateapp.bean.Device;

import java.util.ArrayList;
import java.util.List;

public class ActivityDemo extends AppCompatActivity {

    private ListView listView;
    private AdapterDemoList adapter;
    private List<Device> deviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        listView = (ListView)this.findViewById(R.id.listView);
        deviceList = new ArrayList<>();

        Device device = new Device("ARMv7 Processor rev 0","MSM","4","384 MHz","swp","odemand","3.4.5 ","armv7");;
        deviceList.add(device);

        adapter = new AdapterDemoList(ActivityDemo.this,deviceList);
        listView.setAdapter(adapter);
    }
}
