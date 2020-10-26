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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.user.oxostay.R;
import com.user.oxostay.models.ApprovedModel;
import com.user.oxostay.screens.HotelDetailActivity;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
    private ArrayList<ApprovedModel> listdata;
    private ArrayList<String> hotelListIdsNew;
    private Context context;
    private String Intentdate,time;

    // RecyclerView recyclerView;
    public FavouriteAdapter(ArrayList<String> hotelListIdsNew, ArrayList<ApprovedModel> listdata, Context context) {
        this.hotelListIdsNew = hotelListIdsNew;
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
    public void onBindViewHolder(FavouriteAdapter.ViewHolder holder, final int position) {
//        final ArrayList<String> myListData = listdata[position];

        try {

            final ApprovedModel approvedModel = listdata.get(position);
            holder.tv_hotel_name.setText(approvedModel.getHotel_name());
            holder.tv_address.setText(approvedModel.getHotel_address());
            holder.tv_3_hr.setText(approvedModel.getRoom_rate_3_hour());
            holder.tv_6_hr.setText(approvedModel.getRoom_rate_6_hour());
            holder.tv_12_hr.setText(approvedModel.getRoom_rate_12_hour());
            Glide.with(context).load(approvedModel.getHotel_images().get(0)).into(holder.iv_hotel_pic);
//            holder.rl_prices.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, HotelDetailActivity.class);
//                    intent.putExtra("images_array",approvedModel.getHotel_images());
//                    intent.putExtra("hotel_name",approvedModel.getHotel_name());
//                    intent.putExtra("hotel_address",approvedModel.getHotel_address());
//                    intent.putExtra("hotel_rating",approvedModel.getHotel_rating());
//                    intent.putExtra("hotel_rate",approvedModel.getRoom_rate_3_hour());
//                    intent.putExtra("hotel_amenities",approvedModel.getAmenities());
//                    intent.putExtra("hotel_desc",approvedModel.getHotel_desc());
//                    context.startActivity(intent);
//                }
//            });
            holder.rl_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HotelDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("time","3 Hours");
                    intent.putExtra("preAct","Fav");
                    intent.putExtra("check_in_time",time);
                    intent.putExtra("images_array",approvedModel.getHotel_images());
                    intent.putExtra("hotel_name",approvedModel.getHotel_name());
                    intent.putExtra("hotel_address",approvedModel.getHotel_address());
                    intent.putExtra("hotel_rating",approvedModel.getHotel_rating());
                    intent.putExtra("hotel_rate",approvedModel.getRoom_rate_3_hour());
                    intent.putExtra("hotel_amenities",approvedModel.getAmenities());
                    intent.putExtra("hotel_desc",approvedModel.getHotel_desc());
                    intent.putExtra("hotel_email",approvedModel.getHotel_email());
                    intent.putExtra("hotel_phnno",approvedModel.getPhNumber());
                    intent.putExtra("hotel_id",hotelListIdsNew.get(position));
                    intent.putExtra("date_selected",Intentdate);
                    context.startActivity(intent);
                    Log.e("checkOncClick",">>" + hotelListIdsNew.get(position));
                }
            });
            holder.rl_six.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HotelDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("time","6 Hours");
                    intent.putExtra("preAct","Fav");
                    intent.putExtra("check_in_time",time);
                    intent.putExtra("images_array",approvedModel.getHotel_images());
                    intent.putExtra("hotel_name",approvedModel.getHotel_name());
                    intent.putExtra("hotel_address",approvedModel.getHotel_address());
                    intent.putExtra("hotel_rating",approvedModel.getHotel_rating());
                    intent.putExtra("hotel_rate",approvedModel.getRoom_rate_3_hour());
                    intent.putExtra("hotel_amenities",approvedModel.getAmenities());
                    intent.putExtra("hotel_desc",approvedModel.getHotel_desc());
                    intent.putExtra("hotel_id",hotelListIdsNew.get(position));
                    intent.putExtra("date_selected",Intentdate);
                    context.startActivity(intent);
                }
            });
            holder.rl_twelve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HotelDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("time","12 Hours");
                    intent.putExtra("preAct","Fav");
                    intent.putExtra("check_in_time",time);
                    intent.putExtra("images_array",approvedModel.getHotel_images());
                    intent.putExtra("hotel_name",approvedModel.getHotel_name());
                    intent.putExtra("hotel_address",approvedModel.getHotel_address());
                    intent.putExtra("hotel_rating",approvedModel.getHotel_rating());
                    intent.putExtra("hotel_rate",approvedModel.getRoom_rate_3_hour());
                    intent.putExtra("hotel_amenities",approvedModel.getAmenities());
                    intent.putExtra("hotel_desc",approvedModel.getHotel_desc());
                    intent.putExtra("hotel_id",hotelListIdsNew.get(position));
                    intent.putExtra("date_selected",Intentdate);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        Log.e("checkOncClick","1111>>" + listdata.size() + ">>" + hotelListIdsNew.size());
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_hotel_pic;
        public TextView tv_hotel_name,tv_3_hr,tv_6_hr,tv_12_hr,tv_address;
        public RelativeLayout rl_three,rl_prices,rl_twelve,rl_six;

        public ViewHolder(View itemView) {
            super(itemView);

            this.iv_hotel_pic = (ImageView) itemView.findViewById(R.id.iv_hotel_pic);
            this.tv_hotel_name = (TextView) itemView.findViewById(R.id.tv_hotel_name);
            this.tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            this.tv_3_hr = (TextView) itemView.findViewById(R.id.tv_3_hr);
            this.tv_6_hr = (TextView) itemView.findViewById(R.id.tv_6_hr);
            this.tv_12_hr = (TextView) itemView.findViewById(R.id.tv_12_hr);
            this.rl_three = (RelativeLayout) itemView.findViewById(R.id.rl_three);
            this.rl_prices = (RelativeLayout) itemView.findViewById(R.id.rl_prices);
            this.rl_six = (RelativeLayout) itemView.findViewById(R.id.rl_six);
            this.rl_twelve = (RelativeLayout) itemView.findViewById(R.id.rl_twelve);
        }
    }
}

