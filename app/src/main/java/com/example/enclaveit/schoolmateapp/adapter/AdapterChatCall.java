package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.bean.Contact;

import java.util.List;


/**
 * Created by vuongluis on 05/11/2016.
 */

public class AdapterChatCall extends BaseAdapter {

    private List<Contact> listItems;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterChatCall(Context context, List<Contact> listItems){
        this.context = context;
        this.listItems = listItems;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (listItems.isEmpty() || listItems == null) {
            return 0;
        }
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Contact contact = (Contact) getItem(i);
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
//        holder.contactPhone.setText(contact.getContactPhone());
//        holder.contactPhoto.setImageResource(R.drawable.anouce_icon_15);
        return view;
    }

    public static class ViewHolder {
        TextView contactName, contactPhone;
        ImageView contactPhoto;
    }
}
