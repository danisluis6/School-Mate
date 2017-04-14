package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityChat;
import com.example.enclaveit.schoolmateapp.bean.Announcement;
import com.example.enclaveit.schoolmateapp.bean.Contact;
import com.example.enclaveit.schoolmateapp.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enclaveit on 12/04/2017.
 */

public class SearchableAdapter extends BaseAdapter implements Filterable{

    private LayoutInflater layoutInflater;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    private String filterableString;

    private List<Contact>originalData = null;
    private List<Contact>filteredData = null;

    public SearchableAdapter(Context context, List<Contact> listItems){
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
        holder.contactPhone.setText("+84972248187");
        holder.contactPhoto.setImageResource(R.drawable.anouce_icon_15);
        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public static class ViewHolder {
        TextView contactName, contactPhone;
        ImageView contactPhoto;
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Contact> list = originalData;
            int count = list.size();

            final ArrayList<Contact> nlist = new ArrayList<>(count);
            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getContactName();
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
            filteredData = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }
}
