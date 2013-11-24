package com.example.coolworld.listactivities;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ListView;
import com.example.coolworld.OnlineListMaker;
import com.example.coolworld.R;
import com.example.coolworld.ListNode;
import com.example.coolworld.listAdapters.SimpleListAdapter;
import com.example.coolworld.listNodes.SimpleListNode;

public class CountryLister extends OnlineListMaker {
	private Bitmap bitmap = null; //just for the sake for the listnode array builder
	private String imageUrl = null; //just for the sake for the listnode array builder also
	// A friendly constructor
	public CountryLister() {
	}

	@Override
	public int setLayout() {
		System.out.println("TEST: setting layout in countrylister.java");
		return R.layout.listview_preset;
	}

	@Override
	public ListView setListView() {
		System.out.println("TEST: setting listview in countrylister.java");
		return (ListView) findViewById(R.id.list);
	}

	@Override
	public String setHeaderText() {
		System.out.println("TEST: setting header text in countrylister.java");
		return getResources().getString(R.string.countrySelectDescription);
	}

	@Override
	public void setURLBuildParams() {
		System.out.println("TEST: setting url params in countrylister.java");
		urlParams = new HashMap<String, String>();
		urlParams.put("vars", "flag");
		urlParams.put("tags", "prop:countries");
		urlParams.put("f", "getAllData");
		System.out.println("TEST: params set: "+urlParams);
	}

	@Override
	public ArrayList<HashMap<String, String>> getOnlineList() {
		System.out.println("TEST: setting online list in country lister.java");
		return onlineGateway.getCountryList(urlParams);
	}

	@Override
	public void buildListNodeArray(){
		listNodes = new SimpleListNode[list.size()];

		for(int i=0; i < list.size(); i++){	
			ListNode node = new SimpleListNode();
			node.setLocalName(list.get(i).get("localName")); //set node local name
			imageUrl = list.get(i).get("image");

			System.out.println("Setting list node array at index "+i);
			System.out.println("localname: "+list.get(i).get("localName"));
			System.out.println("imageUrl: "+list.get(i).get("image"));
			Thread t = new Thread(new Runnable(){				
				@Override
				public void run(){
					//get bitmap online to set node image
					try {
						bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			t.start();
			
			try {
				t.join();//make sure that image will be loaded before proceeding to the next step
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			node.setImage(bitmap); //set node bitmap
			
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
		String countryName = list.get(position).get("englishName");
		String countryFlagUrl = list.get(position).get("image");
		
		offlineGateway.saveCountryName(countryName);
		System.out.println("TEST: saving the selected country: "+countryName);
		String filename = countryName+"_flag."+onlineGateway.extractFileExtension(countryFlagUrl);
		// Save the selected country's name
		System.out.println("TEST: successfully saved country name: "+offlineGateway.getCountryName());
		// Save the selected country's flag
		String saveFlagDir = onlineGateway.downloadedImgDir + "countryFlag/";
		System.out.println("TEST: has chosen a country . saving flag image to " + saveFlagDir);
		String saveDir = onlineGateway.saveImage(((SimpleListNode)listNodes[position]).getImageBitmap(),
																						 onlineGateway.downloadedImgDir+"country/",
																						 filename);
		System.out.println("TEST: country flag has been successfully saved to "+saveDir);
		offlineGateway.saveCountryFlag(saveDir);
	
		startActivity(new Intent(this, new CoolsiteLister().getClass()));
	}

	@Override
	public OnlineListMaker errorReloadTarget() {
		return new CountryLister();
	}
}
