package com.example.coolworld.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.coolworld.BasicListActivity;
import com.example.coolworld.BasicListAdapter;
import com.example.coolworld.Jsonizer;
import com.example.coolworld.SearchListActivity;

public class BasicListMaker extends AsyncTask<String, Void, Void> {
	private BasicListActivity basicListActivity;
	private SearchListActivity searchListActivity;
	private Jsonizer json_country;
	private Handler handler;
  private BasicListAdapter adapter;
  private ProgressDialog progressDialog;
  private Map<String, String> httpGetParams;
	private String flagUrl;
	
	public BasicListMaker(SearchListActivity pCountrySelectActivity, String url, Map<String, String> pHTTPGetParams){
		searchListActivity = pCountrySelectActivity;
		json_country = new Jsonizer(url);
	  handler = new Handler(Looper.getMainLooper());
	  progressDialog = new ProgressDialog(searchListActivity);
	  httpGetParams = pHTTPGetParams;
	  searchListActivity.countryList = new ArrayList<HashMap<String,String>>();
	}

	public BasicListMaker(BasicListActivity pCountrySelectActivity, String url, Map<String, String> pHTTPGetParams){
		basicListActivity = pCountrySelectActivity;
		json_country = new Jsonizer(url);
	  handler = new Handler(Looper.getMainLooper());
	  progressDialog = new ProgressDialog(basicListActivity);
	  httpGetParams = pHTTPGetParams;
	  basicListActivity.countryList = new ArrayList<HashMap<String,String>>();
	}
	
	public BasicListMaker(BasicListActivity pCountrySelectActivity, String url, Map<String, String> pHTTPGetParams, String pFlagUrl){
		basicListActivity = pCountrySelectActivity;
		json_country = new Jsonizer(url);
	  handler = new Handler(Looper.getMainLooper());
	  progressDialog = new ProgressDialog(basicListActivity);
	  httpGetParams = pHTTPGetParams;
	  basicListActivity.countryList = new ArrayList<HashMap<String,String>>();
	  flagUrl = pFlagUrl;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Please Wait. Retrieving Data..");
		progressDialog.show();
	}
	
	protected Void doInBackground(String... params) {
		try {
			JSONArray countries = json_country.getArray("", httpGetParams);					

			if (countries.length() > 0) {
				for (int i = 0; i < countries.length(); i++) {
					JSONObject jo = json_country.getArray("", httpGetParams).getJSONObject(i);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("englishName", jo.getString("en_name"));
					map.put("localName", jo.getString("local_name"));
					
					if(flagUrl==null){
						if(jo.has("flag"))
							map.put("flag", jo.getString("flag"));
						else if(jo.has("featured"))
							map.put("flag", jo.getString("featured"));
						else
							map.put("flag", "css/not_found.jpg");
					}else
						map.put("flag", flagUrl);
						
					// adding HashList to ArrayList
					if(basicListActivity!=null)
						basicListActivity.countryList.add(map);
					else
						searchListActivity.countryList.add(map);
				}
						  
	  	  handler.post(new Runnable() {				
	  	  	@Override
	  	  	public void run() {
	  	  		progressDialog.hide();
						if(basicListActivity!=null){
		  	      adapter=new BasicListAdapter(basicListActivity, basicListActivity.countryList, progressDialog);        
		  	      basicListActivity.list.setAdapter(adapter);
						}else{
		  	      adapter=new BasicListAdapter(searchListActivity, searchListActivity.countryList, progressDialog);        
		  	      searchListActivity.list.setAdapter(adapter);							
						}
	  	  	}
	  	  });
			}else{
	  	  handler.post(new Runnable() {				
	  	  	@Override
	  	  	public void run() {
	  	  		progressDialog.hide();
	  	  	}
	  	  });
			}
		} catch (Exception e) {
  	  handler.post(new Runnable() {				
  	  	@Override
  	  	public void run() {
  	  		progressDialog.hide();
  	  	}
  	  });
		}
		
		return null;
	}	
}
	
	