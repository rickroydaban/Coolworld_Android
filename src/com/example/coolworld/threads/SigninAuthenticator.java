	package com.example.coolworld.threads;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.example.coolworld.activities.SigninActivity;
import com.example.coolworld.gateways.OfflineGateway;
import com.example.coolworld.gateways.OnlineGateway;
import com.example.coolworld.listactivities.CountryLister;

public class SigninAuthenticator implements Runnable {
	private SigninActivity signinActivity;
	private HashMap<String, String> userMap;
	private ProgressDialog pd;
	private OnlineGateway onlineGateway;
	private OfflineGateway offlineGateway;
	private String username;
	
	public SigninAuthenticator(SigninActivity signinActivity, HashMap<String, String> userMap) {
		this.signinActivity = signinActivity;
		this.userMap = userMap;
		onlineGateway = OnlineGateway.getInstance(signinActivity);
		offlineGateway = OfflineGateway.getInstance(signinActivity);
		
		pd = new ProgressDialog(signinActivity);
		pd.setMessage("Please Wait. Logging in...");
		pd.show();
	}	    

	@Override
	public void run() {
		username = onlineGateway.getSignedInUser(userMap);

		new Handler(Looper.getMainLooper()).post(new Runnable() {				
	  	@Override
	  	public void run() {
				pd.dismiss();				
	  	}
  	});
		
		if(username==null || username.equals("false")){
			new Handler(Looper.getMainLooper()).post(new Runnable() {				
		  	@Override
		  	public void run() {
		  		if(username==null)
		  			Toast.makeText(signinActivity, "Connection Error!", Toast.LENGTH_LONG).show();
		  		else
						Toast.makeText(signinActivity, "Username or Password is incorrect!", Toast.LENGTH_LONG).show();
		  	}
	  	});
		}else{
			offlineGateway.saveUsername(username);
			new Handler(Looper.getMainLooper()).post(new Runnable() {				
		  	@Override
		  	public void run() {
					signinActivity.startActivity(new Intent(signinActivity, new CountryLister().getClass()));
		  	}
	  	});
		}

	}			
}
	
	