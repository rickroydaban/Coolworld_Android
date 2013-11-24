package com.example.coolworld.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.coolworld.R;
import com.example.coolworld.R.id;
import com.example.coolworld.R.layout;
import com.example.coolworld.R.string;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFilterFragment extends Fragment {
  public GoogleMap map;
	public ArrayList<HashMap<String, String>> itemList;
	public ArrayList<String> categoryList;
//	public ListView categoryListView = new ListView(super.getActivity());
	public ListView itemListView, categoryListView;

  private HashMap<String,String> httpGetCategoryMapUrl, httpGetItemsMapUrl;
	public String coolsiteName;
	public ProgressDialog progressDialog;
	
  public static MapFilterFragment newInstance(String pCoolsiteName){
  		MapFilterFragment f = new MapFilterFragment();
      Bundle bdl = new Bundle(1);
      bdl.putString("coolsiteName", pCoolsiteName);
      f.setArguments(bdl);
      return f;
  }
	
	@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);    
  }

  /**
   * The Fragment's UI is just a simple text view showing its
   * instance number.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_mapinfo, container, false);
      itemListView = (ListView)v.findViewById(R.id.itemList);
      categoryListView = (ListView)v.findViewById(R.id.categoryList);
      map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();      
   
      coolsiteName = getArguments().getString("coolsiteName");
      System.out.println("ANDROID MAP (MAP FILTER FRAGMENT): Coolsite Name: "+coolsiteName);
      progressDialog = new ProgressDialog(super.getActivity());
      itemList = new ArrayList<HashMap<String,String>>();
      categoryList = new ArrayList<String>();
      
      httpGetCategoryMapUrl = new HashMap<String, String>();
    	httpGetCategoryMapUrl.put("lookup", "categories");  	  
    	httpGetCategoryMapUrl.put("target", coolsiteName);  	  
    	httpGetCategoryMapUrl.put("vars", "categories");  	  
    	httpGetCategoryMapUrl.put("tags", "prop:coolsites");
    	httpGetCategoryMapUrl.put("f", "getAllData");
    	
    	httpGetItemsMapUrl = new HashMap<String, String>();
    	httpGetItemsMapUrl.put("vars", "featured");  	  
    	httpGetItemsMapUrl.put("tags", "prop:items cat:hotels coolsites:"+coolsiteName);
    	httpGetItemsMapUrl.put("f", "getAllData");

//      new Thread(new CoolsiteCategoryGetter(this, getResources().getString(R.string.urlRootLink),httpGetCategoryMapUrl)).start();
      categoryListView.setOnItemClickListener(new OnItemClickListener() {
  			@Override
  			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
  				System.out.println("A CATEGORY IS CLICKED!");
  			}
      });
//      new Thread(new CoolsiteItemsGetter(this, getResources().getString(R.string.urlRootLink),httpGetItemsMapUrl)).start();    
      itemListView.setOnItemClickListener(new OnItemClickListener() {
  			@Override
  			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
  				System.out.println("AN ITEM IS CLICKED!");
 			}
      });

      
      
      return v;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
            
  }
}