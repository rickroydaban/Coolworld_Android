//package com.example.coolworld.threads;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import android.app.ProgressDialog;
//import android.os.Handler;
//import android.os.Looper;
//
//import com.example.coolworld.Jsonizer;
//import com.example.coolworld.fragments.MapFilterFragment;
//import com.example.coolworld.fragments.MapFragment;
//import com.example.coolworld.listAdapters.ItemListAdapter;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class CoolsiteItemsGetter implements Runnable {
//	private MapFilterFragment mapFilterFragment;
//	private MapFragment mapFragment;
//	private Jsonizer jsonizer;
//	private Handler handler;
//  private ProgressDialog progressDialog;
//	private double itemLat;
//	private double itemLng;
//	private Map<String, String> httpItemGetParams;
//	private String itemName;
//	
//	public CoolsiteItemsGetter(MapFilterFragment pMapFilterFragment, String url, Map<String, String> pHTTPItemGetParams){
//		mapFilterFragment = pMapFilterFragment;
//		jsonizer = new Jsonizer(url);
//	  handler = new Handler(Looper.getMainLooper());
//	  progressDialog = mapFilterFragment.progressDialog;
//	  httpItemGetParams = pHTTPItemGetParams;
//	  progressDialog.setMessage("Please Wait. Retrieving Category List");
//	}
//		
//	public CoolsiteItemsGetter(MapFragment pMapFragment, String url, Map<String, String> pHTTPItemGetParams){
//		mapFragment = pMapFragment;
//		jsonizer = new Jsonizer(url);
//	  handler = new Handler(Looper.getMainLooper());
//	  progressDialog = mapFragment.progressDialog;
//	  httpItemGetParams = pHTTPItemGetParams;
//	  progressDialog.setMessage("Setting Item Markers on the map");
//	}
//
//	@Override
//	public void run() {
//		try {
//			JSONArray coolsiteItems = jsonizer.getArray("", httpItemGetParams);					
//
//			if (coolsiteItems.length() > 0) {
//				System.out.println("ANDROID: Retrieved "+coolsiteItems.length()+" items");
//				for (int i = 0; i < coolsiteItems.length(); i++) {
//						JSONObject itemObject = jsonizer.getArray("", httpItemGetParams).getJSONObject(i);
//						
//						itemName = itemObject.getString("local_name");
//						
//						if(mapFragment!=null){
//							String itemLocation = itemObject.getString("location");
//							System.out.println("ANDROID: at location: "+itemLocation);
//							itemLat = Double.parseDouble(itemLocation.substring(0, itemLocation.indexOf(',')-1));
//							System.out.println("ANDROID: Lat: "+itemLat);
//							itemLng = Double.parseDouble(itemLocation.substring(itemLocation.indexOf(',')+1, itemLocation.length()));
//							System.out.println("ANDROID: Lng: "+itemLng);
//
//							handler.post(new Runnable() {				
//				  	  	@Override
//				  	  	public void run() {
//				  	  		progressDialog.hide();						
//				  	  		mapFragment.map.addMarker(new MarkerOptions().position(new LatLng(itemLat,itemLng))
//																													 		 .title(itemName));
//				  	  	}
//				  	  });
//						}else{
//							HashMap<String, String> map = new HashMap<String, String>();
//							map.put("englishName", itemObject.getString("en_name"));
//							map.put("localName", itemObject.getString("local_name"));
//							
//							if(itemObject.has("featured"))
//								map.put("flag",itemObject.getString("featured"));
//								
//							System.out.println("Android: Adding "+map.get("localName")+" to the map");
//							System.out.println("Android: Image URL: "+map.get("flag")+" to the map");
//							
//							mapFilterFragment.itemList.add(map);							
//						}
//				}
//				
//				if(mapFilterFragment!=null){
//		  	  handler.post(new Runnable() {				
//		  	  	@Override
//		  	  	public void run() {
//		  	      mapFilterFragment.itemListView.setAdapter(new ItemListAdapter(mapFilterFragment.getActivity(), mapFilterFragment.itemList));
//		  	  	}
//		  	  });
//				}
//			}else{
//				System.out.println("ANDROID: Retrieved 0 items");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}	
//}
//	
//	