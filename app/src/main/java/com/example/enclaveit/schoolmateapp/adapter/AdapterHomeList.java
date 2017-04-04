package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Category;

import java.util.List;


/**
 * Created by vuongluis on 05/11/2016.
 */

public class AdapterHomeList extends BaseAdapter {

    private List<Category> listItems;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterHomeList(Context context, List<Category> listItems){
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
        Category category = (Category)getItem(i);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_home, null);
            holder = new ViewHolder();

            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.icon.setImageResource(category.getResId());
        holder.title.setText(category.getTitle());
        return view;
    }

    public static class ViewHolder {
        ImageView icon;
        TextView title;
    }
}
