package com.user.oxostay.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
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
import com.user.oxostay.models.Booking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder>{
    private ArrayList<Booking> listdata;
    private Context context;

    // RecyclerView recyclerView;
    public BookingAdapter(ArrayList<Booking> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.booking_item, parent, false);
        BookingAdapter.ViewHolder viewHolder = new BookingAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookingAdapter.ViewHolder holder, int position) {
//        final ArrayList<String> myListData = listdata[position];

        try {

            final Booking booking = listdata.get(position);
            holder.tv_hotel_name.setText(booking.getHotel_name());
            holder.tv_total_amount.setText(booking.getTotal_amount());
            holder.tv_room_nos.setText(booking.getRooms_booked());
            holder.tv_booking_id.setText(booking.getTransaction_id());
            SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = inFormat.parse(booking.getCheck_in_date());
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            SimpleDateFormat outFormat2 = new SimpleDateFormat("dd");
            SimpleDateFormat outFormat3 = new SimpleDateFormat("MMM");
            String goal = outFormat.format(date);
            String goal2 = outFormat2.format(date);
            String goal3 = outFormat3.format(date);
            Log.e("checkFinalDates","1>>" + goal);
//            String dayOfTheWeek = (String) DateFormat.format("EEEE", Long.parseLong(booking.getCheck_in_date()));
//            String day          = (String) DateFormat.format("dd",   Long.parseLong(booking.getCheck_in_date()));
//            String year         = (String) DateFormat.format("yyyy", Long.parseLong(booking.getCheck_in_date()));
//            Log.e("checkFinalDates","2>>" + day + "??" + year + "??" + dayOfTheWeek);
            holder.tv_date.setText(goal2);
            holder.tv_year.setText(goal3);
            holder.tv_day.setText(goal);
//            Glide.with(context).load(booking.getHotel_images().get(0)).into(holder.iv_hotel_pic);
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
        }catch (Exception e){
            Log.e("checkFinalDates","Exception>>" + e.getMessage());

        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView iv_hotel_pic;
        public TextView tv_total_amount,tv_room_nos,tv_hotel_name,tv_booking_id,tv_date,tv_year,tv_day;
//        public RelativeLayout rl_three,rl_prices;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tv_total_amount = (TextView) itemView.findViewById(R.id.tv_total_amount);
            this.tv_room_nos = (TextView) itemView.findViewById(R.id.tv_room_nos);
            this.tv_hotel_name = (TextView) itemView.findViewById(R.id.tv_hotel_name);
            this.tv_booking_id = (TextView) itemView.findViewById(R.id.tv_booking_id);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            this.tv_day = (TextView) itemView.findViewById(R.id.tv_day);
        }
    }
}

