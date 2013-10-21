package com.example.coolworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projects.coolworld.threads.BasicListMaker;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BasicListActivity extends Activity{
	private BasicListActivity countrySelectActivity = this;
	public ListView list;
	public ArrayList<HashMap<String, String>> countryList;
	public String target = "countries";

	@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listview);
		list=(ListView)findViewById(R.id.list);
		    
    Map<String, String> countryMap = new HashMap<String, String>();				
		countryMap.put("tags", "prop:countries");
		countryMap.put("f", "getAllData");
					
    new BasicListMaker(this, getResources().getString(R.string.urlRootLink),countryMap, target).execute();    
    
    // Click event for single list row
    list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(target.equals("countries")){
					target = "coolsites";
			    Map<String, String> httpGetParams = new HashMap<String, String>();				
			    httpGetParams.put("vars", "featured");
			    httpGetParams.put("tags", "prop:coolsites countries:"+countryList.get(position).get("englishName").toLowerCase());
					httpGetParams.put("f", "getAllData");
	
			    new BasicListMaker(countrySelectActivity, getResources().getString(R.string.urlRootLink),httpGetParams,target,countryList.get(position).get("flag")).execute();    
				}else{
//					Intent i = new Intent(this, );
				}
			}
		});		

  }
}
