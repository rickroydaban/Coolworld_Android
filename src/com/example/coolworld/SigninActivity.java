package com.example.coolworld;

import com.example.coolworld.threads.SigninAuthenticator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SigninActivity extends Activity implements OnClickListener{
	public EditText usernameField, passwordField;
	public TextView forgotPassword, signup;
	public Button signinButton;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signin);
    
    usernameField = (EditText)findViewById(R.id.usernameField);
    passwordField = (EditText)findViewById(R.id.passwordField);
    signinButton = (Button)findViewById(R.id.signinButton);
    signup = (TextView)findViewById(R.id.signupText);
    forgotPassword = (TextView)findViewById(R.id.forgotPasswordText);
    
    signinButton.setOnClickListener(this);
    signup.setOnClickListener(this);
    forgotPassword.setOnClickListener(this);
  }
	@Override
	public void onClick(View clicked) {
		if(clicked == signinButton){
			new SigninAuthenticator(this,getResources().getString(R.string.urlRootLink)).execute();			
		}else if(clicked == signup){
			System.out.println("Android: Switch Activity to Sign up");
		}else if(clicked == forgotPassword){
			System.out.println("Android: Switch Activity to Forgot Password");
		}
	}

}
