package com.user.oxostay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.user.oxostay.R;
import com.user.oxostay.models.Booking;
import com.user.oxostay.utils.RecyclerViewClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>{
    private ArrayList<String> listdata;
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private boolean is_Selected = false;
    private String selected_pos = "";

    // RecyclerView recyclerView;
    public TimeAdapter(ArrayList<String> listdata, Context context, RecyclerViewClickListener itemListener) {
        this.listdata = listdata;
        this.context = context;
        this.itemListener = itemListener;
    }
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.time_item, parent, false);
        TimeAdapter.ViewHolder viewHolder = new TimeAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TimeAdapter.ViewHolder holder, final int position) {

        try {
            holder.tv_time.setText(listdata.get(position));
            holder.tv_time.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {

                      if (!is_Selected){
                          selected_pos= String.valueOf(position);
                          holder.ll_time.setBackgroundColor(Color.YELLOW);
                          is_Selected = true;
                          Log.e("checkOnG",">>" + is_Selected + ">>" + selected_pos);

                      }else if (is_Selected){
                          holder.ll_time.setBackgroundColor(R.color.colorLightGrey);
                          is_Selected = false;
                          Log.e("checkOnG","else>>" + is_Selected + ">>" + selected_pos);

                      }

//                    Intent intent = new Intent(context, HotelDetailActivity.class);
//                    intent.putExtra("images_array",approvedModel.getHotel_images());
//                    intent.putExtra("hotel_name",approvedModel.getHotel_name());
//                    intent.putExtra("hotel_address",approvedModel.getHotel_address());
//                    intent.putExtra("hotel_rating",approvedModel.getHotel_rating());
//                    intent.putExtra("hotel_rate",approvedModel.getRoom_rate_3_hour());
//                    intent.putExtra("hotel_amenities",approvedModel.getAmenities());
//                    intent.putExtra("hotel_desc",approvedModel.getHotel_desc());
//                    context.startActivity(intent);
                }
            });
        }catch (Exception e){
            Log.e("checkFinalDates","Exception>>" + e.getMessage());

        }
    }



    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //        public ImageView iv_hotel_pic;
        public TextView tv_time;
        public LinearLayout ll_time;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            this.ll_time = (LinearLayout) itemView.findViewById(R.id.ll_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.recyclerViewListClicked(getAdapterPosition());
                }
            });
        }

    }
}
