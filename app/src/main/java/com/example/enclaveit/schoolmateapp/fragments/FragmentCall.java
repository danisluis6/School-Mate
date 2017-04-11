package com.example.enclaveit.schoolmateapp.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityChat;

/**
 * Created by enclaveit on 02/03/2017.
 */

public class FragmentCall extends Fragment {

    private ActivityChat mainActivity;

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
        
        getContactList();
        return view;
    }

    public Object getContactList() {
        Cursor cursor = mainActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        return cursor;
    }
}
