package com.example.coolworld;

import java.util.ArrayList;
import java.util.HashMap;

import projects.coolworld.threads.ImageDownloader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private ProgressDialog progressDialog;
    private String target;
    
    public InformationListAdapter(Activity a, ArrayList<HashMap<String, String>> d, ProgressDialog p) {
    		System.out.println("Instantiated a new list adapter!");
        activity = a;
        data=d;
        progressDialog = p;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null){
        	vi = inflater.inflate(R.layout.listnode_info, null);

          TextView title = (TextView)vi.findViewById(R.id.title); // title
          TextView category = (TextView)vi.findViewById(R.id.category);
          TextView mobile = (TextView)vi.findViewById(R.id.mobileText);
          ImageView countryFlag = (ImageView)vi.findViewById(R.id.list_image); // thumb image
          HashMap<String, String> country = new HashMap<String, String>();
          country = data.get(position);
          
          // Setting all values in listview
          title.setText(country.get("localName"));           
          category.setText(country.get("category"));
          mobile.setText(country.get("mobile").subSequence(0, 30)+"...");
          
          if(!country.get("imageUrl").equals("NA")){
          	target = activity.getResources().getString(R.string.urlRootLink)+country.get("imageUrl");
          	System.out.println("Target Image: "+target);
          	new Thread(new ImageDownloader(activity, countryFlag, target, progressDialog)).start();
          }
        }
        return vi;
    }
}