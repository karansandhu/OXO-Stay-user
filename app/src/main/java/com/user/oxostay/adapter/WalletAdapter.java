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
import com.user.oxostay.screens.SearchActivity;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder>{
    private ArrayList<String> listdata;
    private Context context;

    // RecyclerView recyclerView;
    public WalletAdapter(ArrayList<String> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.wallet_item, parent, false);
        WalletAdapter.ViewHolder viewHolder = new WalletAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WalletAdapter.ViewHolder holder, int position) {
//        final ArrayList<String> myListData = listdata[position];
        holder.tv_wallet_amount.setText(listdata.get(position));
//        holder.tv_wallet_amount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SearchActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_wallet_amount,tv_wallet_status,tv_wallet_date;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_wallet_amount = (TextView) itemView.findViewById(R.id.tv_wallet_amount);
            this.tv_wallet_status = (TextView) itemView.findViewById(R.id.tv_wallet_status);
            this.tv_wallet_date = (TextView) itemView.findViewById(R.id.tv_wallet_date);
        }
    }
}
