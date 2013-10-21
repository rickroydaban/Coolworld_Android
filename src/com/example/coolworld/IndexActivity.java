package com.example.coolworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IndexActivity extends Activity implements OnClickListener{
	Button signupButton, signinButton, apurriButton;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_index);
    
    signinButton = (Button)findViewById(R.id.signinButton);
    signupButton = (Button)findViewById(R.id.signupButton);
    apurriButton = (Button)findViewById(R.id.apuriButton);
    
    signinButton.setOnClickListener(this);
    signupButton.setOnClickListener(this);
    apurriButton.setOnClickListener(this);
  }
	@Override
	public void onClick(View clicked) {
		if(clicked == signinButton){
			Intent intent = new Intent(this, SigninActivity.class);
			finish();
			startActivity(intent);
		}else if(clicked == signupButton){
			System.out.println("Switch to Sign Up Activity");
		}else if(clicked == apurriButton){
			System.out.println("Switch to Aparri Activty");
		}
	}

}
