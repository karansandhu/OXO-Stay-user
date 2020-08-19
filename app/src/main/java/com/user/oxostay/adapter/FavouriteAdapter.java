package com.user.oxostay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.user.oxostay.R;
import com.user.oxostay.screens.HotelDetailActivity;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
    private ArrayList<String> listdata;
    private Context context;

    // RecyclerView recyclerView;
    public FavouriteAdapter(ArrayList<String> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.hotel_list_item, parent, false);
        FavouriteAdapter.ViewHolder viewHolder = new FavouriteAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.ViewHolder holder, int position) {
//        final ArrayList<String> myListData = listdata[position];
        holder.tv_hotel_name.setText(listdata.get(position));
        holder.rl_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_hotel_pic;
        public TextView tv_hotel_name;
        public RelativeLayout rl_three;

        public ViewHolder(View itemView) {
            super(itemView);

            this.iv_hotel_pic = (ImageView) itemView.findViewById(R.id.iv_hotel_pic);
            this.tv_hotel_name = (TextView) itemView.findViewById(R.id.tv_hotel_name);
            this.rl_three = (RelativeLayout) itemView.findViewById(R.id.rl_three);
        }
    }
}

