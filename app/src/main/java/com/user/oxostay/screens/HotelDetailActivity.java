package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.user.oxostay.R;
import com.user.oxostay.adapter.GridViewAdapter;
import com.user.oxostay.adapter.HotelListAdapter;
import com.user.oxostay.adapter.SliderAdapterExample;
import com.user.oxostay.common.BaseActivity;
import com.user.oxostay.models.Amenities;
import com.user.oxostay.models.ApprovedModel;
import com.user.oxostay.models.SliderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HotelDetailActivity extends AppCompatActivity {

    String hotel_name,hotel_desc,hotel_rating,hotel_imgs,hotel_rate,hotel_amenities,hotel_address;
    ImageView iv_banner,iv_map;
    TextView tv_hotelname,tv_hoteladress,tv_hotelrating,tv_hotelrate,tv_hoteldesc,tv_hoteldirect;
    RatingBar ratingbar;
    LinearLayout ll_map,ll_directions;
    Fragment map;
    ArrayList<String> hotel_pic_list,hotel_amenities_list,hotel_amenities_list_ids,final_amenities,hotel_ame_image,hotel_ame_name;
    ArrayList<Amenities> hotel_amenities_list_two;
    List<SliderItem> sliderItemList;
    SliderView sliderView;
    FirebaseDatabase database;
    DatabaseReference reference;
    private SliderAdapterExample adapter;
    GridView gridview;
    BaseActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        initView();
    }

    public void initView(){
        baseActivity = new BaseActivity();
        baseActivity.showLoader(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("oxostaypartner").child("amenities");

        sliderItemList=new ArrayList<>();
        hotel_ame_image=new ArrayList<>();
        hotel_ame_name=new ArrayList<>();
        final_amenities = new ArrayList<>();
        hotel_amenities_list_two = new ArrayList<>();
        hotel_amenities_list_ids = new ArrayList<>();

        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        ll_directions = (LinearLayout) findViewById(R.id.ll_directions);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        iv_map = (ImageView) findViewById(R.id.iv_map);
        tv_hotelname = (TextView) findViewById(R.id.tv_hotelname);
        tv_hoteladress = (TextView) findViewById(R.id.tv_hoteladress);
        tv_hotelrating = (TextView) findViewById(R.id.tv_hotelrating);
        tv_hotelrate = (TextView) findViewById(R.id.tv_hotelrate);
        tv_hoteldesc = (TextView) findViewById(R.id.tv_hoteldesc);
        tv_hoteldirect = (TextView) findViewById(R.id.tv_hoteldirect);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        gridview = (GridView) findViewById(R.id.gridView);

        hotel_pic_list=new ArrayList<>();

        final Intent intent = getIntent();
        hotel_pic_list = intent.getStringArrayListExtra("images_array");
        hotel_address = intent.getStringExtra("hotel_address");
        hotel_name = intent.getStringExtra("hotel_name");
        hotel_desc = intent.getStringExtra("hotel_desc");
        hotel_rating = intent.getStringExtra("hotel_rating");
        hotel_rate = intent.getStringExtra("hotel_rate");
        hotel_amenities_list = intent.getStringArrayListExtra("hotel_amenities");
        Log.e("checkNameo",">>" + hotel_name);
//        String[] imageList = hotel_imgs.split(",");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                hotelList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    Amenities upload = postSnapshot.getValue(Amenities.class);
                    hotel_amenities_list_two.add(upload);
                    hotel_amenities_list_ids.add(upload.getId());
                }

                try {

                    Log.e("checkFinalAmeerror",">>" + hotel_amenities_list + "><><" + hotel_amenities_list_ids);
                    for (int i=0; i < hotel_amenities_list.size(); i++){
                        if(hotel_amenities_list_ids.contains(hotel_amenities_list.get(i))){
                            final_amenities.add(hotel_amenities_list_ids.get(i));
                            //do something for equals
                        }else{
                            //do something for not equals
                            int index = hotel_amenities_list_ids.indexOf(hotel_amenities_list.get(i));
                            hotel_amenities_list_ids.remove(index);
                        }
                    }

                    Log.e("checkFinalAme",">>" + final_amenities);

                    for (int i=0; i < hotel_amenities_list.size(); i++) {
                        if (hotel_amenities_list_two.get(i).getId().contains(hotel_amenities_list_ids.get(i))){
                            hotel_ame_image.add(hotel_amenities_list_two.get(i).getAmenitiesImage());
                            hotel_ame_name.add(hotel_amenities_list_two.get(i).getAmenetiesLabel());
                            Log.e("checkImageNow",">>" + hotel_amenities_list_two.get(i).getAmenitiesImage());
                        }
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("checkFinalAme","RESULT IF>>" + hotel_ame_image + "><>" + hotel_ame_name);
                gridview.setColumnWidth(60);
                gridview.setNumColumns(3);
                GridViewAdapter gridViewAdapter = new GridViewAdapter(HotelDetailActivity.this,hotel_ame_image,hotel_ame_name);
                gridview.setAdapter(gridViewAdapter);
                gridViewAdapter.notifyDataSetChanged();
//                gridview.setAdapter(new GridViewAdapter(HotelDetailActivity.this,hotel_ame_image,hotel_ame_name));
                baseActivity.dismissLoader();
            }
        }, 1000);

//        for(int i=0;i<imageList.length;i++){
//            sliderItemList.add(imageList[1]);
//        }

//        imagesListMain.add(imageList.toString());

//        Log.e("checkImageArray",">>" + temp);
        tv_hotelname.setText(hotel_name);
        tv_hoteladress.setText(hotel_address);
        tv_hoteldesc.setText(hotel_desc);
        tv_hotelrate.setText("Rs " + hotel_rate);
        tv_hotelrating.setText(hotel_rating);

        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

//        for (int i = 0;i<hotel_amenities_list.size();i++){
//            Log.e("checkAarray",">>" + hotel_amenities_list.get(i));
//        }

        ll_directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + 30.733315 + "," + 76.779419);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 30.733315, 76.779419);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
            }
        });
        renewItems();
    }


    public void renewItems() {
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
//            String[] imageList = hotel_imgs.split(",");
//            sliderItem.setDescription("Slider Item " + i);
//            if (i % 2 == 0) {
//                sliderItem.setImageUrl(hotel_list.get(0));
//            } else {
//                sliderItem.setImageUrl(hotel_list.get(1));
//            }

//            Log.e("checkImagesMain",">>" + imageList[0] + "><" + imageList[1]);
//            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(hotel_pic_list);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
//        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }

}