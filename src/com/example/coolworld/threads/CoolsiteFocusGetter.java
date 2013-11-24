//package com.example.coolworld.threads;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import org.apache.http.client.ClientProtocolException;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Looper;
//import com.example.coolworld.R;
//import com.example.coolworld.fragments.MapFragment;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.model.LatLng;
//
//public class CoolsiteFocusGetter implements Runnable {
//	private MapFragment mapFragment;
//	private Handler handler;
//  private ProgressDialog progressDialog;
//  private Map<String, String> httpCoolsiteGetParams;
//	private double coolsiteLat;
//	private double coolsiteLng;
//	private HashMap<String, String> httpItemGetParams;
//	
//	public CoolsiteFocusGetter(MapFragment pMapFragment, String url, Map<String, String> pHTTPGetParams){
//		mapFragment = pMapFragment;
//	  handler = new Handler(Looper.getMainLooper());
//	  progressDialog = mapFragment.progressDialog;
//	  httpCoolsiteGetParams = pHTTPGetParams;
//		progressDialog.setMessage("Please Wait. Focusing to Coolsite Map..");
//		progressDialog.show();
//	}
//	
//	public void run() {
//		try {
//			JSONArray countryCoolsites = jsonizer.getArray("", httpCoolsiteGetParams);					
//
//			if (countryCoolsites.length() > 0) {
//				for (int i = 0; i < countryCoolsites.length(); i++) {
//					JSONObject coolsiteObject = jsonizer.getArray("", httpCoolsiteGetParams).getJSONObject(i);
//
//					if(coolsiteObject.getString("en_name").toLowerCase().equals(mapFragment.coolsiteName.toLowerCase())){
//						String coolsiteLocation = coolsiteObject.getString("location");
//						coolsiteLat = Double.parseDouble(coolsiteLocation.substring(0, coolsiteLocation.indexOf(',')-1));
//						coolsiteLng = Double.parseDouble(coolsiteLocation.substring(coolsiteLocation.indexOf(',')+1, coolsiteLocation.length()));
//						
//						System.out.println("Lat: "+coolsiteLat);
//						System.out.println("Lng: "+coolsiteLng);
//				
//						httpItemGetParams = new HashMap<String, String>();
//						httpItemGetParams.put("vars", "location featured");  	  
//						httpItemGetParams.put("tags", "prop:items coolsites:"+mapFragment.coolsiteName);
//						httpItemGetParams.put("f", "getAllData");
//
//			  	  handler.post(new Runnable() {				
//			  	  	@Override
//			  	  	public void run() {
//								mapFragment.map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(coolsiteLat,coolsiteLng), 13), 2000, null);								
//								new Thread(new CoolsiteItemsGetter(mapFragment, mapFragment.getResources().getString(R.string.urlRootLink), httpItemGetParams)).start();
//			  	  	}
//			  	  });
//					}
//				}						  
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