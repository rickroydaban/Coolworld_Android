package projects.coolworld.threads;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.coolworld.BasicListActivity;
import com.example.coolworld.Jsonizer;
import com.example.coolworld.SigninActivity;

public class SigninAuthenticator extends AsyncTask<String, Void, String> {
	private SigninActivity signinActivity;
	private HashMap<String, String> user;
	private Jsonizer authenClient;

	public SigninAuthenticator(SigninActivity pSigninActivity, String url) {
		signinActivity = pSigninActivity;
		
		user = new HashMap<String, String>();		
		user.put("email", signinActivity.usernameField.getText().toString());
		user.put("password", signinActivity.passwordField.getText().toString());						
		user.put("tags", "prop:users");
		user.put("f", "authenticate");
		
		authenClient = new Jsonizer(url);		
	}
	    
	protected String doInBackground(String... params) {
		try {
			JSONObject j = authenClient.get("", user);
			Log.d("JSONObject", j.getString("en_name"));
			return j.getString("en_name");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}							
	
		return "false";
	}

		protected void onPostExecute(String userData) {
			super.onPostExecute(userData);

			if(userData.equals("false")){
				Log.d("WRONG","LOGIN");
				Toast.makeText(signinActivity, "Login Failed!", Toast.LENGTH_SHORT).show();
			}else{
				Intent i = new Intent(signinActivity, BasicListActivity.class);
				signinActivity.startActivity(i);
			}
		}
			
	}
	
	