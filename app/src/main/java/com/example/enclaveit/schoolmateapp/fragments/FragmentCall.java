package com.example.enclaveit.schoolmateapp.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityChat;
import com.example.enclaveit.schoolmateapp.adapter.AdapterChatCall;
import com.example.enclaveit.schoolmateapp.bean.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentCall extends Fragment {

    private ActivityChat mainActivity;
    private List<Contact> contacts;
    private ListView listOfContacts;
    private EditText inputSearch;
    private AdapterChatCall adapter;

    private FragmentCallDetail callfragmentHome;

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

        // Soft ListView
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                String nameFirst = o1.getContactName();
                String nameSecond = o2.getContactName();
                return nameFirst.compareTo(nameSecond);
            }
        });

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

        listOfContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) parent.getItemAtPosition(position);
                if(establishFragmentsAndroid(contact)) {
                    switchFragment(callfragmentHome, false, R.id.fragment_fee);
                }
            }
        });

        return view;
    }

    private void initComponents(View view) {
        contacts = new ArrayList<>();
        contacts = getAllContacts();
        adapter = new AdapterChatCall(mainActivity,contacts);
        listOfContacts = (ListView) view.findViewById(R.id.listOfContacts);
        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
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
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phone = "";
                Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
                if(pCur.moveToNext()) {
                    phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                pCur.close();
                int photo = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                allContacts.add(new Contact(name,phone,photo));
            }
        }
        return allContacts;
    }

    /** Initialize fragment */
    private boolean establishFragmentsAndroid(Contact contact) {
        boolean valid = true;
        try{
            callfragmentHome = new FragmentCallDetail(contact);
        }catch (Exception ex){
            valid = false;
            ex.printStackTrace();
        }
        return valid;
    }

    /** Initialize object FragmentManger to manager fragment */
    private void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        FragmentManager fm = mainActivity.getSupportFragmentManager();
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
