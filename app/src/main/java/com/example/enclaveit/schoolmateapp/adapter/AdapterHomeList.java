package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.activities.ActivityBusTracking;
import com.example.enclaveit.schoolmateapp.activities.ActivityChat;
import com.example.enclaveit.schoolmateapp.activities.ActivityReport;
import com.example.enclaveit.schoolmateapp.activities.ActivityTimeTable;
import com.example.enclaveit.schoolmateapp.bean.Feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuongluis on 4/9/2017.
 */

public class AdapterHomeList extends RecyclerView.Adapter<AdapterHomeList.FeatureViewHolder>{

    private Context context;
    private List<Feature> features;

    public class FeatureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private ImageView thumbnail, overflow;
        private List<Feature> features = new ArrayList<>();
        private Context context;

        public FeatureViewHolder(View view, Context context, ArrayList<Feature> features) {
            super(view);

            this.features = features;
            this.context = context;
            view.setOnClickListener(this);

            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Feature feature = this.features.get(position);
            if(feature.getName().equals("Announcement")){
                context.startActivity(new Intent(context, ActivityAnnouncement.class));
            }else if(feature.getName().equals("Timetable")){
                context.startActivity(new Intent(context, ActivityTimeTable.class));
            }else if(feature.getName().equals("Bus Tracking")){
                context.startActivity(new Intent(context, ActivityBusTracking.class));
            }else if(feature.getName().equals("Chatting")){
                context.startActivity(new Intent(context, ActivityChat.class));
            }else if(feature.getName().equals("Home Work")){

            }else if(feature.getName().equals("Report")){
                context.startActivity(new Intent(context, ActivityReport.class));
            }
        }
    }

    public AdapterHomeList(Context context, List<Feature> features) {
        this.context = context;
        this.features = features;
    }

    @Override
    public FeatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        FeatureViewHolder featureHolder =  new FeatureViewHolder(itemView,context, (ArrayList<Feature>) features);
        return featureHolder;
    }

    @Override
    public void onBindViewHolder(final FeatureViewHolder holder, int position) {
        Feature feature = features.get(position);
        holder.title.setText(feature.getName());

        // loading feature cover using Glide library
        Glide.with(context).load(feature.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

     /**
      * Click listener for popup menu items
      */
     class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
     }

    @Override
    public int getItemCount() {
        return features.size();
    }
}
