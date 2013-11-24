//package com.example.coolworld.threads;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import android.app.ProgressDialog;
//import android.os.Handler;
//import android.os.Looper;
//import com.example.coolworld.ListMaker;
//import com.example.coolworld.listAdapters.PreSetAdapter;
//
//public class BasicListMaker implements Runnable {
//	private ListMaker basicListActivity;
//	private Handler handler;
//  public Map<String, String> httpGetParams;
//	private String flagUrl;
//	private ProgressDialog pd;
//	public Thread imageLoader;
//	public BasicListMaker listMaker;
//	
//	public BasicListMaker(ListMaker pCountrySelectActivity, String url, Map<String, String> pHTTPGetParams){
//		basicListActivity = pCountrySelectActivity;
//	  handler = new Handler(Looper.getMainLooper());
//	  httpGetParams = pHTTPGetParams;
//	  basicListActivity.list = new ArrayList<HashMap<String,String>>();
//	  listMaker = this;
//	}
//	
//	public BasicListMaker(ListMaker pCountrySelectActivity, String url, Map<String, String> pHTTPGetParams, String pFlagUrl){
//		basicListActivity = pCountrySelectActivity;
//	  handler = new Handler(Looper.getMainLooper());
//	  httpGetParams = pHTTPGetParams;
//	  basicListActivity.list = new ArrayList<HashMap<String,String>>();
//	  flagUrl = pFlagUrl;
//	  listMaker = this;
//	}
//
//	@Override
//	public void run() {			  
//	  
//		handler.post(new Runnable() {				
//	  	@Override
//	  	public void run() {
//  			pd = new ProgressDialog(basicListActivity);
//	  		pd.setMessage("Please Wait. Retrieving Data...");
//	  		pd.show();
//	  	}
//		});
//	  		
//		try {
//			JSONArray listNodes = json_country.getArray("", httpGetParams);					
//
//			if (listNodes.length() > 0) {
//				if(httpGetParams.containsKey("lookup")){
//					System.out.println("ANDROID_S: LOOKUP!");
//					JSONObject entry = listNodes.getJSONObject(0);
//					JSONArray categories_local = entry.getJSONArray("lookup_categories");
//					JSONArray categories_en = entry.getJSONArray("categories");
//					
//					for(int i=0;i<categories_local.length();i++){
//						System.out.println("ANDROID_S: en: "+categories_en.getString(i));
//						System.out.println("ANDROID_S: lo: "+categories_local.getString(i));
//						System.out.println("ANDROID_S: --- at index "+i+" ---");
//						HashMap<String, String> map = new HashMap<String, String>();
//						map.put("englishName", categories_en.getString(i));
//						map.put("localName", categories_local.getString(i));
//						map.put("flag", entry.getString("featured"));
//						
//						basicListActivity.list.add(map);
//					}
//				}else{
//					System.out.println("ANDROID[S]: not contains lookup!");
//					for (int i = 0; i < listNodes.length(); i++) {
//						JSONObject jo = json_country.getArray("", httpGetParams).getJSONObject(i);
//						HashMap<String, String> map = new HashMap<String, String>();
//						
//						
//						map.put("englishName", jo.getString("en_name"));
//						map.put("localName", jo.getString("local_name"));
//						
//						if(flagUrl==null){
//							if(jo.has("flag"))
//								map.put("flag", jo.getString("flag"));
//							else if(jo.has("featured"))
//								map.put("flag", jo.getString("featured"));
//							else
//								map.put("flag", "css/not_found.jpg");
//						}else
//							map.put("flag", flagUrl);
//							
//						// adding HashList to ArrayList
//							basicListActivity.list.add(map);
//					}
//				}
//				
//	  	  handler.post(new Runnable() {				
//	  	  	@Override
//	  	  	public void run() {
//	  	      basicListActivity.listView.setAdapter(new PreSetAdapter(basicListActivity, listMaker, basicListActivity.list));
//						pd.dismiss();
//	  	  	}
//	  	  });
//			}			
//		} catch (Exception e) {
//		}			
//	}
//}
//	
//	