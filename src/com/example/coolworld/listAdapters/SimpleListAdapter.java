package com.example.coolworld.listAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolworld.R;
import com.example.coolworld.ListNode;
import com.example.coolworld.listNodes.SimpleListNode;

/* A BasicListAdapter only contains title and image*/

public class SimpleListAdapter extends BaseAdapter {  
  private ListNode [] listNodes;
  private LayoutInflater inflater;
  
  public SimpleListAdapter(Activity activity, ListNode [] listNodes) {
  	this.listNodes=listNodes;
  	System.out.println("List node in simple list adapter contains "+listNodes.length+" items");
    inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return listNodes.length;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }
  
  @Override
  public View getView(int position, View vi, ViewGroup parent) {  	
  	View toBeSeenView = vi;  	
  	ViewHolder holder = null;
  	
    ListNode node = listNodes[position];

    if(toBeSeenView==null){
  		holder = new ViewHolder();
    	toBeSeenView = inflater.inflate(R.layout.listnode_simple, null);
    	System.out.println("Test: inflating "+position);
    	//make a new holder for this node
    	holder.localName = (TextView)toBeSeenView.findViewById(R.id.title); 
      holder.image = (ImageView)toBeSeenView.findViewById(R.id.node_image); 
      toBeSeenView.setTag(holder);
    }
    
    holder = (ViewHolder) toBeSeenView.getTag();
    holder.localName.setText(node.getLocalName());    
  	holder.image.setImageBitmap(node.getImageBitmap());
    
  	return toBeSeenView;
      
//      File imgFile = new File(node.get("image"));
//      if(imgFile.exists()){
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        image.setImageBitmap(myBitmap);
//      }else{
//      	//do something here if the file does not exist
//      	System.out.println("TEST: file: "+node.get("image")+" does not exist!");
//      }
  }
  
//  public Activity getAdapterContext(){
//  	return activity;
//  }

  static class ViewHolder{
  	TextView localName;
  	ImageView image;
  }
}