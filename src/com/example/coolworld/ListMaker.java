package com.example.coolworld;

import android.widget.ListView;

public interface ListMaker{
	//methods to ensure that data will be set to our list activity
	public int setLayout(); 
	public ListView setListView(); 
	public String setHeaderText(); 
	public void setURLBuildParams();
	public void setListAdapter();
	public void initiateListItemClickActions(int position);
	public OnlineListMaker errorReloadTarget();
}
