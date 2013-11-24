package com.example.coolworld;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolworld.gateways.OfflineGateway;
import com.example.coolworld.gateways.OnlineGateway;
import com.example.coolworld.listAdapters.SimpleListAdapter;
import com.example.coolworld.listactivities.CountryLister;

public abstract class OnlineListMaker extends Activity implements ListMaker{
	//public variables
	protected ListView listView;
	protected ArrayList<HashMap<String, String>> list;
	protected ListNode[] listNodes;
	protected SimpleListAdapter adapter;
	protected HashMap<String, String> urlParams;
	
	//protected variables
	protected ListMaker listMaker;
	protected String baseURL;
	protected OfflineGateway offlineGateway;
	protected OnlineGateway onlineGateway;
	
	//private variables
	private TextView titleView;
	private ProgressDialog pd;
	
	//abstract methods
	public abstract int setLayout(); 
	public abstract ListView setListView(); 
	public abstract String setHeaderText(); 
	public abstract void setURLBuildParams();
	public abstract void buildListNodeArray();
	public abstract void setListAdapter();
	public abstract OnlineListMaker errorReloadTarget();
	public abstract void initiateListItemClickActions(int position);
	public abstract ArrayList<HashMap<String, String>> getOnlineList();
	
	@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    //initializations
		baseURL = getResources().getString(R.string.urlRootLink);
		offlineGateway = OfflineGateway.getInstance(this);
		System.out.println("TEST: Instantiated offline gateway: "+offlineGateway);
		onlineGateway = OnlineGateway.getInstance(this);
		listMaker = this;

		pd = new ProgressDialog((Context) listMaker);
		pd.setMessage("Initializing List...");
		pd.show();
    setContentView(setLayout());
		listView = setListView();
		titleView = (TextView)findViewById(R.id.listview_description);//should be placed here	
		String headerText = setHeaderText();
		System.out.println("TEST: header text: "+headerText);
		System.out.println("TEST: titleview: "+titleView.getContext());
		titleView.setText(headerText);
		setURLBuildParams();
		pd.setMessage("Retrieving List Data. Please Wait.");

		new Thread(new Runnable() {
			@Override
			public void run() {
				//get the online list
				list = getOnlineList();

				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						pd.setMessage("Finalizing List Contents...");
						
						System.out.println("TEST: setting list adapter in country lister.java");
						if (list == null) {
							Toast.makeText((Context)listMaker, "Error in Connection. Restart loading list.", Toast.LENGTH_LONG).show();
							// restart
							finish();
							startActivity(new Intent((Context) listMaker, errorReloadTarget().getClass()));
						} else{
							//build the listNode array
							buildListNodeArray();
							//build list view
							setListAdapter();
						}
						
						// Click event for single list row
						listView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								initiateListItemClickActions(position);
							}
						});
						pd.dismiss();
					}
				});
			}
		}).start();

  }
}
