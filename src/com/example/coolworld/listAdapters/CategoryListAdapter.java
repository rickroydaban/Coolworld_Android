package com.example.coolworld.listAdapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coolworld.R;

public class CategoryListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<String> data;
    private static LayoutInflater inflater=null;
    
    public CategoryListAdapter(Activity a, ArrayList<String> d) {
    		System.out.println("ANDROID: Instantiated a new Category list adapter!");
        activity = a;
        data=d;
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
        	vi = inflater.inflate(R.layout.listnode_mapcategories, null);

          TextView categoryName = (TextView)vi.findViewById(R.id.categoryName); 
                    
          System.out.println("ANDROID: Setting category node textview value to "+data.get(position));
          
          categoryName.setText(data.get(position));           
        }
        return vi;
    }
}