package com.example.enclaveit.schoolmateapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.adapter.AdapterChatting;
import com.example.enclaveit.schoolmateapp.asynctasks.JSONTeacher;
import com.example.enclaveit.schoolmateapp.bean.Teacher;
import com.example.enclaveit.schoolmateapp.config.ConfigLog;
import com.example.enclaveit.schoolmateapp.config.ConfigURL;

import com.example.enclaveit.schoolmateapp.fragments.FragmentChatDetail;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityChat extends AppCompatActivity implements JSONTeacher.AsyncResponse{

    private Toolbar toolbar;
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private JSONTeacher jsonTeacher;
    private AdapterChatting adapter;
    private ListView listOfContacts;
    private EditText inputSearch;

    private FragmentChatDetail teacherfragmentHome;

    /** Check permission for Android with version >= 6.0 **/
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (isGranted) {
            startApplication();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
        }
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_CODE);
    }

    public void startApplication() {
        initComponents();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorStatusBar2));

        setContentView(R.layout.activity_chat);

        if (checkPermissions()) {
            startApplication();
        } else {
            setPermissions();
        }
    }

    private void initComponents() {
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        this.setSupportActionBar(toolbar);
        toolbar.setTitle("Chatting");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);
        listOfContacts = (ListView)this.findViewById(R.id.listOfContacts);
        inputSearch = (EditText)this.findViewById(R.id.inputSearch);

        jsonTeacher = new JSONTeacher(ActivityChat.this);
        jsonTeacher.execute(ConfigURL.urlTeachers);
        jsonTeacher.delegateTeacher = this;
    }

    @Override
    public void getListTeacher(List<Teacher> output) {
        try{
            Log.d("TAG","N = "+output.size());
            // Soft ListView
            Collections.sort(output, new Comparator<Teacher>() {
                @Override
                public int compare(Teacher o1, Teacher o2) {
                    String nameFirst = o1.getTeacherName();
                    String nameSecond = o2.getTeacherName();
                    return nameFirst.compareTo(nameSecond);
                }
            });

            // Set data for AdapterChatting
            adapter = new AdapterChatting(this,output);

            // Show contacts on ListView
            listOfContacts.setAdapter(adapter);
            inputSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    /** TODO */
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    /** TODO */
                    adapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            // Catch event of Listview
            listOfContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Teacher teacher = (Teacher) parent.getItemAtPosition(position);
                    if(establishFragmentsAndroid(teacher)) {
                        switchFragment(teacherfragmentHome, false, R.id.fragment_contact);
                    }
                }
            });

        }catch (Exception ex){
            Log.d("ActivityChatting", ConfigLog.ErrorGetRootListAnnouncement);
        }
    }

    /** Load menu of Toolbar **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chatting, menu);
        return true;
    }

    /** Return ActivityHome **/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /** Initialize fragment */
    private boolean establishFragmentsAndroid(Teacher teacher) {
        boolean valid = true;
        try{
            teacherfragmentHome = new FragmentChatDetail(teacher);
        }catch (Exception ex){
            valid = false;
            ex.printStackTrace();
        }
        return valid;
    }

    /** Initialize object FragmentManger to manager fragment */
    private void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        FragmentManager fm = this.getSupportFragmentManager();
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
