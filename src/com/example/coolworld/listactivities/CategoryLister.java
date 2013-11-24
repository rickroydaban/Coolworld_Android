package com.example.coolworld.listactivities;

import java.util.ArrayList;
import java.util.HashMap;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.coolworld.ListMaker;
import com.example.coolworld.OnlineListMaker;
import com.example.coolworld.R;

public class CategoryLister implements ListMaker{
	private HashMap<String, String> urlParams = new HashMap<String, String>();
	private String coolsiteName, coolsiteImage;
	
	public CategoryLister(String coolsiteName, String coolsiteImage){
		this.coolsiteName = coolsiteName;
		this.coolsiteImage = coolsiteImage;
	}
	
		@Override
	public int setLayout() {
		return R.layout.listview_simple_header;
	}

	@Override
	public ListView setListView() {
//		ImageView coolsiteImageView = (ImageView)findViewById(R.id.listview_header_image);
//		coolsiteImageView.setImageURI(Uri.parse(coolsiteImage));
//		
//		//SET THE FEATURED IMAGE HERE
//		
//		return (ListView)findViewById(R.id.list);
		return null;
	}

	@Override
	public String setHeaderText() {
//		return getResources().getString(R.string.informationSelectDescription);
		return null;
	}

	@Override
	public void setURLBuildParams() {
		urlParams.put("vars", "featured");
		urlParams.put("tags", "prop:posts posts:static coolsites:"+coolsiteName);
		urlParams.put("f", "getAllData");
	}
	

	@Override
	public void initiateListItemClickActions(int position) {
		//show something here
	}

	@Override
	public void setListAdapter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OnlineListMaker errorReloadTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
