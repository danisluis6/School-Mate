package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Announcement;

import java.util.List;


/**
 * Created by vuongluis on 05/11/2016.
 */

public class AdapterAnnounceConfer extends BaseAdapter {

    private List<Announcement> listItems;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterAnnounceConfer(Context context, List<Announcement> listItems){
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
        Announcement announcement = (Announcement) getItem(i);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_listview_confer, null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.description = (TextView) view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(announcement.getAnnouncementTitle());
        holder.description.setText(announcement.getAnnouncementDescription());

        return view;
    }

    public static class ViewHolder {
        TextView title;
        TextView description;
    }
}
