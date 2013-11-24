package com.example.coolworld.activities;


import com.example.coolworld.HomeActivity;
import com.example.coolworld.R;
import com.example.coolworld.gateways.OfflineGateway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IndexActivity extends Activity implements OnClickListener{
	private Button signupButton, signinButton, tutorialButton;
	private OfflineGateway gateway;
	
	@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    gateway = OfflineGateway.getInstance(this); //gateway manages the data of this phone's database
    
    if(gateway.isUserDataSet()) //if the user has already set the necessary data, will be redirected to home activity
    	startActivity(new Intent(this, HomeActivity.class));
    
    
    setContentView(R.layout.activity_index);

    signinButton = (Button)findViewById(R.id.signinButton);
    signupButton = (Button)findViewById(R.id.signupButton);
    tutorialButton = (Button)findViewById(R.id.apuriButton);
    
    signinButton.setOnClickListener(this);
    signupButton.setOnClickListener(this);
    tutorialButton.setOnClickListener(this);
  }
	
	@Override
	public void onClick(View clicked) {
		if(clicked == signinButton){
			startActivity(new Intent(this, SigninActivity.class));
		}else if(clicked == signupButton){
			System.out.println("Switch to Sign Up Activity");
		}else if(clicked == tutorialButton){
			System.out.println("Switch to Tutorial Activty");
		}
	}
}
