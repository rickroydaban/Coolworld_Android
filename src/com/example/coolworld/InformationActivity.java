package com.example.coolworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projects.coolworld.threads.InformationListMaker;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class InformationActivity extends Activity{
	private InformationActivity countrySelectActivity = this;
	public ListView list;
	public ArrayList<HashMap<String, String>> informationList;
	public String target = "countries";

	@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listview);
		list=(ListView)findViewById(R.id.list);
		    
    Map<String, String> countryMap = new HashMap<String, String>();				
    countryMap.put("vars", "featured categories mobile_long");
		countryMap.put("tags", "prop:items coolsites:Manila");
		countryMap.put("f", "getAllData");
					
    new InformationListMaker(this, getResources().getString(R.string.urlRootLink),countryMap).execute();    
    
    // Click event for single list row
    list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(target.equals("countries")){
					target = "coolsites";
			    Map<String, String> httpGetParams = new HashMap<String, String>();				
			    httpGetParams.put("vars", "featured");
			    httpGetParams.put("tags", "prop:coolsites countries:"+informationList.get(position).get("englishName").toLowerCase());
					httpGetParams.put("f", "getAllData");
	
			    new InformationListMaker(countrySelectActivity, getResources().getString(R.string.urlRootLink),httpGetParams).execute();    
				}else{
//					Intent i = new Intent(this, );
				}
			}
		});		

  }
}
