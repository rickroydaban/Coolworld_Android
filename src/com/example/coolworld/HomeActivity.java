package com.example.coolworld;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        TabHost tabHost = getTabHost();        
        
        TabSpec infoSpec = tabHost.newTabSpec("Info");
        View infoView = createTabView(tabHost.getContext(), "Info", R.drawable.info);
        infoSpec.setIndicator(infoView);
        Intent infoIntent = new Intent(this, InformationActivity.class);
        infoSpec.setContent(infoIntent);
              
        TabSpec mapSpec = tabHost.newTabSpec("Map");
        View mapView = createTabView(tabHost.getContext(), "Map", R.drawable.map);
        mapSpec.setIndicator(mapView);
        Intent mapIntent = new Intent(this, IndexActivity.class);
        mapSpec.setContent(mapIntent);
        
        TabSpec categorySpec = tabHost.newTabSpec("Category");
        View categoryView = createTabView(tabHost.getContext(), "Category", R.drawable.category);
        categorySpec.setIndicator(categoryView);
        Intent categoryIntent = new Intent(this, IndexActivity.class);
        categorySpec.setContent(categoryIntent);
        
        TabSpec searchSpec = tabHost.newTabSpec("Search");
        View searchView = createTabView(tabHost.getContext(), "Search", R.drawable.search);
        searchSpec.setIndicator(searchView);
        Intent searchIntent = new Intent(this, IndexActivity.class);
        searchSpec.setContent(searchIntent);
        
        TabSpec settingsSpec = tabHost.newTabSpec("Settings");
        View settingsView = createTabView(tabHost.getContext(), "Settings", R.drawable.settings);
        settingsSpec.setIndicator(settingsView);
        Intent settingsIntent = new Intent(this, IndexActivity.class);
        settingsSpec.setContent(settingsIntent);
 
        TabSpec homeSpec = tabHost.newTabSpec("Home");
        View homeView = createTabView(tabHost.getContext(), "Home", R.drawable.settings);
        homeSpec.setIndicator(homeView);
        Intent homeIntent = new Intent(this, IndexActivity.class);
        homeSpec.setContent(homeIntent);
        
        tabHost.addTab(infoSpec); 
        tabHost.addTab(mapSpec); 
        tabHost.addTab(categorySpec);
        tabHost.addTab(searchSpec);
        tabHost.addTab(settingsSpec);        
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

