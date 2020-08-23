package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.user.oxostay.R;
import com.user.oxostay.adapter.FragmentAdapter;
import com.user.oxostay.utils.PrefManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    ArrayList<Fragment> fragments;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrefManager prefManager = new PrefManager(getApplicationContext());
        if(prefManager.isFirstTimeLaunch()){
            Log.e("checkIntro","main>>");
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }
        initView();
//        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext(), fragments);
//        viewPager.setAdapter(pagerAdapter);

//        tabLayout.setupWithViewPager(viewPager);

//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search_black_24dp);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_apps_black_24dp);
//        tabLayout.getTabAt(3).setIcon(R.drawable.ic_notifications_black_24dp);
//        tabLayout.getTabAt(4).setIcon(R.drawable.ic_person_black_24dp);
    }

    public void initView(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("checkUSer",">>" + user);
        if (user != null) {
            // User is signed in
//            Intent i = new Intent(MainActivity.this, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(i);
        } else {
            // User is signed out
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Log.e("LOGIN", "onAuthStateChanged:signed_out");
        }


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
//        viewPager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabTextColors(R.color.colorWhite, R.color.colorBlack);
//        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.colorWhite));
//        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAppLightYellow));
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(pagerAdapter.getTabView(i));
//        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
//                    floatingActionButton.setVisibility(View.GONE);
                }else if (tab.getPosition() == 1) {
//                    floatingActionButton.setVisibility(View.GONE);
                }else if (tab.getPosition() == 2) {
//                    floatingActionButton.setVisibility(View.VISIBLE);
//                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(MainActivityTwo.this,AddActivity.class);
//                            startActivity(intent);
//                        }
//                    });
                }else if (tab.getPosition() == 3) {
//                    floatingActionButton.setVisibility(View.VISIBLE);
//                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(MainActivityTwo.this,AddBuddyPlan.class);
//                            startActivity(intent);
//                        }
//                    });
                }else if (tab.getPosition() == 4) {
//                    floatingActionButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void setupTabIcons() {

        tabLayout.getTabAt(0).setCustomView(getTabView(tabLayout.getContext(), R.drawable.home_icon,"Home"));
        tabLayout.getTabAt(1).setCustomView(getTabView(tabLayout.getContext(), R.drawable.fav_icon,"Favourite"));
        tabLayout.getTabAt(2).setCustomView(getTabView(tabLayout.getContext(), R.drawable.booking_icon,"Bookings"));
        tabLayout.getTabAt(3).setCustomView(getTabView(tabLayout.getContext(), R.drawable.profile_icon,"Profile"));



    }

    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),this);
        pagerAdapter.addFrag(new HomeFragment(), "One");
        pagerAdapter.addFrag(new FavouriteFragment(), "Two");
        pagerAdapter.addFrag(new BookingFragment(), "Three");
        pagerAdapter.addFrag(new ProfileFragment(), "Four");
        viewPager.setAdapter(pagerAdapter);
    }

    class PagerAdapter extends FragmentPagerAdapter {

//        String tabTitles[] = new String[] { "Events", "Maps", "Add" ,"Profile" };
        private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
        private ArrayList<String> mFragmentTitleList = new ArrayList<String>();
        //        String tabIcons[] = new String[] {"R.drawable.add" ,"R.drawable.add","R.drawable.add"};
        int[] tabIcons = new int[]{R.drawable.eye, R.drawable.eye, R.drawable.eye, R.drawable.eye};
        Context context;

        public PagerAdapter(FragmentManager supportFragmentManager, MainActivity context) {
            super(supportFragmentManager);
            this.context = context;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment,String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {

//            switch (position) {
//                case 0:
//                    return new HomeFragment();
//                case 1:
//                    return new HomeFragment();
//                case 2:
//                    return new HomeFragment();
//                case 3:
//                    return new HomeFragment();
//            }
            return mFragmentList.get(position);
//            return null;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return mFragmentTitleList.get(position);
        }



    }
    public View getTabView(Context context,int icon,String text) {
        View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab_bg, null);
        TextView tv = (TextView) tab.findViewById(R.id.tab);
        ImageView ivTab = (ImageView) tab.findViewById(R.id.ivTab);
        ivTab.setImageResource(icon);
        tv.setText(text);
//        tv.setTextColor(R.color.colorBlack);
        return tab;
    }
//    class PagerAdapter extends FragmentPagerAdapter {
//
//        String tabTitles[] = new String[] { "Events", "Maps", "Add" ,"Profile" };
//        //        String tabIcons[] = new String[] {"R.drawable.add" ,"R.drawable.add","R.drawable.add"};
//        int[] tabIcons = new int[]{R.drawable.eye, R.drawable.eye, R.drawable.eye, R.drawable.eye};
//        Context context;
//
//        public PagerAdapter(FragmentManager supportFragmentManager, MainActivity context) {
//            super(supportFragmentManager);
//            this.context = context;
//        }
//
//        @Override
//        public int getCount() {
//            return tabTitles.length;
//        }
//
//
//
//        @Override
//        public Fragment getItem(int position) {
//
//            switch (position) {
//                case 0:
//                    return new HomeFragment();
//                case 1:
//                    return new HomeFragment();
//                case 2:
//                    return new HomeFragment();
//                case 3:
//                    return new HomeFragment();
//            }
//
//            return null;
//        }
//
//
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // Generate title based on item position
//            return tabTitles[position];
//        }
//
//        public View getTabView(int position) {
//            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab_bg, null);
//            TextView tv = (TextView) tab.findViewById(R.id.tab);
//            ImageView ivTab = (ImageView) tab.findViewById(R.id.ivTab);
//            ivTab.setImageResource(tabIcons[position]);
//            tv.setText(tabTitles[position]);
//            return tab;
//        }
//
//    }

}