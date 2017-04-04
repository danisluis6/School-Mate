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

import java.util.HashMap;
import java.util.List;

/**
 * Created by vuongluis on 05/11/2016.
 */

public class AdapterAnnounceTest extends BaseExpandableListAdapter{

    private Context context;
    private List<String> listHeaders;
    private HashMap<String,List<String>> listData;

    private String TAG = AdapterAnnounceTest.class.getSimpleName();

    public AdapterAnnounceTest(Context context, List<String> listHeaders, HashMap<String,List<String>> listData){
        this.context = context;
        this.listHeaders = listHeaders;
        this.listData = listData;
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
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        final String childText = (String) getChild(i, i1);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_listview_test, null);

            holder = new ViewHolder();
            holder.iconView = (ImageView) view.findViewById(R.id.icon_fee);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.time = (TextView)view.findViewById(R.id.date);
            holder.imageView = (ImageView)view.findViewById(R.id.imageDetail);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.title.setText(childText);
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
