package com.example.coolworld.gateways;

import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

/* This class abstracts all the database gateways
 */

public final class OfflineGateway {
	private static OfflineGateway instance;
	private DataGateway dataGateway;
	private final String USER_NAME = "userName";
	private final String COUNTRY_NAME = "country";
	private final String COUNTRY_FLAG = "countryFlag";
	private final String COOLSITE_NAME = "coolsite";
	private final String COOLSITE_IMAGE = "coolsiteImage";
	private final String COOLSITE_COVER_IMAGE = "coolsiteCoverImage";
	
	private OfflineGateway(Context context){
		//gateways are defined here
		dataGateway = new DataGateway(context);
	}
	
	public static OfflineGateway getInstance(Context context){
		if(instance==null)
			instance = new OfflineGateway(context);
		
		return instance;
	}
	
	/**public functions are defined below**/
	public boolean isUserDataSet(){
		return (getCountryName()!=null && getCoolsiteName()!=null)?true:false;
	}
	
	public void closeDB(){
		dataGateway.closeDB();
	}
	
	
	//RETRIEVE DATA
	private String getValue(String name){
		return dataGateway.getValue(name);
	}
	
	public String getUsername(){
		return dataGateway.getValue(USER_NAME);
	}
	
	public String getCountryName(){
		System.out.println("TEST: Retrieving value of "+COUNTRY_NAME);
		return dataGateway.getValue(COUNTRY_NAME);
	}
		
	public String getCountryFlag(){
		return dataGateway.getValue(COUNTRY_FLAG);
	}
	
	public String getCoolsiteName(){
		return dataGateway.getValue(COOLSITE_NAME);
	}
	
	public String getCoolsiteImage(){
		return dataGateway.getValue(COOLSITE_IMAGE);
	}
	
	public String getCoolsiteCoverImage(){
		return dataGateway.getValue(COOLSITE_COVER_IMAGE);
	}
	
	public List<HashMap<String, String>> getAllData(){
		return dataGateway.getAllData();
	}
		
	public void saveUsername(String username){
		dataGateway.insertUniqueData(USER_NAME, username);
	}
	
	public void saveCountryName(String country){
		System.out.println("TEST: saving country name: "+country+" to the database");
		dataGateway.insertUniqueData(COUNTRY_NAME, country);
	}
	
	public void saveCountryFlag(String savePath){
		dataGateway.insertUniqueData(COUNTRY_FLAG, savePath);
	}
	
	public void saveCoolsiteName(String coolsite){
		dataGateway.insertUniqueData(COOLSITE_NAME, coolsite);
	}
	
	public void saveCoolsiteImage(String savePath){
		dataGateway.insertUniqueData(COOLSITE_IMAGE, savePath);
	}
	
	public void saveCoolsiteCoverImage(String savePath){
		dataGateway.insertUniqueData(COOLSITE_COVER_IMAGE, savePath);
	}
	
	public void saveHomeItemList(List<HashMap<String, String>> list){
		JSONArray ja = null;
		
		if(list.size()>0){
			ja = new JSONArray();
			
			for(int i=0; i<list.size(); i++){
				JSONObject jo = new JSONObject();
				HashMap<String, String> map = list.get(i);
				
				try {
					jo.put("englishName", map.get("englishName"));
					jo.put("localName", map.get("localName"));
					jo.put("category", "category");
					jo.put("itemImagePath", map.get("itemImagePath"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			
				ja.put(jo);
			}
		}
		
		dataGateway.insertUniqueData("homeItemList", ja.toString());		
	}
		
	public void saveCategoryList(List<HashMap<String, String>> list){
		JSONArray ja = null;
		
		if(getValue("category") != null){
			if(list.size()>0){
				ja = new JSONArray();
				
				for(int i=0; i<list.size(); i++){
					JSONObject jo = new JSONObject();
					HashMap<String, String> map = list.get(i);
					
					try {
						jo.put("englishName", map.get("englishName"));
						jo.put("localName", map.get("localName"));
						jo.put("featuredImagePath", map.get("featuredImagePath"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
					ja.put(jo);
				}
			}
		}
		
		dataGateway.insertUniqueData("categoryList", ja.toString());
	}
	
	public void saveInformationList(List<HashMap<String, String>> list){
		JSONArray ja = null;
		
		if(getValue("information") != null){
			if(list.size()>0){
				ja = new JSONArray();
				
				for(int i=0; i<list.size(); i++){
					JSONObject jo = new JSONObject();
					HashMap<String, String> map = list.get(i);
					
					try {
						jo.put("englishName", map.get("englishName"));
						jo.put("localName", map.get("localName"));
						jo.put("featuredImagePath", map.get("featuredImagePath"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
					ja.put(jo);
				}
			}
		}
	}

	public void updateValue(String name, String newValue){
		dataGateway.updateValue(name, newValue);
	}
}
