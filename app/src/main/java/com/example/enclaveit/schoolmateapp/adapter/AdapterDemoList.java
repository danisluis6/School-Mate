package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.bean.Device;

import java.util.List;


/**
 * Created by vuongluis on 05/11/2016.
 */

public class AdapterDemoList extends BaseAdapter {

    private List<Device> listItems;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterDemoList(Context context, List<Device> listItems){
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
        Device device = (Device)getItem(i);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_demo, null);
            holder = new ViewHolder();

            holder.name = (TextView) view.findViewById(R.id.name);
            holder.board = (TextView) view.findViewById(R.id.board);
            holder.cores = (TextView) view.findViewById(R.id.cores);
            holder.clockspeed = (TextView) view.findViewById(R.id.clockspeed);
            holder.cpu = (TextView) view.findViewById(R.id.cpu);
            holder.govemor = (TextView) view.findViewById(R.id.govemor);
            holder.version = (TextView) view.findViewById(R.id.version);
            holder.arm7 = (TextView) view.findViewById(R.id.arm7);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(device.getName());
        holder.board.setText(device.getBoard());
        holder.cores.setText(device.getCores());
        holder.clockspeed.setText(device.getClockspeed());
        holder.cpu.setText(device.getCpu());
        holder.govemor.setText(device.getGovemor());
        holder.version.setText(device.getVersion());
        holder.arm7.setText(device.getArm7());
        return view;
    }

    public static class ViewHolder {
        TextView name;
        TextView board;
        TextView cores;
        TextView clockspeed;
        TextView cpu;
        TextView govemor;
        TextView version;
        TextView arm7;
    }
}
