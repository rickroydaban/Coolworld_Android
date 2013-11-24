package com.example.coolworld;

import android.app.Activity;
import android.app.ProgressDialog;

public final class GlobalVars {
	private static GlobalVars instance;
	private static ProgressDialog pd;
	
	private GlobalVars(Activity activity){
		pd = new ProgressDialog(activity);
	}
	
	public static GlobalVars getInstance(Activity activity){
		if(instance == null)
			instance = new GlobalVars(activity);
		
		return instance;
	}
	
	public ProgressDialog getProgressDialog(){
		return pd;
	}
}
