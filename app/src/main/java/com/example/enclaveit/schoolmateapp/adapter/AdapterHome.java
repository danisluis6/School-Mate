package com.example.enclaveit.schoolmateapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.activities.ActivityAnnouncement;
import com.example.enclaveit.schoolmateapp.activities.ActivityBusTracking;
import com.example.enclaveit.schoolmateapp.activities.ActivityChat;
import com.example.enclaveit.schoolmateapp.activities.ActivityProfile;
import com.example.enclaveit.schoolmateapp.activities.ActivityReport;
import com.example.enclaveit.schoolmateapp.activities.ActivityTimeTable;
import com.example.enclaveit.schoolmateapp.bean.Feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuongluis on 4/9/2017.
 */

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.FeatureViewHolder>{

    private Context context;
    private List<Feature> features;

    public class FeatureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView thumbnail;
        private List<Feature> features = new ArrayList<>();
        private Context context;

        public FeatureViewHolder(View view, Context context, ArrayList<Feature> features) {
            super(view);

            this.features = features;
            this.context = context;
            view.setOnClickListener(this);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Feature feature = this.features.get(position);
            if(feature.getThumbnail()==R.drawable.feature1){
                context.startActivity(new Intent(context, ActivityAnnouncement.class));
            }else if(feature.getThumbnail()==R.drawable.feature2){
                context.startActivity(new Intent(context, ActivityTimeTable.class));
            }else if(feature.getThumbnail()==R.drawable.feature3){
                context.startActivity(new Intent(context, ActivityBusTracking.class));
            }else if(feature.getThumbnail()==R.drawable.feature4){
                context.startActivity(new Intent(context, ActivityChat.class));
            }else if(feature.getThumbnail()==R.drawable.feature5){
                context.startActivity(new Intent(context, ActivityProfile.class));
            }else if(feature.getThumbnail()==R.drawable.feature6){
                context.startActivity(new Intent(context, ActivityReport.class));
            }
        }
    }

    public AdapterHome(Context context, List<Feature> features) {
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
    public void onBindViewHolder(FeatureViewHolder holder, int i) {
        Feature feature = features.get(i);
        // loading feature cover using Glide library
        Glide.with(context).load(feature.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return features.size();
    }
}
