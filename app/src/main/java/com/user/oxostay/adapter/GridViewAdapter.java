package com.user.oxostay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.user.oxostay.R;
import com.user.oxostay.models.Amenities;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> amenitiesImageList = null;
    ArrayList<String> amenitiesLabelList = null;

    private LayoutInflater layoutInflater;
    private ImageView iv_grid;
    private TextView tv_grid;

    // Constructor
    public GridViewAdapter(Context c,ArrayList<String> amenitiesImageList,ArrayList<String> amenitiesLabelList) {
        this.mContext = c;
        this.amenitiesImageList = amenitiesImageList;
        this.amenitiesLabelList = amenitiesLabelList;
    }

    public int getCount() {
//        return amenitiesImageList.size();
        return amenitiesImageList == null ? 0 : amenitiesImageList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//
//        if (convertView == null) {
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        }
//        else
//        {
//            imageView = (ImageView) convertView;
//        }
//        Log.e("CheckFinalAmeNa","<>" + amenitiesLabelList.get(position));
//        imageView.setImageResource(Integer.parseInt(amenitiesImageList.get(position)));
//        return imageView;
        Log.e("checkFianll",">>" + amenitiesLabelList.get(position));

        if (layoutInflater==null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
        }
        iv_grid = convertView.findViewById(R.id.iv_grid);
        tv_grid = convertView.findViewById(R.id.tv_grid);
        Glide.with(mContext).load(amenitiesImageList.get(position)).into(iv_grid);
//        iv_grid.setImageBitmap(amenitiesImageList.get(position));
        tv_grid.setText(amenitiesLabelList.get(position));
        return convertView;

    }

    // Keep all Images in array
//    public Integer[] mThumbIds = {
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7
//    };
}
