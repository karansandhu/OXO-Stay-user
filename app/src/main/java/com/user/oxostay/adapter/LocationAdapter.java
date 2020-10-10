package com.user.oxostay.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.user.oxostay.R;
import com.user.oxostay.models.Location;
import com.user.oxostay.screens.SearchActivity;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{
        private ArrayList<Location> listdata;
        private Context context;

    // RecyclerView recyclerView;
        public LocationAdapter(ArrayList<Location> listdata, Context context) {
                this.listdata = listdata;
                this.context = context;
                }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                    View listItem= layoutInflater.inflate(R.layout.location_item, parent, false);
                    ViewHolder viewHolder = new ViewHolder(listItem);
                    return viewHolder;
                }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//        final ArrayList<String> myListData = listdata[position];
            final Location location = listdata.get(position);
                holder.tv_location_name.setText(location.getCity_name());
                holder.rl_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("location",location.getCity_name());
                        intent.putExtra("location_id",location.getCity_id());
                        Log.e("checkLocation",">>" + location.getCity_name());
                        context.startActivity(intent);
                    }
                    });
                }


        @Override
        public int getItemCount() {
                return listdata.size();
                }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView iv_location;
            public TextView tv_location_name;
            public RelativeLayout rl_location;
            public ViewHolder(View itemView) {
                super(itemView);
                this.iv_location = (ImageView) itemView.findViewById(R.id.iv_location);
                this.tv_location_name = (TextView) itemView.findViewById(R.id.tv_location_name);
                rl_location = (RelativeLayout)itemView.findViewById(R.id.rl_location);
            }
        }
    }
