package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Contact;

import java.util.List;


/**
 * Created by vuongluis on 05/11/2016.
 */

public class AdapterChatCall extends ArrayAdapter<Contact> {

    private List<Contact> listItems;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterChatCall(Context context, List<Contact> listItems){
        super(context, 0, listItems);
        this.context = context;
        this.listItems = listItems;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (listItems.isEmpty()) {
            return 0;
        }
        return listItems.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        final Contact contact = listItems.get(i);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_listview_contact, null);
            holder = new ViewHolder();
            holder.contactName = (TextView) view.findViewById(R.id.contactName);
            holder.contactPhone = (TextView) view.findViewById(R.id.contactPhone);
            holder.contactPhoto = (ImageView) view.findViewById(R.id.contactPhoto);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.contactName.setText(contact.getContactName());
        holder.contactPhone.setText("+84972248187");
        holder.contactPhoto.setImageResource(R.drawable.anouce_icon_15);
        return view;
    }

    public static class ViewHolder {
        TextView contactName, contactPhone;
        ImageView contactPhoto;
    }
}
