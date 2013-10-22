package com.example.coolworld;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MapActivity extends FragmentActivity {    
  @Override  
  protected void onCreate(Bundle savedInstanceState) {  
      super.onCreate(savedInstanceState);  
      setContentView(R.layout.activity_map);  
      
      MyPagerAdapter adapter = new MyPagerAdapter();
      ViewPager myPager = (ViewPager) findViewById(R.id.pager);
      myPager.setAdapter(adapter);
      myPager.setCurrentItem(1);  
  }  

  @Override  
  public boolean onCreateOptionsMenu(Menu menu) {  
      // Inflate the menu; this adds items to the action bar if it is present.  
      getMenuInflater().inflate(R.menu.main, menu);  
      return true;  
  }  
}  
