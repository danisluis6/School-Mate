package com.example.enclaveit.schoolmateapp.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityChat;
import com.example.enclaveit.schoolmateapp.adapter.AdapterChatCall;
import com.example.enclaveit.schoolmateapp.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentCall extends Fragment {

    private ActivityChat mainActivity;
    private List<Contact> contacts;
    private ListView listOfContacts;
    private AdapterChatCall adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ActivityChat){
            this.mainActivity = (ActivityChat) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savesInstanceState){
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        initComponents(view);
        // Show contacts on ListView
        listOfContacts.setAdapter(adapter);
        return view;
    }

    private void initComponents(View view) {
        contacts = new ArrayList<>();
        contacts = getAllContacts();
        adapter = new AdapterChatCall(mainActivity,contacts);
        listOfContacts = (ListView) view.findViewById(R.id.listOfContacts);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public List<Contact> getAllContacts() {
        List<Contact> allContacts = new ArrayList<>();
        ContentResolver contentResolver = mainActivity.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                int photo = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                allContacts.add(new Contact(name,phone,photo));
            }
        }
        return allContacts;
    }
}
