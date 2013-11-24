package com.example.coolworld;

import com.example.coolworld.listactivities.CategoryLister;
import com.example.coolworld.listactivities.HomeItemLister;
import com.example.coolworld.listactivities.InformationLister;
import com.example.coolworld.listactivities.SearchLister;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class HomeActivity extends TabActivity {
	private Bundle extras;
	public String countryName, countryFlag, coolsiteName, coolsiteImage, coolsiteCover; //preset data
	@Override
	public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    
    TabHost tabHost = getTabHost();  
    
    extras = getIntent().getExtras(); //get the passed extras sent by the previous intent
    countryName = extras.getString("countryName");
    countryFlag = extras.getString("countryFlag");
    coolsiteName = extras.getString("coolsiteName");
    coolsiteImage = extras.getString("coolsiteImage");
    coolsiteCover = extras.getString("coolsiteCover");
    
    TabSpec homeSpec = tabHost.newTabSpec("Home");
    View homeView = createTabView(tabHost.getContext(), "Home", R.drawable.settings);
    homeSpec.setIndicator(homeView);
    Intent homeIntent = new Intent(this, new HomeItemLister(coolsiteName, coolsiteImage, coolsiteCover).getClass());
    homeSpec.setContent(homeIntent);
    
    TabSpec infoSpec = tabHost.newTabSpec("Info");
    View infoView = createTabView(tabHost.getContext(), "Info", R.drawable.info);
    infoSpec.setIndicator(infoView);
    Intent infoIntent = new Intent(this, new InformationLister(coolsiteName, coolsiteImage).getClass());
    infoSpec.setContent(infoIntent);
          
    TabSpec mapSpec = tabHost.newTabSpec("Map");
    View mapView = createTabView(tabHost.getContext(), "Map", R.drawable.map);
    mapSpec.setIndicator(mapView);
    Intent mapIntent = new Intent(this, FragmentPagerSupport.class);
    mapIntent.putExtra("coolsiteName", extras.getString("englishName"));
    mapSpec.setContent(mapIntent);
    
    TabSpec categorySpec = tabHost.newTabSpec("Category");
    View categoryView = createTabView(tabHost.getContext(), "Category", R.drawable.category);
    categorySpec.setIndicator(categoryView);
    Intent categoryIntent = new Intent(this, new CategoryLister(coolsiteName, coolsiteImage).getClass());
    categorySpec.setContent(categoryIntent);
    
    TabSpec searchSpec = tabHost.newTabSpec("Search");
    View searchView = createTabView(tabHost.getContext(), "Search", R.drawable.search);
    searchSpec.setIndicator(searchView);
    Intent searchIntent = new Intent(this, new SearchLister(coolsiteName, coolsiteImage).getClass());
    searchSpec.setContent(searchIntent);
 
    tabHost.addTab(homeSpec);        
    tabHost.addTab(infoSpec); 
    tabHost.addTab(mapSpec); 
    tabHost.addTab(categorySpec);
    tabHost.addTab(searchSpec);
  }

  private static View createTabView(final Context context, final String text, final int iconId) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
		ImageView iv = (ImageView) view.findViewById(R.id.tabIcon);
		TextView tv = (TextView) view.findViewById(R.id.tabTitle);
		iv.setBackgroundResource(iconId);
		tv.setText(text);
		return view;
	}
}

