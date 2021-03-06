package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
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
import com.user.oxostay.models.Favourites;
import com.user.oxostay.models.SliderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HotelDetailActivity extends AppCompatActivity {

    String hotel_name,hotel_desc,hotel_rating,check_in_time,hotel_rate,hotel_id,hotel_address,is_liked,selected_time,hotel_email,hotel_phnno;
    ImageView iv_banner,iv_map,iv_like;
    TextView tv_hotelname,tv_hoteladress,tv_hotelrating,tv_hotelrate,tv_hoteldesc,tv_hoteldirect;
    RatingBar ratingbar;
    RelativeLayout rl_continue_booking;
    LinearLayout ll_map,ll_directions;
    Fragment map;
    ArrayList<String> hotel_pic_list,hotel_amenities_list,hotel_amenities_list_ids,final_amenities,hotel_ame_image,hotel_ame_name;
    ArrayList<Amenities> hotel_amenities_list_two;
    List<SliderItem> sliderItemList;
    SliderView sliderView;
    FirebaseDatabase database;
    DatabaseReference reference,likesRef;
    private SliderAdapterExample adapter;
    GridView gridview;
    BaseActivity baseActivity;
    private FirebaseAuth mAuth;
    Boolean likeChecker = false;
    String user_name,date_selected,preAct;

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
        likesRef = database.getReference().child("oxostayuser").child("favourites");

        sliderItemList=new ArrayList<>();
        hotel_ame_image=new ArrayList<>();
        hotel_ame_name=new ArrayList<>();
        final_amenities = new ArrayList<>();
        hotel_amenities_list_two = new ArrayList<>();
        hotel_amenities_list_ids = new ArrayList<>();

        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        rl_continue_booking = (RelativeLayout) findViewById(R.id.rl_continue_booking);
        ll_directions = (LinearLayout) findViewById(R.id.ll_directions);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        iv_map = (ImageView) findViewById(R.id.iv_map);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        tv_hotelname = (TextView) findViewById(R.id.tv_hotelname);
        tv_hoteladress = (TextView) findViewById(R.id.tv_hoteladress);
        tv_hotelrating = (TextView) findViewById(R.id.tv_hotelrating);
        tv_hotelrate = (TextView) findViewById(R.id.tv_hotelrate);
        tv_hoteldesc = (TextView) findViewById(R.id.tv_hoteldesc);
        tv_hoteldirect = (TextView) findViewById(R.id.tv_hoteldirect);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        gridview = (GridView) findViewById(R.id.gridView);

        hotel_pic_list=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        final Intent intent = getIntent();
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        user_name = sharedpreferences.getString("user_name","");
        try {
            preAct = intent.getStringExtra("preAct");
            if (preAct.equals("Fav")){
                iv_like.setImageResource(R.drawable.ic_heart_shape_silhouette);
            }else if (preAct.equals("Hotel")){

            }
        }catch (Exception e){

        }
        date_selected = intent.getStringExtra("date_selected");
        hotel_email = intent.getStringExtra("hotel_email");
        hotel_phnno = intent.getStringExtra("hotel_phnno");
        selected_time = intent.getStringExtra("time");
        hotel_id = intent.getStringExtra("hotel_id");
        hotel_pic_list = intent.getStringArrayListExtra("images_array");
        hotel_address = intent.getStringExtra("hotel_address");
        hotel_name = intent.getStringExtra("hotel_name");
        hotel_desc = intent.getStringExtra("hotel_desc");
        hotel_rating = intent.getStringExtra("hotel_rating");
        hotel_rate = intent.getStringExtra("hotel_rate");
        check_in_time = intent.getStringExtra("check_in_time");
//        is_liked = intent.getStringExtra("is_liked");
        hotel_amenities_list = intent.getStringArrayListExtra("hotel_amenities");
        Log.e("checkNameo",">HotelDetailActivity>" + date_selected + ">>" + is_liked);
//        String[] imageList = hotel_imgs.split(",");
        setLikebutton(mAuth.getCurrentUser().getUid());
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("checkLikeCount",">>" + is_liked);

                if (is_liked.equals("0")){
                    likeHotel();
//                    iv_like.setImageResource(R.drawable.ic_heart_white);
                }else if (is_liked.equals("1")){
                    unLikeHotel();
//                    iv_like.setImageResource(R.drawable.ic_heart_shape_silhouette);
                }

            }
        });
        rl_continue_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(HotelDetailActivity.this,ReviewActivity.class);
                intent1.putExtra("time",selected_time);
                intent1.putExtra("hotel_name",hotel_name);
                intent1.putExtra("check_in_time",check_in_time);
                intent1.putExtra("check_in_date",date_selected);
                intent1.putExtra("hotel_address",hotel_address);
                intent1.putExtra("hotel_email",hotel_email);
                intent1.putExtra("hotel_phnno",hotel_phnno);
                intent1.putExtra("hotel_id",hotel_id);
                intent1.putExtra("hotel_room",selected_time);
                intent1.putExtra("hotel_rate",hotel_rate);
                Log.e("checkFinalData","detail>>" + hotel_rate + ">" + hotel_name + ">"
                        + hotel_address + ">" + hotel_email + ">" + hotel_phnno + ">" + hotel_id);
                startActivity(intent1);
            }
        });

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

    public void setLikebutton(final String postKey) {

        likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                countLikes = (int) dataSnapshot.child(postKey).getChildrenCount();
//                tv_like_count.setText(Integer.toString(countLikes) + (" People Liked this"));
                try {
                    if (dataSnapshot.child(postKey).hasChild(hotel_id)) {

//                        countLikes = (int) dataSnapshot.child(postKey).getChildrenCount();
                        iv_like.setImageResource(R.drawable.ic_heart_shape_silhouette);
                        is_liked = "1";
//                        tv_like_count.setText(Integer.toString(countLikes) + (" People Liked this"));

                    } else {

//                        countLikes = (int) dataSnapshot.child(postKey).getChildrenCount();
                        iv_like.setImageResource(R.drawable.ic_heart_white);
                        is_liked = "0";
//                        tv_like_count.setText(Integer.toString(countLikes) + (" People Liked this"));
                    }
                } catch (Exception e) {


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void likeHotel(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("oxostayuser").child("favourites");
        Favourites favourites = new Favourites();
        favourites.setHotel_id(hotel_id);
        favourites.setUsername(user_name);
        ref.child(mAuth.getCurrentUser().getUid()).child(hotel_id).setValue(favourites);
        Toast.makeText(this, "Added to Favourites", Toast.LENGTH_SHORT).show();
        setLikebutton(mAuth.getCurrentUser().getUid());


    }
    public void unLikeHotel(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("oxostayuser").child("favourites");
        Favourites favourites = new Favourites();
        favourites.setHotel_id(hotel_id);
        favourites.setUsername(user_name);
        ref.child(mAuth.getCurrentUser().getUid()).child(hotel_id).removeValue();
        setLikebutton(mAuth.getCurrentUser().getUid());

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