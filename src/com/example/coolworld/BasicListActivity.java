package com.example.coolworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.example.coolworld.threads.BasicListMaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BasicListActivity extends Activity{
	private BasicListActivity basicListActivity = this;
	public ListView list;
	public ArrayList<HashMap<String, String>> countryList;
	private Bundle extras;
	protected Intent intent;

	@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listview);
		list=(ListView)findViewById(R.id.list);

		
    extras = getIntent().getExtras(); //get the passed extras sent by the previous intent

	  Map<String, String> countryMap = new HashMap<String, String>();		
	  
	  
	  if(extras.containsKey("vars"))
	  	countryMap.put("vars", extras.getString("vars"));
	  
	  if(extras.containsKey("tags"))
	  	countryMap.put("tags", extras.getString("tags"));
		
	  countryMap.put("f", extras.getString("f"));
					
	  if(extras.containsKey("imageUrl"))
	  	new BasicListMaker(this, getResources().getString(R.string.urlRootLink),countryMap,extras.getString("imageUrl")).execute();    
	  else
	  	new BasicListMaker(this, getResources().getString(R.string.urlRootLink),countryMap).execute();    
    
    // Click event for single list row
    list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(extras.getString("nextActivity").equals("makeCoolsiteList")){
					intent = new Intent(basicListActivity, BasicListActivity.class);
					intent.putExtra("imageUrl", countryList.get(position).get("flag"));
			    intent.putExtra("vars", "featured");
			    intent.putExtra("tags", "prop:coolsites countries:"+countryList.get(position).get("englishName").toLowerCase());
					intent.putExtra("f", "getAllData");	
					intent.putExtra("nextActivity", "homeActivity");
					basicListActivity.startActivity(intent);
				}else if(extras.getString("nextActivity").equals("homeActivity")){
					intent = new Intent(basicListActivity, HomeActivity.class);
					intent.putExtra("englishName", countryList.get(position).get("englishName").toLowerCase());
					intent.putExtra("featuredImg", countryList.get(position).get("flag"));					
					basicListActivity.startActivity(intent);
				}
			}
		});		

  }
}
