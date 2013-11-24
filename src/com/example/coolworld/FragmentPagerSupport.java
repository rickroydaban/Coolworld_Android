package com.example.coolworld;

import com.example.coolworld.fragments.MapFilterFragment;
import com.example.coolworld.fragments.MapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class FragmentPagerSupport extends FragmentActivity {
  static final int NUM_ITEMS = 2;

  MyAdapter mAdapter;

  ViewPager mPager;

	public static Bundle extras;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_map);
      extras = getIntent().getExtras(); //get the passed extras sent by the previous intent

      mAdapter = new MyAdapter(getSupportFragmentManager());

      mPager = (ViewPager)findViewById(R.id.pager);
      mPager.setAdapter(mAdapter);
      mPager.setCurrentItem(1);

  }
  

  public static class MyAdapter extends FragmentPagerAdapter {
      public MyAdapter(FragmentManager fm) {
          super(fm);
      }

      @Override
      public float getPageWidth(int position){
      if (position == 0){
          return 0.5f;
          }
      return 1f;
      } 

      @Override
      public int getCount() {
          return NUM_ITEMS;
      }

      @Override
      public Fragment getItem(int position) {
      	switch(position){
      		case 0:
            return MapFilterFragment.newInstance(extras.getString("coolsiteName"));
//      				return new MapFilterFragment();
      			
      		case 1:
            return MapFragment.newInstance(extras.getString("coolsiteName"));
      	}
      	
      	return null;
      }
            
  }   
}