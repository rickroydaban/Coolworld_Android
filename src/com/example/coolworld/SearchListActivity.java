package com.example.coolworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.coolworld.threads.BasicListMaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class SearchListActivity extends Activity{
	private SearchListActivity searchListActivity = this;
	public ListView list;
	public ArrayList<HashMap<String, String>> countryList;
	protected Intent intent;
	private EditText searchField;
  Map<String, String> countryMap = new HashMap<String, String>();
	private Bundle extras;		

	@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listview_search);
		list=(ListView)findViewById(R.id.list);
    extras = getIntent().getExtras(); //get the passed extras sent by the previous intent
		searchField = (EditText)findViewById(R.id.searchField);
		final String tags = extras.getString("tags");
		searchField.addTextChangedListener(new TextWatcher() {

      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

			@Override
			public void afterTextChanged(Editable s) {
	  	  list.setAdapter(null);

	  	  if(s.length()>0){
					countryMap.put("target", searchField.getText().toString()+"*");	  
		    	countryMap.put("vars", "featured");	  
		    	countryMap.put("tags", tags);
		  	  countryMap.put("f", "getAllData");
		  	  new BasicListMaker(searchListActivity, getResources().getString(R.string.urlRootLink),countryMap).execute();    
				}
			}
   });			
    
    // Click event for single list row
//    list.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				if(extras.getString("nextActivity").equals("makeCoolsiteList")){
//					intent = new Intent(searchListActivity, SearchListActivity.class);
//					intent.putExtra("imageUrl", countryList.get(position).get("flag"));
//			    intent.putExtra("vars", "featured");
//			    intent.putExtra("tags", "prop:coolsites countries:"+countryList.get(position).get("englishName").toLowerCase());
//					intent.putExtra("f", "getAllData");	
//					intent.putExtra("nextActivity", "homeActivity");
//					searchListActivity.startActivity(intent);
//				}else if(extras.getString("nextActivity").equals("homeActivity")){
//					intent = new Intent(searchListActivity, HomeActivity.class);
//					intent.putExtra("englishName", countryList.get(position).get("englishName").toLowerCase());
//					intent.putExtra("featuredImg", countryList.get(position).get("flag"));					
//					searchListActivity.startActivity(intent);
//				}
//			}
//		});		

  }
}
