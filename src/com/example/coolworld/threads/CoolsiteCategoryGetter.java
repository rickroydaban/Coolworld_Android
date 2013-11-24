//package com.example.coolworld.threads;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.apache.http.client.ClientProtocolException;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import com.example.coolworld.Jsonizer;
//import com.example.coolworld.fragments.MapFilterFragment;
//import com.example.coolworld.listAdapters.CategoryListAdapter;
//
//public class CoolsiteCategoryGetter implements Runnable{
//	private MapFilterFragment mapFilterFragment;
//	private Jsonizer jsonizer;
//	private Handler handler;
//  private Map<String, String> httpCoolsiteGetParams;
//	
//	public CoolsiteCategoryGetter(MapFilterFragment pMapFilterFragment, String url, Map<String, String> pHTTPGetParams){
//		mapFilterFragment = pMapFilterFragment;
//		jsonizer = new Jsonizer(url);
//	  handler = new Handler(Looper.getMainLooper());
//	  httpCoolsiteGetParams = pHTTPGetParams;
//	}
//		
//	@Override
//	public void run() {
//		try {
//			System.out.println("ANDROID: HTTP COOLSITE GET PARAMS: "+httpCoolsiteGetParams);
//			JSONArray coolsiteCategories = jsonizer.getArray("", httpCoolsiteGetParams);					
//			
//			
//			System.out.println("ANDROID: Retrieved Coolsite(s): "+coolsiteCategories.length());
//			
//			if (coolsiteCategories.length() > 0) {
//				JSONObject coolsiteObject = jsonizer.getArray("", httpCoolsiteGetParams).getJSONObject(0);
//				JSONArray categories = coolsiteObject.getJSONArray("lookup_categories");
//
//				System.out.println("ANDROID: Retrieved Coolsite Categories: "+categories.length());
//				System.out.println("ANDROID: categories: "+categories);
//				
//				for(int i=0;i<categories.length();i++){
//					String category = categories.getString(i);
////					JSONObject category = categories.getJSONObject(i).getString(name);
//					System.out.println("ANDROID: Adding "+category+" to the category list");
//					mapFilterFragment.categoryList.add(category);
//				}
//				
//				System.out.println("ANDROID:  setting category list view adapater");
//	  	  handler.post(new Runnable() {				
//	  	  	@Override
//	  	  	public void run() {
//	  	      mapFilterFragment.categoryListView.setAdapter(new CategoryListAdapter(mapFilterFragment.getActivity(), mapFilterFragment.categoryList));
//	  	  	}
//	  	  });
//			} 
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}	
//}
//	
//	