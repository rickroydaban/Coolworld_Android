package com.example.coolworld.fragments;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolworld.R;
import com.example.coolworld.R.id;
import com.example.coolworld.R.layout;
import com.example.coolworld.R.string;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment {
  public GoogleMap map;
//  final LatLng HAMBURG = new LatLng(53.558, 9.927);
//  final LatLng KIEL = new LatLng(53.551, 9.993);
	private HashMap<String,String> countryMap;
	public String coolsiteName;
	public ProgressDialog progressDialog;
	
  public static MapFragment newInstance(String pCoolsiteName){
  		MapFragment f = new MapFragment();
      Bundle bdl = new Bundle(1);
      bdl.putString("coolsiteName", pCoolsiteName);
      f.setArguments(bdl);
      return f;
  }
	
	@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    coolsiteName = getArguments().getString("coolsiteName");
    System.out.println("ANDROID MAP (MAP FRAGMENT): Coolsite Name: "+coolsiteName);
    progressDialog = new ProgressDialog(super.getActivity());
    
    countryMap = new HashMap<String, String>();
    
  	countryMap.put("vars", "location featured");  	  
  	countryMap.put("tags", "prop:coolsites countries:philippines");
  	countryMap.put("f", "getAllData");

//    new CoolsiteFocusGetter(this, getResources().getString(R.string.urlRootLink),countryMap).execute();
  }

  /**
   * The Fragment's UI is just a simple text view showing its
   * instance number.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_map, container, false);

      map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
          .getMap();

          return v;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
  }
}