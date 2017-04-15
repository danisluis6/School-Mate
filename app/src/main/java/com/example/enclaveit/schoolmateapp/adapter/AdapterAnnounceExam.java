package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.libraries.AnnouncementUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vuongluis on 05/11/2016.
 */

public class AdapterAnnounceExam extends BaseExpandableListAdapter{

    private Context context;
    private List<String> listHeaders;
    private HashMap<String,List<Announcement>> listData;
    private AnnouncementUtils announcementUtils;

    public AdapterAnnounceExam(Context context, List<String> listHeaders, HashMap<String,List<Announcement>> listData){
        this.context = context;
        this.listHeaders = listHeaders;
        this.listData = listData;
        announcementUtils = new AnnouncementUtils();
    }

    @Override
    public int getGroupCount() {
        return this.listHeaders.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.listData.get(this.listHeaders.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.listHeaders.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.listData.get(this.listHeaders.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        ViewHolderParent viewHolder;
        if(view == null){
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_expandlistview, null);

            viewHolder = new ViewHolderParent();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageTitle);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderParent) view.getTag();
        }
        viewHolder.title.setTypeface(null, Typeface.BOLD);
        viewHolder.title.setText(headerTitle);

        /** Switch subject with other icon */
        switch(headerTitle){
            case "Art":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_art);
                break;
            case "Biology":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_biology);
                break;
            case "Chemistry":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_chemistry);
                break;
            case "Civic Education":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_civic);
                break;
            case "English":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_english);
                break;
            case "Geography":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_geography);
                break;
            case "History":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_history);
                break;
            case "Informatics":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_infor);
                break;
            case "Literature":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_lit);
                break;
            case "Mathematics":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_math);
                break;
            case "Music":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_music);
                break;
            case "Physical Education":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_physicaledu);
                break;
            case "Physics":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_physic);
                break;
            case "Science":
                viewHolder.imageView.setImageResource(R.drawable.anouce_icon_science);
                break;
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        final Announcement childText = (Announcement) getChild(i, i1);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_listview_test, null);

            holder = new ViewHolder();
            holder.iconView = (ImageView) view.findViewById(R.id.icon_exam);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.time = (TextView)view.findViewById(R.id.date);
            holder.imageView = (ImageView)view.findViewById(R.id.imageDetail);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.title.setText(childText.getAnnouncementTitle());
        holder.iconView.setImageResource(announcementUtils.getTypeOfIconExam(announcementUtils.getPositionContent(childText.getAnnouncementContent(),4)));
        holder.time.setText(announcementUtils.convertTimeToVN(childText.getAnnouncementDate())+" "+announcementUtils.convertDateToVN(childText.getAnnouncementDate()));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static class ViewHolder {
        ImageView iconView;
        ImageView imageView;
        TextView title;
        TextView time;
    }

    public static class ViewHolderParent {
        ImageView imageView;
        TextView title;
    }
}
