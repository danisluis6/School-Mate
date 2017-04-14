package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 12/04/2017.
 */

public class AdapterChatting extends BaseAdapter implements Filterable{

    private LayoutInflater layoutInflater;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    private String filterableString;

    private List<Teacher>originalData = null;
    private List<Teacher>filteredData = null;

    public AdapterChatting(Context context, List<Teacher> listItems){
        this.context = context;
        this.originalData = listItems;
        this.filteredData = listItems;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        Teacher teacher = (Teacher) getItem(i);

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_listview_chat, null);
            holder = new ViewHolder();
            holder.chatName = (TextView) view.findViewById(R.id.chatname);
            holder.chatPhone = (TextView) view.findViewById(R.id.chatphone);
            holder.chatEmail = (TextView) view.findViewById(R.id.chatmail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.chatName.setText(teacher.getTeacherName());
        holder.chatPhone.setText("Phone: "+teacher.getTeacherPhone());
        String temp = "Email: "+teacher.getTeacherEmail();
        if(temp.length()>10){
            holder.chatEmail.setText(temp.substring(0,10)+"...");
        }else{
            holder.chatEmail.setText(temp);
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public static class ViewHolder {
        TextView chatName, chatPhone, chatEmail;
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Teacher> list = originalData;
            int count = list.size();

            final ArrayList<Teacher> nlist = new ArrayList<>(count);
            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getTeacherName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Teacher>) results.values;
            notifyDataSetChanged();
        }
    }
}
