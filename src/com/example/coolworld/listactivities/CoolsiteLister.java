package com.example.coolworld.listactivities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ListView;

import com.example.coolworld.HomeActivity;
import com.example.coolworld.OnlineListMaker;
import com.example.coolworld.R;
import com.example.coolworld.ListNode;
import com.example.coolworld.listAdapters.SimpleListAdapter;
import com.example.coolworld.listNodes.SimpleListNode;

public class CoolsiteLister extends OnlineListMaker{	

	//A friendly constructor
	public CoolsiteLister(){
	}
		
		@Override
	public int setLayout() {
		return R.layout.listview_preset;
	}

	@Override
	public ListView setListView() {
		return (ListView) findViewById(R.id.list);
	}

	@Override
	public String setHeaderText() {
		return getResources().getString(R.string.coolsiteSelectDescription);
	}

	@Override
	public void setURLBuildParams() {
		urlParams = new HashMap<String, String>();
		urlParams.put("vars", "featured");
		urlParams.put("tags", "prop:coolsites countries:"+offlineGateway.getCountryName());
		urlParams.put("f", "getAllData");
	}

	@Override
	public ArrayList<HashMap<String, String>> getOnlineList() {
		return onlineGateway.getCoolsiteList(urlParams);
	}

	@Override
	public void buildListNodeArray() {
		Bitmap bitmap = null;
		listNodes = new SimpleListNode[list.size()];
		String imageDir = offlineGateway.getCountryFlag();
		File file = new File(imageDir);

		try {
			InputStream fis = new FileInputStream(file);
			//get bitmap from the saved coolsite flag file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			System.out.println("TEST: retrieving saved country flag directory: "+imageDir);
			bitmap = BitmapFactory.decodeStream(fis, null, options);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i < list.size(); i++){	
			ListNode node = new SimpleListNode();
			node.setLocalName(list.get(i).get("localName")); //set node local name
			node.setImage(bitmap);
			System.out.println("Setting list node array at index "+i);
			System.out.println("localname: "+list.get(i).get("localName"));
			
						
			listNodes[i] = node;						
		}		
		
		System.out.println("TEST: listnodes items: "+listNodes.length);
	}

	@Override
	public void setListAdapter() {
		adapter = new SimpleListAdapter(this, listNodes);
		listView.setAdapter(adapter);
	}
	
	@Override
	public void initiateListItemClickActions(int position) {
		// Save the selected coolsite's name
		offlineGateway.saveCoolsiteName(list.get(position).get("englishName"));
		// Save the coolsite's header image
		offlineGateway.saveCoolsiteImage(list.get(position).get("image"));
		//Save the front cover
		offlineGateway.saveCoolsiteCoverImage(null);
		
		startActivity(new Intent(this, HomeActivity.class));
	}

	@Override
	public OnlineListMaker errorReloadTarget() {
		// TODO Auto-generated method stub
		return new CoolsiteLister();
	}
}
